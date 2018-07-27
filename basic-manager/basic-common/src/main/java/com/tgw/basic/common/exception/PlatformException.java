package com.tgw.basic.common.exception;

/**
 * Created by zhaojg on 2017/3/13.
 */
public class PlatformException extends  RuntimeException {

    private String msg;

    public PlatformException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
