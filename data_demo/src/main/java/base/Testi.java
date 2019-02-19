package base;

/**
     * 注意i++和++i的区别：
     * i++  :  先赋值，后相加
     * ++i  :  先相加，后赋值
     *
     * 两者在循环没有什么很大的区别
 *
 *
 1、先要注意，count是静态变量，被所有对象共享
 2、接着要明白count++与++count的区别，前者是先赋值后相加，后者是先相加后赋值
 3、第一次a.increment()里的count是先赋值后相加，所以会先返回0，接着后相加，此时静态变量count为1。
 4、第二次a.anotherIncrement()里的count是先相加后赋值，所以相加后count为2，接着返回2，此时静态变量count为2。
 5、第三次a.increment()里的count是先赋值后相加，所以会先返回2，接着后相加，此时静态变量count为3。
 * */


public class Testi {
    public static void main(String[] args)
    {
        Counter a = new Counter();
        System.out.println(a.increment());
        System.out.println(a.anotherIncrement());
        Counter b = new Counter();
        System.out.println(b.increment());
    }
}
class Counter
{
    private static int count = 0;
    public int increment()
    {
        return count++;
    }
    public int anotherIncrement()
    {
        return ++count;
    }
}

