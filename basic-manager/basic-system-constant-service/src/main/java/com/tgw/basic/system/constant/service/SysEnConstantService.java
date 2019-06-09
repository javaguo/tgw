package com.tgw.basic.system.constant.service;

import com.tgw.basic.framework.service.BaseService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by lee on 2016/10/16.
 */
public interface SysEnConstantService extends BaseService {
    /**
     * 加载指定命名空间常量
     * @param namespace
     * @return
     */
    abstract List<Map<String,Object>> loadConstantByNamespace(@Param("namespace")String namespace);
}
