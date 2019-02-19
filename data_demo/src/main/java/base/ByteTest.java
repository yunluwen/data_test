package base;

public class ByteTest {

    public static void main(String[] args) {
        Byte a = 127;            //Byte的范围-128 - 127，超出范围为负值
        System.out.println(a++); //127
        System.out.println(a++);  //-128
        System.out.println(a++);   //-127
    }
}
