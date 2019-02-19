package datastatus.line;

import datastatus.line.linkedlist.two.Node;

public class ObjectTest {
    public static void main(String[] args) {
        Person person = new Person("zhangshan",89);
        Person person2 = person;
        System.out.println(person == person2);   //true

        Node<Integer> node = new Node<Integer>(1,null,null);
        Node<Integer> node2 = new Node<Integer>(2,null,null);
        Node<Integer> node3 = new Node<Integer>(3,null,null);
        node.next = node2;
        node2.pre = node;
        node2.next = node3;
        node3.pre = node2;   //datastatus.line.linkedlist.two.Node@27082746
        System.out.println(node3);  //datastatus.line.linkedlist.two.Node@27082746
        //node2.pre.next=node3;
        System.out.println(node3);  //datastatus.line.linkedlist.two.Node@27082746
        System.out.println(node2.pre.next==node2);     //true
        System.out.println(node2);  //datastatus.line.linkedlist.two.Node@66133adc
    }
}

class Person{
    private String name;
    private int age;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}