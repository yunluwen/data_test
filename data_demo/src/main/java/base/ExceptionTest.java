package base;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 异常处理
 *
 * try-catch遇到循环时，将try代码块放在循环内还是循环外的选择:
 *      1、当循环内的代码出现异常，需要结束循环时，将try代码块放在循环外;
 *      2、当循环内的代码出现异常，需要继续执行循环时，将try代码块放在循环内。
 */
public class ExceptionTest {

    @Test
    public void test1(){
        try {      //出现异常循环就终止执行
            for (int i = 0; i < 10; i++) {
                int j = 2/i;
                System.out.println(i);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        for (int i = 0; i < 10; i++) {
            try {  //出现异常不影响循环的执行
                int j = 2 / i;
                System.out.println(i);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        //异常不处理，出现异常，终止程序的执行
        for (int i = 0; i < 10; i++) {
            int j = 2/i;
            System.out.println(i);
        }


    }

    public static void main(String[] args) {
//        test2();
//        test4();
        //test5();
        try {
            test6();
        }catch (Exception e){
            System.out.println("收到了异常信息：");
            e.printStackTrace();
        }

    }

    public static String test2() {
        try {
            System.out.println("try block");
            return test3();
        } finally {
            System.out.println("finally block");
            // return "finally";
        }
    }

    public static String test3() {
        System.out.println("return statement");
        return "after return";
    }

    /**
     * try throw catch的组合使用
     */
    public static void test4(){
        try{
            int i = 3;
            if(i>2){
                throw new RuntimeException("抛出异常1");   //这个时候就不会往下面执行了
            }
            System.out.println("抛出异常1后。。。");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void test5(){
        List<String> errorList = new ArrayList<>();

        for(int i=0;i<3;i++) {
            try {
                throw new RuntimeException("aaaa" + i);
            } catch (Exception e) {
                errorList.add(e.getMessage());
//                System.out.println(e.toString());
                //e.printStackTrace();
            }
        }

        if(!errorList.isEmpty()){
            System.out.println(errorList);
        }
    }

    public static void test6() throws Exception{
        try {
            System.out.println("hahaha");
            throw new RuntimeException("lalala");
        }catch (Exception e){
            //e.printStackTrace();
            throw e;
        }
    }

}
