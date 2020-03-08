package com.tgw.basic.example.exampleBeanFormVal.service;


import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.framework.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * Created by lee on 2017/03/25.
 */
public interface ExampleBeanFormValService extends BaseService {

   /**
     * 查询行政区划树节点数据
     * @return
     * @throws PlatformException
     */
    public abstract List<Map<String,Object>> queryDistrictTreeMap()  throws PlatformException;

}
