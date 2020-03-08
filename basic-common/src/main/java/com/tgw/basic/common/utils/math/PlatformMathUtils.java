package com.tgw.basic.common.utils.math;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * Created by zjg on 2017/7/1.
 */
public class PlatformMathUtils {

    /**
     * 生成0至maxInt-1之间的随机整数
     * @param maxInt
     * @return
     */
    public static int createRandInt(int maxInt){
        Random random = new Random();
        return random.nextInt( maxInt );
    }

    /**
     * 生成000-999之间的一个随机字符串
     * @return
     */
    public static String createRandString999(){
        int randomInt = PlatformMathUtils.createRandInt(1000);
        DecimalFormat decimalFormat = new DecimalFormat("###");
        return decimalFormat.format( randomInt );
    }

}
