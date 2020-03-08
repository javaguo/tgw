package com.tgw.basic.framework.model.frame;


import com.tgw.basic.core.model.AbstractBaseBean;

/**
 * 此实体类目前只用于为SysEnFrameMapper设置泛型。暂时没有其它用途。--2017.10.08
 *
 * Created by zhaojg on 2017/10/08.
 */
public class SysEnFrame extends AbstractBaseBean {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
