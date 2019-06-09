package com.tgw.basic.system.constant.service.impl;

import com.tgw.basic.framework.service.impl.BaseServiceImpl;
import com.tgw.basic.system.constant.dao.SysEnConstantMapper;
import com.tgw.basic.system.constant.service.SysEnConstantService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * Created by zhaojg on 2016/10/16.
 */
@Service("sysEnConstantService")
public class SysEnConstantServiceImpl extends BaseServiceImpl implements SysEnConstantService {

    @Resource
    private SysEnConstantMapper sysEnConstantMapper;

    @Override
    public void initMapper() {
        /**
         * 具体业务层必须调用
         */
        if( null!= sysEnConstantMapper){
            super.setBaseModelMapper( this.getSysEnConstantMapper() );
        }
    }

    public List<Map<String, Object>> loadConstantByNamespace(String namespace) {
        return this.getSysEnConstantMapper().loadConstantByNamespace(namespace);
    }

    public SysEnConstantMapper getSysEnConstantMapper() {
        return sysEnConstantMapper;
    }

    public void setSysEnConstantMapper(SysEnConstantMapper sysEnConstantMapper) {
        this.sysEnConstantMapper = sysEnConstantMapper;
    }
}
