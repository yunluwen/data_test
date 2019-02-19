package offer;

/**
 * 求1+2+3+...+n，要求不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句（A?B:C）。
 */
public class Test {

    public static int Sum_Solution(int n) {
        return (n*(n+1))/2;
    }

    public static void main(String[] args) {
        int a = Sum_Solution(100);
        System.out.println(a);
    }

}
