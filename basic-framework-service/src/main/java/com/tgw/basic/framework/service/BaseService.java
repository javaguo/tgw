package com.tgw.basic.framework.service;

import com.github.pagehelper.PageInfo;
import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.core.model.AbstractBaseBean;

import java.util.List;
import java.util.Map;

public interface BaseService {
    /**
     * 为满足框架需要，具体业务层的service一定要调用此方法。
     * 将具体业务的mapper赋值给baseModelMapper
     */
    public abstract void initMapper();

    /*
     * 初始化查询列表数据方法，目前暂时用不着
     * @param pageNum
     * @param pageSize
     * @param object

    public abstract  void initSearchData(int pageNum,int pageSize,Object object);
*/
    /**
     * 根据条件分页查询列表数据接口
     * @param pageNum
     * @param pageSize
     * @param object
     * @return
     */
    public abstract PageInfo searchData(int pageNum, int pageSize, Object object);

    /**
     * 保存对象
     * @param abstractBaseBean
     */
    public abstract void saveBean(AbstractBaseBean abstractBaseBean);

    /**
     * 更新对象
     * @param object
     * @throws PlatformException
     */
    public abstract void updateBean(Object object) throws PlatformException;

    /**
     * 更新多个对象
     * @param beanList
     * @throws PlatformException
     */
    public abstract void updateBeans(List<Object> beanList) throws PlatformException;

    /**
     * 根据ID查询唯一的对象
     * @param object   给Bean对象设置ID即可
     * @return
     * @throws PlatformException
     */
    public abstract Object selectUniqueBeanByPrimaryKey(Object object) throws PlatformException;

    /**
     * 批量删除bean对象
     * @param beanList
     * @throws PlatformException
     */
    public abstract void deleteBatchBean(List<Object> beanList) throws PlatformException;

    /**
     * 查询下拉框数据
     * @param  loadDataMethodName Mapper查询数据的方法名
     * @param  value  查询条件值，下拉框父value
     * @return
     * @throws PlatformException
     */
    public abstract Object loadComboboxData(String loadDataMethodName, Object value) throws PlatformException;

    /**
     * 根据菜单标识查询该菜单的所有功能
     * @param menuIdentify
     * @return
     */
    public abstract List<Map<String,Object>> queryFuncByMenuIdentify( String menuIdentify ) throws PlatformException;

    /**
     * 根据菜单标识及用户id查询该用户在该菜单下的所有功能
     * @param menuIdentify
     * @param userId
     * @return
     */
    public abstract List<Map<String,Object>> queryFuncByMenuIdentifyUserId( String menuIdentify ,Integer userId ) throws PlatformException;

    /**
     * 查询常量下拉框数据
     * @param namespace
     * @return
     * @throws PlatformException
     */
    public abstract List<Map<String,Object>> loadComboboxDataConstant( String namespace ) throws PlatformException;
    /**
     * 暂时放弃次方法。
     * 查询树节点数据
     * @return
     * @throws PlatformException
     */
    public abstract List<Map<String,Object>> loadTreeNodeDataMap(String loadDataMethodName)  throws PlatformException;

}
