package com.tgw.basic.example.exampleBean.service.impl;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.example.exampleBean.dao.ExampleBeanMapper;
import com.tgw.basic.example.exampleBean.service.ExampleBeanService;
import com.tgw.basic.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * Created by zhaojg on 2017/03/25.
 */
@Service("exampleBeanService")
public class ExampleBeanServiceImpl extends BaseServiceImpl implements ExampleBeanService {

    @Resource
    private ExampleBeanMapper exampleBeanMapper;

    @Override
    public void initMapper() {
        System.out.println("ExampleBeanServiceImpl-->initMapper()");
        /**
         * 具体业务service层必须覆写此方法
         */
        if( null!=exampleBeanMapper ){
            super.setBaseModelMapper( this.getExampleBeanMapper() );
        }
    }

    public List<Map<String, Object>> queryDistrictComboBoxMap(Object value) throws PlatformException {
        return this.getExampleBeanMapper().queryDistrictComboBoxMap(value);
    }

    public List<Map<String, Object>> queryMenuComboBoxMap(Object value) throws PlatformException {
        return this.getExampleBeanMapper().queryMenuComboBoxMap(value);
    }

    public List<Map<String,Object>> queryDistrictTreeMap()  throws PlatformException{
        return this.getExampleBeanMapper().queryDistrictTreeMap();
    }

    public List<Map<String, Object>> queryMenuTreeMap() throws PlatformException {
        return this.getExampleBeanMapper().queryMenuTreeMap();
    }

    public List<Map<String, Object>> queryAllDistrict() throws PlatformException {
        return this.getExampleBeanMapper().queryAllDistrict();
    }

    public ExampleBeanMapper getExampleBeanMapper() {
        return exampleBeanMapper;
    }

    public void setExampleBeanMapper(ExampleBeanMapper exampleBeanMapper) {
        this.exampleBeanMapper = exampleBeanMapper;
    }
}
