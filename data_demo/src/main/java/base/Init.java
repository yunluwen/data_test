package base;

/**
 * 初始化一般遵循3个原则：
 *
 * 静态对象（变量）优先于非静态对象（变量）初始化，静态对象（变量）只初始化一次，而非静态对象（变量）可能会初始化多次；
 * 父类优先于子类进行初始化；
 * 按照成员变量的定义顺序进行初始化。 即使变量定义散布于方法定义之中，它们依然在任何方法（包括构造函数）被调用之前先初始化；
 * 加载顺序
 *
 * 父类（静态变量、静态语句块）
 * 子类（静态变量、静态语句块）
 * 父类（实例变量、普通语句块）
 * 父类（构造函数）
 * 子类（实例变量、普通语句块）
 * 子类（构造函数）
 */
class Base {
    // 1.父类静态代码块
    static {
        System.out.println("Base static block!");
    }
    // 3.父类非静态代码块
    {
        System.out.println("Base block");
    }
    // 4.父类构造器
    public Base() {
        System.out.println("Base constructor!");
    }
}

public class Init extends Base {
    // 2.子类静态代码块
    static{
        System.out.println("Derived static block!");
    }
    // 5.子类非静态代码块
    {
        System.out.println("Derived block!");
    }
    // 6.子类构造器
    public Init() {
        System.out.println("Derived constructor!");
    }

    public static void main(String[] args) {
        new Init();
    }

}