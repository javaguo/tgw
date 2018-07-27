package com.tgw.basic.example.exampleBean.service;


import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.framework.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * Created by lee on 2017/03/25.
 */
public interface ExampleBeanService extends BaseService {

    /**
     * 查询行政区划下拉框数据
     * @return
     * @throws PlatformException
     */
    public abstract List<Map<String,Object>> queryDistrictComboBoxMap(Object value)  throws PlatformException;

    /**
     * 查询菜单下拉框数据
     * @return
     * @throws PlatformException
     */
    public abstract List<Map<String,Object>> queryMenuComboBoxMap(Object value) throws PlatformException;

    /**
     * 查询行政区划树节点数据
     * @return
     * @throws PlatformException
     */
    public abstract List<Map<String,Object>> queryDistrictTreeMap()  throws PlatformException;

    /**
     * 查询菜单树节点数据
     * @return
     * @throws PlatformException
     */
    public abstract List<Map<String,Object>> queryMenuTreeMap() throws PlatformException;

    /**
     * 查询所有的行政区划信息
     * @return
     * @throws PlatformException
     */
    public abstract List<Map<String,Object>> queryAllDistrict() throws PlatformException;
}
