package com.tgw.basic.system.role.dao;


import com.tgw.basic.framework.dao.BaseModelMapper;
import com.tgw.basic.system.role.model.SysReRoleFunc;

import java.util.List;
import java.util.Map;


public interface SysReRoleFuncMapper extends BaseModelMapper<SysReRoleFunc> {
    /**
     * 根据角色id删除角色拥有的功能权限
     * @param roleId
     */
    public void deleteRoleFuncByRoleId(Integer roleId);

    /**
     * 加载角色拥有的功能权限
     * @param roleId
     */
    public List<Map<String,Object>> loadRoleFuncByRoleId(Integer roleId );
}


