package com.tgw.basic.common.utils.reflect;


import com.tgw.basic.common.utils.string.PlatformStringUtils;

import java.lang.reflect.Method;

/**
 * Created by zjg on 2017/8/7.
 */
public class PlatformReflectUtils {

    public static void setter(Object obj,String att,Object value,Class<?> type){
        try{
            Method met=obj.getClass().getMethod("set"+ PlatformStringUtils.firstLetterToUpperCase(att),type);
            met.invoke(obj,value);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static Object getter(Object obj,String att){
        try{
            Method met=obj.getClass().getMethod("get"+PlatformStringUtils.firstLetterToUpperCase(att));
            return met.invoke(obj);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
