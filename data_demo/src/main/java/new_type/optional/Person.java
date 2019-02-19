package new_type.optional;

import new_type.optional.Car;

import java.io.Serializable;
import java.util.Optional;

/**
 * 可以实现序列化
 */
public class Person implements Serializable {

    private String name;
    private int age;

    //注意这里，一定要初始化为Optional.empty,否则默认为null,会出现空指针
    private Optional<Car> car = Optional.empty();

    public Person() {
    }

    public Person(String name, int age, Optional<Car> car) {
        this.name = name;
        this.age = age;
        this.car = car;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Optional<Car> getCar() {
        return car;
    }

    public void setCar(Optional<Car> car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "[" + this.name + "\t" + this.age + "]";
    }
}
