package com.tgw.basic.example.exampleBean.dao;

import com.tgw.basic.example.exampleBean.model.ExampleBean;
import com.tgw.basic.framework.baseMapper.BaseModelMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaojg on 2017/03/25.
 */
public interface ExampleBeanMapper extends BaseModelMapper<ExampleBean> {

    /**
     * 查询下拉框数据接口
     * @param  value  说明：value可以映数据库中的int和varchar类型。
     *                       sql中使用方式如下：[where menu.fk_parent_id = #{value}]或[where menu.qtip = #{value}]
     *                       fk_parent_id为int 类型，qtip为varchar类型。
     * @return
     */
    public abstract List<Map<String,Object>> loadComboboxData(Object value);

    /**
     * 查询行政区划下拉框数据接口
     * @return
     */
    public abstract List<Map<String,Object>> queryDistrictComboBoxMap(Object value);

    /**
     * 查询菜单下拉框数据接口
     * @return
     */
    public abstract List<Map<String,Object>> queryMenuComboBoxMap(Object value);

    /**
     * 查询行政区划树节点数据接口
     * @return
     */
    public abstract List<Map<String,Object>> queryDistrictTreeMap();

    /**
     * 查询菜单树节点数据接口
     * @return
     */
    public abstract List<Map<String,Object>> queryMenuTreeMap();

    /**
     * 查询所有的行政区划信息
     * @return
     */
    public abstract List<Map<String,Object>> queryAllDistrict();

}
