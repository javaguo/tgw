package com.tgw.basic.example.exampleBeanFormVal.service.impl;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.example.exampleBeanFormVal.dao.ExampleBeanFormValMapper;
import com.tgw.basic.example.exampleBeanFormVal.service.ExampleBeanFormValService;
import com.tgw.basic.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * Created by zhaojg on 2017/03/25.
 */
@Service("exampleBeanFormValService")
public class ExampleBeanFormValServiceImpl extends BaseServiceImpl implements ExampleBeanFormValService {

    @Resource
    private ExampleBeanFormValMapper exampleBeanFormValMapper;

    @Override
    public void initMapper() {

        if( null!=exampleBeanFormValMapper ){
            super.setBaseModelMapper( this.getExampleBeanFormValMapper() );
        }
    }

    public List<Map<String,Object>> queryDistrictTreeMap()  throws PlatformException {
        return this.getExampleBeanFormValMapper().queryDistrictTreeMap();
    }

    public ExampleBeanFormValMapper getExampleBeanFormValMapper() {
        return exampleBeanFormValMapper;
    }

    public void setExampleBeanFormValMapper(ExampleBeanFormValMapper exampleBeanFormValMapper) {
        this.exampleBeanFormValMapper = exampleBeanFormValMapper;
    }
}
