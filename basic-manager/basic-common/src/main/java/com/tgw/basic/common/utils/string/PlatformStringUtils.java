package com.tgw.basic.common.utils.string;


import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by zjg on 2017/8/7.
 */
public class PlatformStringUtils {

    /**
     * 第一个字母转为大写
     * @param old
     * @return
     */
    public static String firstLetterToUpperCase(String old){
        String str=old.substring(0,1).toUpperCase()+old.substring(1);
        return str;
    }

    /**
     * 将逗号分隔的key转为逗号分隔的name
     * 例：将  110000,110105,110108  转为  北京市,朝阳区,海淀区
     * @param keys
     * @param map
     * @return
     */
    public static String strKeyToName(String keys, Map<String,Object> map){
        StringBuffer resStr = new StringBuffer();
        if( StringUtils.isBlank( keys ) || map==null || map.size()==0 ){
            return resStr.toString();
        }

        String[] keyArray = keys.split(",");
        for( int i=0;i<keyArray.length;i++ ){
            if( map.containsKey( keyArray[i] ) ){
                resStr.append( map.get( keyArray[i] ).toString()+"," );
            }else{
                resStr.append( keyArray[i]+"," );
            }
        }
        if( resStr.length()>0 ){
            resStr.deleteCharAt( resStr.length()-1 );
        }

        return resStr.toString();
    }

}
