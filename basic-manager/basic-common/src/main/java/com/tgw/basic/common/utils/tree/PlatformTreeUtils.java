package com.tgw.basic.common.utils.tree;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.common.utils.string.PlatformStringUtils;
import com.tgw.basic.common.utils.tree.model.SysEnTreeNode;
import net.sf.json.JSONArray;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlatformTreeUtils {

	/**
	 * 生成ext树节点所需的json格式字符串
	 * @param treeNodeList
	 * @param treeRootVal
	 * @return
	 * @throws PlatformException
	 */
	public static String  createExtTreeList(List<SysEnTreeNode> treeNodeList, boolean hasCheckedBox,String treeRootVal) throws PlatformException {
		if( treeNodeList==null || treeNodeList.isEmpty() ){
			return "[]";
		}
		/**
		 * 定义“数组-链表”，该数组链表的每一项相当于一深度为2的小树
		 * Map的key相当于“数组”的某一项，Map的value相当于该key所拥有的“链表”
		 * 这里，key为父节点ID，list为具有相同父节点ID的所有同级子节点实体list（属于该父节点的所有子节点）
		 */
		Map<String, List<SysEnTreeNode>> treeNodeMap = new HashMap<String, List<SysEnTreeNode>>();

		for (SysEnTreeNode tempTreeNode : treeNodeList) {
			// 变量定义务必在循环内，对象是引用，不能重复使用同一个对象变量
			SysEnTreeNode newTreeNode = new SysEnTreeNode();

			try {
				BeanUtils.copyProperties(newTreeNode,tempTreeNode);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new PlatformException("生成树节点出错，给节点赋值失败！");
			} catch (InvocationTargetException e) {
				e.printStackTrace();
				throw new PlatformException("生成树节点出错，给节点赋值失败！");
			}

			String fatherId = String.valueOf(  tempTreeNode.getParentId()  );
			if( (null == treeRootVal && null==fatherId)
				|| ( null!=treeRootVal && treeRootVal.equals( fatherId ) )  ){
				//|| StringUtils.isBlank( fatherId ) || "null".equals( fatherId )  没有treeRootVal参数时候的判断条件
				fatherId = "defaultTreeRoot";
			}

			// 获取当前遍历结点的父ID，并判断该父节点的数组链表项是否存在，如果该“数组项-链表项”不存在，则新建一个，并放入“数组-链表”
			if (treeNodeMap.get(fatherId) == null) {
				List<SysEnTreeNode> list = new ArrayList<SysEnTreeNode>();
				list.add(newTreeNode);
				treeNodeMap.put(fatherId, list);
			} else {
				List<SysEnTreeNode> valueList = treeNodeMap.get(fatherId);
				valueList.add(newTreeNode);
				treeNodeMap.put(fatherId, valueList);
			}
		}
		// 以上，至此，第一遍遍历完毕，非叶子节点都拥有一个“数组-链表项”，也即“最小的树”已创建完毕

		// 以下，对“数组链表”Map进行遍历，更改“最小的树”的从属关系（更改指针指向），也即把所有小树组装成大树
		for (Map.Entry<String, List<SysEnTreeNode>> entry : treeNodeMap.entrySet()) {
			// 获取当前遍历“数组项-链表项”的链表项，并对链表项进行遍历，从“数组-链表”小树中找到它的子节点，并将该子节点加到该小树的children中
			List<SysEnTreeNode> smallTreeList = new ArrayList<SysEnTreeNode>();
			smallTreeList = entry.getValue();
			int nodeListSize = smallTreeList.size();
			for (int i = 0; i < nodeListSize; i++) {
				String findID = String.valueOf( smallTreeList.get(i).getId() );
				List<SysEnTreeNode> findList = treeNodeMap.get(findID);
				// 以下操作不能取出对象存放在变量中，否则将破坏树的完整性
				smallTreeList.get(i).setChildren(findList);
			}
		}
		// 获取以0为父Id的链表项，该链表项是根节点实体，里面已封装好各子节点，可以由于多个根节点，即这些根结点的父Id都为0
		List<SysEnTreeNode> rootNodeList = treeNodeMap.get("defaultTreeRoot");

		if( rootNodeList!=null && !rootNodeList.isEmpty() ){
			for( SysEnTreeNode sysEnTreeNode : rootNodeList ){
				dealTreeNodeLeaf( sysEnTreeNode );//设置是否叶子节点属性
			}
		}else{
			return "[]";
		}

		JSONArray jsonArray = JSONArray.fromObject(rootNodeList);
		String treeJsonStr = jsonArray.toString();

		if( !hasCheckedBox ){
			//去掉树节点前面的勾选框
			treeJsonStr = treeJsonStr.replace("\"checked\":false,","");
		}
		return treeJsonStr;
	}

	/**
	 * 通过递归判断并设置是否为叶子节点
	 * @param sysEnTreeNode
     */
	public static void dealTreeNodeLeaf( SysEnTreeNode sysEnTreeNode  ){
		if( sysEnTreeNode.getChildren()==null || sysEnTreeNode.getChildren().size()==0 ){
			sysEnTreeNode.setLeaf(true);
		}else{
			sysEnTreeNode.setLeaf(false);
			for( SysEnTreeNode sysEnTreeNodeChild:sysEnTreeNode.getChildren() ){
				dealTreeNodeLeaf(sysEnTreeNodeChild);
			}
		}
	}

	/**
	 * 将原节点对象的值赋值给SysEnTreeNode。
	 * 返回List<SysEnTreeNode>，之后可使用List<SysEnTreeNode>生成一个统一格式的树结点json串
	 * @param orgTreeList
	 * @param fieldMapReal   字段映射关系   例：id:orgNodeId,text:orgNodeText,....   格式：SysEnTreeNode属性：原节点树对象字段名称，...
	 * @return
	 * @throws PlatformException
     */
	public static List<SysEnTreeNode> copyBeanProToTreeNode(List<Object> orgTreeList, String fieldMapReal) throws PlatformException{
		if( StringUtils.isBlank(fieldMapReal) ){
			throw new PlatformException("生成树节点出错，节点配置参数错误！");
		}

		//字段映射关系
		String[] fieldsMap = fieldMapReal.split(",");

		List<SysEnTreeNode> sysEnTreeNodeList = new ArrayList<SysEnTreeNode>();
		for(Object orgTreeNode : orgTreeList){

			SysEnTreeNode sysEnTreeNode = new SysEnTreeNode();
			for( int i=0;i<fieldsMap.length;i++ ){

				try {
					String[] fieldMap = fieldsMap[i].split(":");
					String treeNodeFieldName = fieldMap[0];//树节点对象的字段属性名称
					String orgFieldName = fieldMap[1];//源对象的字段属性名称

					Method met=orgTreeNode.getClass().getDeclaredMethod( "get"+ PlatformStringUtils.firstLetterToUpperCase(orgFieldName.trim()) );
					Object tempVal = met.invoke(orgTreeNode);

					BeanUtils.copyProperty( sysEnTreeNode,treeNodeFieldName,tempVal.toString() );

				} catch (IllegalAccessException e) {
					e.printStackTrace();
					throw new PlatformException("生成树节点出错！");
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
					throw new PlatformException("生成树节点出错！");
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					throw new PlatformException("生成树节点出错！");
				}
			}

			sysEnTreeNodeList.add( sysEnTreeNode );
		}

		return sysEnTreeNodeList;
	}


	public static List<SysEnTreeNode> copyMapValueToTreeNode(List<Map<String,Object>> orgTreeList, String fieldMapReal) throws PlatformException{
		if( StringUtils.isBlank(fieldMapReal) ){
			throw new PlatformException("生成树节点出错，节点配置参数错误！");
		}

		//字段映射关系
		String[] fieldsMap = fieldMapReal.split(",");

		List<SysEnTreeNode> sysEnTreeNodeList = new ArrayList<SysEnTreeNode>();
		if( orgTreeList==null || orgTreeList.isEmpty() ){
			return  sysEnTreeNodeList;
		}

		for(Map<String,Object> orgTreeNodeMap : orgTreeList){

			SysEnTreeNode sysEnTreeNode = new SysEnTreeNode();
			for( int i=0;i<fieldsMap.length;i++ ){

				try {
					String[] fieldMap = fieldsMap[i].split(":");
					String treeNodeFieldName = fieldMap[0];//树节点对象的字段属性名称
					String orgFieldName = fieldMap[1];//map集合中的key值

					String tempTreeNodeAttVal = null;//树节点对象属性值
					//id和text必须有值
					if( ( "id".equals(treeNodeFieldName) || "text".equals(treeNodeFieldName)  )
						 &&  null == orgTreeNodeMap.get( orgFieldName )	){
						throw new PlatformException("树节点查询结果错误！查询结果中必须包含树节点id和text对应的值！");
					}

					if( null == orgTreeNodeMap.get( orgFieldName ) ){
						continue;
					}else{
						tempTreeNodeAttVal = orgTreeNodeMap.get( orgFieldName ).toString();
					}

					BeanUtils.copyProperty( sysEnTreeNode,treeNodeFieldName,tempTreeNodeAttVal );
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					throw new PlatformException("生成树节点出错！");
				}  catch (InvocationTargetException e) {
					e.printStackTrace();
					throw new PlatformException("生成树节点出错！");
				}
			}

			sysEnTreeNodeList.add( sysEnTreeNode );
		}

		return sysEnTreeNodeList;
	}
}
