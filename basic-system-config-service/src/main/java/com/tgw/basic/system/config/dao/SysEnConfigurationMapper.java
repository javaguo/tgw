package com.tgw.basic.system.config.dao;

import com.tgw.basic.framework.baseMapper.BaseModelMapper;
import com.tgw.basic.system.config.model.SysEnConfiguration;

import java.util.List;
import java.util.Map;

/**
 * Created by zjg on 2017/9/3.
 */
public interface SysEnConfigurationMapper extends BaseModelMapper<SysEnConfiguration> {
    /**
     * 查询系统配置树节点数据
     * @return
     */
    public abstract List<Map<String,Object>> queryConfTreeMap();
}
