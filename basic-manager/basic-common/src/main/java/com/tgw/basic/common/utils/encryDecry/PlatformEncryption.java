package com.tgw.basic.common.utils.encryDecry;

import com.tgw.basic.common.exception.PlatformException;

import java.security.MessageDigest;

/**
 * Created by zjg on 2017/10/7.
 *
 * 加密相关工具方法
 */
public class PlatformEncryption {

    public static String MD5(String s) throws PlatformException {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte b = md[i];
                str[k++] = hexDigits[b >> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }
            return new String(str);
        } catch (Exception e){
            e.printStackTrace();
            throw new PlatformException("md5加密出错");
        }
    }

}

