package com.tgw.basic.example.exampleBeanFormVal.dao;


import com.tgw.basic.example.exampleBeanFormVal.model.ExampleBeanFormVal;
import com.tgw.basic.framework.baseMapper.BaseModelMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaojg on 2017/08/05.
 */
public interface ExampleBeanFormValMapper extends BaseModelMapper<ExampleBeanFormVal> {

    /**
     * 查询行政区划树节点数据接口
     * @return
     */
    public abstract List<Map<String,Object>> queryDistrictTreeMap();


}
