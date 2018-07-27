package com.tgw.basic.framework.dao;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.framework.model.frame.SysEnFrame;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysEnFrameMapper extends BaseModelMapper<SysEnFrame> {

    /**
     * 根据菜单标识查询该菜单的所有功能
     * @param menuIdentify
     * @return
     */
    public abstract List<Map<String,Object>> queryFuncByMenuIdentify(String menuIdentify );

    /**
     * 根据菜单标识及用户id查询该用户在该菜单下的所有功能
     * @param menuIdentify
     * @param userId
     * @return
     */
    public abstract List<Map<String,Object>> queryFuncByMenuIdentifyUserId( @Param("menuIdentify")String menuIdentify , @Param("userId")Integer userId );

    /**
     * 查询常量下拉框数据
     * @param namespace
     * @return
     * @throws PlatformException
     */
    public List<Map<String, Object>> loadComboboxDataConstant( @Param("namespace")String namespace);
}


