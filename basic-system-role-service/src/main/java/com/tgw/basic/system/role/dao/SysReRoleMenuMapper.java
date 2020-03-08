package com.tgw.basic.system.role.dao;


import com.tgw.basic.framework.dao.BaseModelMapper;
import com.tgw.basic.system.role.model.SysReRoleMenu;

import java.util.List;
import java.util.Map;


public interface SysReRoleMenuMapper extends BaseModelMapper<SysReRoleMenu> {
    /**
     * 根据角色id删除角色拥有的菜单
     * @param roleId
     */
    public void deleteRoleMenuByRoleId(Integer roleId);

    /**
     * 加载角色拥有的菜单
     * @param roleId
     */
    public List<Map<String,Object>> loadRoleMenuByRoleId(Integer roleId );
}


