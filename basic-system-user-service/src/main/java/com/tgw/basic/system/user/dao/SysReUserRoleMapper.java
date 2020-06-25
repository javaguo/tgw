package com.tgw.basic.system.user.dao;


import com.tgw.basic.framework.baseMapper.BaseModelMapper;
import com.tgw.basic.system.role.model.SysEnRole;
import com.tgw.basic.system.user.model.SysReUserRole;

import java.util.List;
import java.util.Map;


public interface SysReUserRoleMapper extends BaseModelMapper<SysReUserRole> {
    /**
     * 根据用户id删除用户拥有的角色
     * @param userId
     */
    public void deleteUserRoleByUserId(Integer userId);

    /**
     * 加载用户角色
     * @param userId
     */
    public List<Map<String,Object>> loadUserRoleByUserId(Integer userId);

    /**
     * 加载用户角色
     * @param userId
     */
    List<SysEnRole> loadSysEnRolesByUserId(Integer userId);
}


