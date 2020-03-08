package com.tgw.basic.system.config.service.impl;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.framework.service.impl.BaseServiceImpl;
import com.tgw.basic.system.config.dao.SysEnConfigurationMapper;
import com.tgw.basic.system.config.model.SysEnConfiguration;
import com.tgw.basic.system.config.service.SysEnConfigurationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * Created by zhaojg on 2016/10/16.
 */
@Service("sysEnConfigurationService")
public class SysEnConfigurationServiceImpl extends BaseServiceImpl implements SysEnConfigurationService {

    @Resource
    private SysEnConfigurationMapper sysEnConfigurationMapper;

    @Override
    public void initMapper() {
        /**
         * 具体业务层必须调用
         */
        if( null!=sysEnConfigurationMapper ){
            super.setBaseModelMapper( this.getSysEnConfigurationMapper() );
        }
    }

    public List<Map<String, Object>> queryConfTreeMap() throws PlatformException {
        return this.getSysEnConfigurationMapper().queryConfTreeMap();
    }

    public SysEnConfiguration querySysEnConfigByKey(String confKey) {
        if(StringUtils.isBlank( confKey )){
            return null;
        }

        SysEnConfiguration config = new SysEnConfiguration();
        config.setConfKey( confKey );
        List<SysEnConfiguration> resList = this.getSysEnConfigurationMapper().select( config );
        if( resList!=null && !resList.isEmpty() ){
            if( resList.size()==1 ){
                return resList.get(0);
            }else{
                throw new PlatformException("找到重复的配置项！");
            }
        }else{
            return null;
        }
    }

    public String querySysEnConfigValByKey(String confKey) {
        SysEnConfiguration res = this.querySysEnConfigByKey( confKey );
        if( res!=null && StringUtils.isNotBlank( res.getConfValue() ) ){
            return res.getConfValue().trim();
        }
        return null;
    }

    public SysEnConfigurationMapper getSysEnConfigurationMapper() {
        return sysEnConfigurationMapper;
    }

    public void setSysEnConfigurationMapper(SysEnConfigurationMapper sysEnConfigurationMapper) {
        this.sysEnConfigurationMapper = sysEnConfigurationMapper;
    }
}
