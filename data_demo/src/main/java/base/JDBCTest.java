package base;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCTest {

    public static Boolean test(String host) {
        /**
         * jdbc设置连接超时时间
         */
        String URL="jdbc:mysql://"+host+":3306?connectTimeout=3000";
        String USER="root";
        String PASSWORD="123456";

        try {
            //1.加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            //2.获得数据库链接
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            return conn != null;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        //System.out.println(test("127.0.0.1"));
        //System.out.println(test("1224234"));
        System.out.println(test("111.111.11.1"));
    }

}
