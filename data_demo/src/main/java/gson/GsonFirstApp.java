package gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * gson入门
 */
public class GsonFirstApp {

    public static void main(String[] args) {
        String jsonString = "{\"name\":\"Maxsu\", \"age\":24}";

        /**
         * 第1步:使用GsonBuilder创建Gson对象
         * 创建一个Gson对象。 它是一个可重用的对象。
         */
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        /**
         * 第2步: 将JSON反序列化为对象
         * 使用fromJson()方法从JSON获取对象。 传递Json字符串/Json字符串的源和对象类型作为参数。
         */
        Student student = gson.fromJson(jsonString, Student.class);
        System.out.println(student);

        /**
         * 第3步: 将对象序列化为JSON
         * 使用toJson()方法获取对象的JSON字符串表示形式。
         */
        jsonString = gson.toJson(student);
        System.out.println(jsonString);
    }

}

class Student {
    private String name;
    private int age;
    public Student(){}

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
    public String toString() {
        return "Student [ name: "+name+", age: "+ age+ " ]";
    }
}

