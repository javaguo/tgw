package com.tgw.basic.system.constant.dao;


import com.tgw.basic.framework.dao.BaseModelMapper;
import com.tgw.basic.system.constant.model.SysEnConstant;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaojg on 2016/10/16.
 */
public interface SysEnConstantMapper extends BaseModelMapper<SysEnConstant> {
    abstract List<Map<String,Object>> loadConstantByNamespace(@Param("namespace")String namespace);
}
