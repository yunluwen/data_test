package test;

/**
 * 测试java中面向对象
 * 父类和子类的属性和类的加载顺序
 *
 * 在JVM调用mian方法之前先用进行静态内容的初始化。顺序为：[父类的静态变量， 父类的静态代码块 (这两个部分是按照编写的顺序执行的)]，
 *                                                [子类的静态变量，子类的静态代码块]。
 *
 * 顺序如下：
 *
 * 1. 父类的静态代码块。（存在多个静态代码块的话按照编写顺序由上至下依次执行）
 *
 * 2. 父类的静态属性。（存在多个静态属性的话按照编写顺序由上至下依次执行）       //注意:静态代码块和静态属性的顺序是按照编写顺序由上至下依次执行，注意这一个部分，千万不要混淆
 *
 * 3. 子类的静态代码块。（存在多个静态代码块的话按照编写顺序由上至下依次执行）
 *
 * 4. 子类的静态属性。（存在多个静态属性的话按照编写顺序由上至下依次执行）
 *
 * 5. 父类的非静态代码块。（存在多个非静态代码块的话按照编写顺序由上至下依次执行）
 *
 * 6. 父类的非静态属性。（存在多个非静态属性的话按照编写顺序由上至下依次执行）
 *
 * 7. 父类的构造函数。
 *
 * 8. 子类的非静态代码块。（存在多个非静态代码块的话按照编写顺序由上至下依次执行）
 *
 * 9. 子类的非静态属性。（存在多个非静态属性的话按照编写顺序由上至下依次执行）
 *
 * 10. 子类的构造函数。
 *
 * 主要遵循这么一条规则:静态早于非静态的同时父类早于子类。
 */
public class LetCode02 {

    public static void main(String[] args) {
        SubClass subClass = new SubClass();
    }

}

class SuperClass {

    public String property1 = initProperty(1);
    public static String staticProperty1 = initStaticProperty(1);
    public String property2 = initProperty(2);
    public static String staticProperty2 = initStaticProperty(2);
    public String property3 = initProperty(3);
    public static String staticProperty3 = initStaticProperty(3);

    {
        System.out.println("这是父类的非静态代码块1");
    }

    static {
        System.out.println("这是父类的静态代码块1");
    }

    {
        System.out.println("这是父类的非静态代码块2");
    }

    static {
        System.out.println("这是父类的静态代码块2");
    }

    {
        System.out.println("这是父类的非静态代码块3");
    }

    static {
        System.out.println("这是父类的静态代码块3");
    }


    public String initProperty(int order) {
        System.out.println("这是父类的非静态属性" + order);
        return "这是父类的非静态属性" + order;
    }

    public static String initStaticProperty(int order) {
        System.out.println("这是父类的静态属性" + order);
        return "这是父类的静态属性" + order;
    }

    SuperClass() {
        System.out.println("这是父类的构造函数");
    }

}

class SubClass extends SuperClass{


    {
        System.out.println("这是子类的非静态代码块1");
    }

    static {
        System.out.println("这是子类的静态代码块1");
    }

    {
        System.out.println("这是子类的非静态代码块2");
    }

    static {
        System.out.println("这是子类的静态代码块2");
    }

    {
        System.out.println("这是子类的非静态代码块3");
    }

    static {
        System.out.println("这是子类的静态代码块3");
    }

    public String property1 = initProperty(1);
    public static String staticProperty1 = initStaticProperty(1);
    public String property2 = initProperty(2);
    public static String staticProperty2 = initStaticProperty(2);
    public String property3 = initProperty(3);
    public static String staticProperty3 = initStaticProperty(3);

    public String initProperty(int order) {
        System.out.println("这是子类的非静态属性" + order);
        return "这是子类的非静态属性" + order;
    }

    public static String initStaticProperty(int order) {
        System.out.println("这是子类的静态属性" + order);
        return "这是子类的静态属性" + order;
    }

    SubClass() {
        System.out.println("这是子类的构造函数");
    }

}
