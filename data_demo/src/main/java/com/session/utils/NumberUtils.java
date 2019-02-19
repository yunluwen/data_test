package com.session.utils;

import java.math.BigDecimal;

/**
 * 数字格工具类
 * @author zhangyunhao
 *
 */
public class NumberUtils {

    /**
     * 格式化小数
     * @param
     * @param scale 四舍五入的位数
     * @return 格式化小数
     */
    public static double formatDouble(double num, int scale) {
        BigDecimal bd = new BigDecimal(num);
        return bd.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}