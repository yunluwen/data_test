package com.zyh.house_scala.concurrent.publish;

import com.zyh.house_scala.concurrent.annotations.NotThreadSafe;

import java.util.Arrays;

/**
 * 安全发布对象
 * 不安全的方式
 */
@NotThreadSafe
public class UnSafePublish {

    private String[] status = {"a","b","c"};

    public String[] getStatus() {
        return status;
    }

    public static void main(String[] args) {
        UnSafePublish unSafePublish = new UnSafePublish();
        System.out.println(Arrays.toString(unSafePublish.getStatus()));

        unSafePublish.getStatus()[0] = "d";
        System.out.println(Arrays.toString(unSafePublish.getStatus()));
    }

}
