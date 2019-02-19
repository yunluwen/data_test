package base;

/**
 * 深拷贝和浅拷贝
 */
public class Clone {

    public static void main(String[] args) throws Exception{
        FatherClass a = new FatherClass();
        a.name = "zhangsan";
        a.age = 89;
        ChildClass ca = new ChildClass();
        a.child = ca;

        FatherClass b = (FatherClass) a.clone();
        System.out.println(a == b);
        System.out.println(a.hashCode());
        System.out.println(b.hashCode());
        //浅拷贝和深拷贝对于基本数据类型都是值传递
        System.out.println(a.name.hashCode()+"\t"+a.age);
        System.out.println(b.name.hashCode()+"\t"+a.age);
        //浅拷贝：对基本数据类型进行值传递，对引用数据类型进行引用传递般的拷贝，此为浅拷贝。
        //A 和 B 的 child 对象，实际上还是指向了同一个对象，只对对它的引用进行了传递
        System.out.println(a.child == b.child);   //只拷贝引用，不拷贝对象
        System.out.println(a.child.hashCode());
        System.out.println(b.child.hashCode());

        System.out.println("-------------------");
        FatherClass2 f2 = new FatherClass2();
        f2.name = "zhangsanji";
        f2.age = 99;
        ChildClass ca2 = new ChildClass();
        f2.child = ca2;
        FatherClass2 f3 = (FatherClass2) f2.clone();
        System.out.println(f2 == f3);
        //深拷贝：对基本数据类型进行值传递，对引用数据类型，创建一个新的对象，并复制其内容，此为深拷贝。
        //可以看到，对 child 也进行了一次拷贝，这实则是对 ChildClass 进行的浅拷贝，但是对于 FatherClass 而言，则是一次深拷贝。
        System.out.println(f2.child.hashCode());
        System.out.println(f3.child.hashCode());
    }

}

class FatherClass implements Cloneable{

    public String name;
    public int age;
    public ChildClass child;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        //浅拷贝
        return super.clone();
    }
}

class FatherClass2 implements Cloneable{

    public String name;
    public int age;
    public ChildClass child;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        //深拷贝
        FatherClass2 cf = (FatherClass2) super.clone();
        cf.child = (ChildClass) this.child.clone();
        return cf;
    }
}

class ChildClass implements Cloneable{
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}