package test;

/**
 * 面向对象笔试题1
 * 主要内容：
 * 父类与子类内部方法和属性的加载顺序
 */
public class LetCode01 {

    public static void main(String[] args) {
        Animal animal = new Dog();

        Dog dog = (Dog) new Animal();    //子类的引用指向父类需要强制类型转换
    }
}

class Animal{

    private String name = "Animal";

    public Animal() {
        a();                    //调用的是子类的方法,非静态代码块，此时非静态属性还没有加载，所以获取的结果为null
    }

    public void a(){
        System.out.println("父类的a方法 "+name);
    }
}

class Dog extends Animal{
    private String name = "Dog";

    @Override
    public void a() {
        System.out.println("子类的a方法 "+name);
    }
}

