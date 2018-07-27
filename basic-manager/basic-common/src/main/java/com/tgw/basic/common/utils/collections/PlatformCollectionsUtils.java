package com.tgw.basic.common.utils.collections;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.common.utils.reflect.PlatformReflectUtils;
import com.tgw.basic.common.utils.string.PlatformStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by zjg on 2017/8/7.
 */
public class PlatformCollectionsUtils {
    /**
     * 将List集合中对象的某个属性的值作为key值，将对象作为value值，组装成map返回
     * @param   list 对象集合
     * @param   attributeName 对象的属性名
     * @return  Map<Object, Object>
     *          key为对象中的一个属性值，value为对象
     */
    public static Map<Object, Object> convertObjectToMap(List list, String attributeName) {

        Map<Object, Object> map = new HashMap<Object, Object>();
        for (Object object : list) {
            try {
                Method met=object.getClass().getMethod("get"+ PlatformStringUtils.firstLetterToUpperCase(attributeName));
                map.put(met.invoke(object), object);
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return map;
    }

    /**
     * 将List集合中对象的某几个属性的值作为key值，将对象作为value值，组装成map返回
     * @param list  对象集合
     * @param attributeNames 对象的属性名   例：String[] attributeNames={"regNo","peMajor.name","peSite.peSite.name".....};
     * @return
     * @throws PlatformException
     */
    public static Map<Object, Object> convertObjectToMapAttsAsKey(List list,String[] attributeNames) throws PlatformException {
        Map<Object, Object> map = new HashMap<Object, Object>();
        for (Object object : list) {
            StringBuffer strKey=new StringBuffer();
            for( int i=0;i<attributeNames.length;i++ ){
                strKey.append( acquireObjAttributeValue(object, attributeNames[i])  );
            }
            map.put( strKey.toString(), object );
        }

        return map;

    }


    /**
     * 递归获取某实体的属性
     * @param obj  实体类  例：peStudent
     * @param attributeName 属性名   格式：attribute.attribute.attribute.......  可以是多层属性
     * @return 返回最后一层属性的值
     *    例：acquireObjAttributeValue(student,"major.majorName")
     *    返回学生所属专业的名称   major为student的属性,majorName为major的属性
     * @throws PlatformException
     */
    public static Object acquireObjAttributeValue(Object obj,String attributeName) throws PlatformException{
        if( null==obj ){
            throw new PlatformException("参数obj对象为空");
        }

        if( !attributeName.contains(".") ){//最内层的属性
            try {

                Method met=obj.getClass().getMethod("get"+PlatformStringUtils.firstLetterToUpperCase( attributeName ));
                return met.invoke(obj);//最内层的属性，不再需要递归调用，返回值

            } catch (SecurityException e) {
                e.printStackTrace();
                throw new PlatformException("获取属性异常：SecurityException");
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                throw new PlatformException("获取属性异常：IllegalArgumentException");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                throw new PlatformException("获取属性异常：NoSuchMethodException");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new PlatformException("获取属性异常：IllegalAccessException");
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                throw new PlatformException("获取属性异常：InvocationTargetException");
            }
        }else{
            try {

                int firstPointIndex=attributeName.indexOf(".");//第一个点(.)出现的索引
                String tempAttribute=attributeName.substring(0, firstPointIndex);//当前第一层属性
                Method met=obj.getClass().getMethod("get"+PlatformStringUtils.firstLetterToUpperCase( tempAttribute ));
                Object tempOjb=met.invoke(obj);//获取到属性的值
                return acquireObjAttributeValue( tempOjb, attributeName.substring( firstPointIndex+1 ) );//继续递归获取属性内部的值

            } catch (SecurityException e) {
                e.printStackTrace();
                throw new PlatformException("递归获取属性异常：SecurityException");
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                throw new PlatformException("递归获取属性异常：IllegalArgumentException");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                throw new PlatformException("递归获取属性异常：NoSuchMethodException");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new PlatformException("递归获取属性异常：IllegalAccessException");
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                throw new PlatformException("递归获取属性异常：InvocationTargetException");
            }
        }

    }

    /**
     * 将List<Map<Object,Object>> 形式的数据转为Map<Object,Object>
     * @param list
     * @param keyName
     * @param valueName
     * @return
     */
    public static Map<String,Object> convertListMapToMap(List<Map<String,Object>> list, String keyName,String valueName){
        Map<String, Object> map = new HashMap<String, Object>();
        if( null!=list ){
            for( Map<String,Object> tempMap:list ){
                if( tempMap.containsKey( keyName )  && tempMap.containsKey( valueName ) ){
                    map.put( tempMap.get( keyName ).toString(),tempMap.get(valueName) );
                }
            }
        }
        return map;
    }

    /**
     * 将List<Map<Object,Object>> 集合中map的某一属性抽取出来，放到List<Object>中
     * @param list
     * @param keyName
     * @return
     */
    public static List<Object>  extractMapValToList( List<Map<String,Object>> list, String keyName ){
        List<Object> res = new ArrayList<Object>();
        if( null!=list ){
            for( Map<String,Object> tempMap:list ){
                if( tempMap.containsKey( keyName ) ){
                    res.add( tempMap.get( keyName ) );
                }
            }
        }
        return  res;
    }

    /**
     * 将List<Object>转为字符串，多个值用英文逗号分隔
     * @param list
     * @return
     */
    public static String listToStr( List<Object> list){
        StringBuffer str = new StringBuffer();

        if( list!=null && list.size()>0 ){
            for( Object obj :list  ){
                str.append( obj.toString()+"," );
            }
            str.deleteCharAt( str.length()-1 );
        }

        return str.toString();
    }

    /**
     * 将list集合中的对象的某个属性提取出来，转换成List形式
     * @param list
     * @param attributeName
     * @return
     */
    public static List extractObjAttriToList(List list,String attributeName){
        List fieldList=null;

        if( list!=null&&!StringUtils.isBlank(attributeName) ){
            fieldList=new ArrayList();
            Iterator ite=list.iterator();
            while(ite.hasNext()){
                Object obj=ite.next();
                fieldList.add( PlatformReflectUtils.getter(obj,attributeName) );
            }
        }

        return fieldList;
    }

    /**
     * 将list集合中的对象的某个属性提取出来，转换成List<Object>形式
     * @param list
     * @param attributeName
     * @return
     */
    public static List<Object> extractAttriToList(List list, String attributeName){
        List<Object> fieldList=null;

        if( list!=null&&!StringUtils.isBlank(attributeName) ){
            fieldList=new ArrayList<Object>();
            Iterator ite=list.iterator();
            while(ite.hasNext()){
                Object obj=ite.next();
                fieldList.add( PlatformReflectUtils.getter(obj,attributeName) );
            }
        }

        return fieldList;
    }


    /**
     * 将List<Object[]>转成List<Map<Object,Object>>
     * 例：从数据库中查出的结果集转成json串后为如下格式":["张三","数学","90"],["李四","语文","80"]
     *    要将查询出的结果集转成如下格式的json串：[{"studentName":"张三","courseName":"数学","score":"90"},
     *                                {"studentName":"李四","courseName":"语文","score":"80"}]
     *    类似此种情况可以使用此函数
     * @param list
     * @param attributeArray 例： {"studentName","courseName","score"}
     * @return
     * @throws PlatformException
     */
    public static List<Map<Object,Object>> convertObjArrayToMap(List<Object[]> list,Object[] attributeArray) throws PlatformException{
        List<Map<Object,Object>> resultList=new ArrayList<Map<Object,Object>>();

        if( null==attributeArray || attributeArray.length<1 ){
            throw new PlatformException("attributeArray参数错误");
        }

        if( null==list||list.size()<1 ){
            throw new PlatformException("list参数错误");
        }

        Iterator ite=list.iterator();
        while( ite.hasNext() ){
            Object[] temp=(Object[])ite.next();
            if( temp.length!=attributeArray.length ){
                continue;
            }

            Map<Object,Object> map=new HashMap<Object,Object>();
            for( int i=0;i<attributeArray.length;i++ ){
                map.put( attributeArray[i], temp[i] );
            }
            resultList.add(map);
        }

        return resultList;
    }

    /**
     * 将List<Object[]>形式中的Object[]数组的指定下标的列提取出来,转换成List<String>形式
     * @param list
     * @param strIndex
     * @return
     */
    public static List<String> extractColumnToList(List<Object[]> list,int strIndex){
        List<String> idList=new ArrayList<String>();

        if(list!=null&&list.size()>0){
            Iterator ite=list.iterator();
            while(ite.hasNext()){
                try {
                    Object[] obj=(Object[])ite.next();
                    if(obj!=null&&obj.length>strIndex){
                        idList.add(obj[strIndex].toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        return idList;
    }

    /**
     * 将list<String[]> 集合中某几个索引的值作为key，某几个索引的值作为value返回map
     * @param list  Object[]数组
     * @param keyIndex  数组中哪几个索引的值作为key值，如果有多个，用逗号分隔
     * @param valueIndex 数组中哪几个索引的值作为value值，如果有多个，用逗号分隔
     * @return
     * @throws PlatformException
     */
    public static Map<String,String> convertListToMapAttsAsKey( List<Object[]> list ,String keyIndex,String valueIndex ) throws PlatformException{
        Map<String,String> map = new HashMap<String,String>();

        if( null == list || list.size()<1 ){
            throw new PlatformException("数据转换失败，没有要转换的数据！");
        }

        if( StringUtils.isBlank( keyIndex ) || StringUtils.isBlank(valueIndex) ){
            throw new PlatformException("数据转换失败，参数错误！");
        }

        String[] keyIndexArray = keyIndex.split(",");
        String[] valueIndexArray = valueIndex.split(",");

        for( int i=0;i<list.size();i++ ){
            Object[] tempStr = list.get(i);

            //组合key值
            StringBuffer tempKey = new StringBuffer();
            for( int m=0;m<keyIndexArray.length;m++ ){

                int tempIndex = Integer.parseInt( keyIndexArray[m] );

                if( m<keyIndexArray.length-1 ){
                    tempKey.append( tempStr[tempIndex].toString()+",");
                }else{
                    tempKey.append( tempStr[tempIndex].toString());
                }

            }

            //组合value值
            StringBuffer tempValue = new StringBuffer();
            for( int n=0;n<valueIndexArray.length;n++ ){

                int tempIndex = Integer.parseInt( valueIndexArray[n] );

                if( n<valueIndexArray.length-1 ){
                    tempValue.append( tempStr[tempIndex].toString()+"," );
                }else{
                    tempValue.append( tempStr[tempIndex].toString() );
                }

            }

            map.put( tempKey.toString() , tempValue.toString() );
        }

        return map;
    }

}
