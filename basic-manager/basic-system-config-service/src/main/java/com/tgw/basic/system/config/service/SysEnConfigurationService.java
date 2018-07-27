package com.tgw.basic.system.config.service;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.framework.service.BaseService;
import com.tgw.basic.system.config.model.SysEnConfiguration;

import java.util.List;
import java.util.Map;

/**
 * Created by lee on 2016/10/16.
 */
public interface SysEnConfigurationService extends BaseService {
    /**
     * 查询系统配置树节点数据
     * @return
     * @throws PlatformException
     */
    public abstract List<Map<String,Object>> queryConfTreeMap()  throws PlatformException;

    /**
     * 根据系统配置的key值加载配置对象。
     * @return
     */
    public SysEnConfiguration querySysEnConfigByKey(String confKey);

    /**
     * 根据系统配置的key值加载value。
     * @param confKey
     * @return
     */
    public String querySysEnConfigValByKey(String confKey);
}
