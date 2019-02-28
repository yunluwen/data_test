package com.zyh.house_scala.concurrent.publish;


/**
 * 对象溢出
 */
public class Escape {

    private int thisEscape = 9999;

    public Escape() {
        new InnerClass();
    }

    private class InnerClass {

        public InnerClass(){
            System.out.println(Escape.this.thisEscape);
        }
    }

    public static void main(String[] args) {
        new Escape();
    }
}
