package base;

/**
 * 反射
 */
public class Reflection {

    ClassLoader classLoader = this.getClass().getClassLoader();

    public static void main(String[] args) throws ClassNotFoundException {
        //1
        Class cla1 = Person.class;
        System.out.println(cla1.getName());

        //2
        Person person = new Person();
        Class cla2 = person.getClass();
        System.out.println(cla2.getName());

        //3
        Class cla3 = Class.forName("base.Person");
        System.out.println(cla3.getName());

        //4
        //通过类加载器
        Reflection reflection = new Reflection();
        Class cla4 = reflection.classLoader.loadClass("base.Person");
        System.out.println(cla4.getName());
    }
}

class Person{

}
