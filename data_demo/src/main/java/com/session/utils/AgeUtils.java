package com.session.utils;

public class AgeUtils {
    /**
     * param:age:年龄范围,String:num-num
     */
    public static Boolean isFormat(String ages){
        //返回值
        Boolean is_format = false;
        //如果ages=""，则符合规范
        if(ages.equals("")){
            is_format = true;
        }else {
            //先分割字符串，用"-"
            String[] nums = ages.split("-");
            //判断nums的长度，如果不等于2，则不符合规范
            if (2 != nums.length) {
                is_format = false;
            } else {
                //获取两数字，比较大小，判断是否左边的数小于右边的数
                int num1 = Integer.valueOf(nums[0]).intValue();
                int num2 = Integer.valueOf(nums[1]).intValue();
                if (num1 < num2) {
                    //左边的数小于右边的数，则符合规范
                    is_format = true;
                }
            }
        }
        return is_format;
    }
}