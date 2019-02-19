package new_type.optional;

import new_type.optional.Car;
import new_type.optional.Person;
import org.junit.Test;
import java.util.Optional;

/**
 * 测试Optional容器，专门用于封装对象,防止空指针异常
 *
 * 一句话小结: 使用 Optional 时尽量不直接调用 Optional.get() 方法,
 * Optional.isPresent() 更应该被视为一个私有方法, 应依赖于其他像
 * Optional.orElse(), Optional.orElseGet(), Optional.map() 等这样的方法.
 */
public class TestOptional {

    /**
     * Optional容器类的常用方法
     * Optional.of(T t) : 创建一个Optional实例
     * Optional.empty() : 创建一个空的Optional实例
     * Optional.ofNullble(T t) : 若t不为null,创建Optional实例，否则创建空实例
     * isPresent() : 判断是否包含值
     * orElse(T t) : 如果调用对象包含值，则返回该值，否则返回t
     * orElseGet(Supplier s) : 如果调用对象包含值，则返回该值，否则返回s 获取的值
     * map(Function f) : 如果有值对其进行处理，并返回处理后的Optional,否则返回Optional.empty()
     * flatMap(Function map) : 与map类似，要求返回值必须是Optional
     *
     */

    @Test
    public void test1(){
        //创建一个Optional实例
        //这个时候如果传入的对象为null值，还是会出现空指针异常
        Optional<Person> op = Optional.of(new Person());
        Person per = op.get();
        System.out.println(per);
    }

    @Test
    public void test2(){
        //构建一个空的Optional
        Optional<Person> op = Optional.empty();
        System.out.println(op.get());     //空的Optional去get,java.util.NoSuchElementException: No value present
    }

    @Test
    public void test3(){
//        Optional<Person> op = Optional.ofNullable(new Person("lisi",34));
        Optional<Person> op = Optional.ofNullable(null);
        if(op.isPresent()) {   //判断Optional是否包含值
            System.out.println(op.get());  //java.util.NoSuchElementException: No value present
        }
        Person per = op.orElse(new Person("zhangsan",33,null));
        System.out.println(per);

        Person per2 = op.orElseGet(() -> new Person());
        System.out.println(per2);
    }

    @Test
    public void test4(){
//        Optional<Person> op = Optional.ofNullable(new Person("wangwu",89));
        Optional<Person> op = Optional.ofNullable(null);
//        Optional<String> str = op.map((p) -> p.getName());
//        System.out.println(str.get());

        Optional<String> str2 = op.flatMap((p) -> Optional.of(p.getName()));
        System.out.println(str2.get()); //如果为空，get的话还是java.util.NoSuchElementException: No value present
    }

    @Test
    public void test5(){
        Optional<Person> opt = Optional.ofNullable(
                new Person("aaa",67,
                        Optional.ofNullable(
                                new Car("Daben","write"))));
        String string = getCarname(opt);
        System.out.println(string);
    }

    public String getCarname(Optional<Person> opt){
        return opt.orElse(new Person())
                .getCar()
                .orElse(new Car("BMW A8","blue"))
                .getName();
    }

}
