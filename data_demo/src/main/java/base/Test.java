package base;

import java.math.BigDecimal;
import java.util.List;

/**
 * 测试类
 */
public class Test {

    public static void main(String[] args) {
//        int a = 0;
//        while(a<10){
//            for(int i=0;i<5;i++){   //循环中的循环会执行完
//                System.out.println(i);
//            }
//
//            for(int j=0;j<3;j++){
//                System.out.println(j);
//            }
//            System.out.println("a="+a);
//            a++;
//        }
//
//        "a".equals("b");

//        String a = "log_service.sys_job";
//        String[] arr = a.split("\\.");
//        System.out.println(arr[0]);
//        System.out.println(arr[1]);

        float a2 = 143929778900998f;
        a2 = a2/10000000;
        System.out.println(a2);
        float num= (float)(Math.round(a2*100)/100);
        System.out.println(num);

        BigDecimal b = new BigDecimal(a2);
        float f1 = b.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
        //   b.setScale(2,   BigDecimal.ROUND_HALF_UP)   表明四舍五入，保留两位小数
        System.out.println(f1);

    }



}
