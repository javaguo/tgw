package com.tgw.basic.framework.dao;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaojg on 2016/10/10.
 */
public interface BaseModelMapper<T> extends Mapper<T>,MySqlMapper<T> {
    /**
     * 查询列表数据接口
     * @return
     */
    public abstract List<Map<String,Object>> searchData(Object object);

    /**
     * 查询树节点
     * @return
     */
    /*public abstract List loadTreeData();*/


}
