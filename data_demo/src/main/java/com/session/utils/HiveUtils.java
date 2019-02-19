package com.session.utils;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
/**
 * hive JDBC
 */
public class HiveUtils {

    private static String driverName = "com.mysql.jdbc.Driver";
    private Connection conn;
    private String url;
    private String user;
    private String password;

    public HiveUtils(String url, String user, String password){
        this.url = url;
        this.user = user;
        this.password = password;
    }

    private boolean login(){
        try {

            Class.forName(driverName);

            try {
                //建立连接
                conn = DriverManager.getConnection(this.url, user, password);
                if (conn != null){
                    return true;
                }else{
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }


    public String exec_auth_cmd(String command) throws SQLException {
        try{
            if (login()){
                StringBuilder sb = new StringBuilder();
                Statement stmt = conn.createStatement();
                // System.out.println("start");
                ResultSet rs = stmt.executeQuery(command);
                while(rs.next()){
                    sb.append(rs.getString(1));
                    sb.append('\n');
                }
                stmt.close();
                return sb.toString();
            }
        } finally {
            if (conn != null){
                conn.close();
            }
        }
        return "";
    }



    public static void main(String[] args) throws SQLException {
        HiveUtils tec = new HiveUtils(
                "jdbc:mysql://localhost:3306/",
                "root", "123456");

        System.out.println(
                //查询所有的库表信息
                tec.exec_auth_cmd(
                        "select concat_ws('.',t1.schema_name,t2.table_name) from (select schema_name from information_schema.schemata) t1 join " +
                                "(select table_name,table_schema from information_schema.tables " +
                                "where table_type = 'base table' and table_schema != 'performance_schema') t2 on " +
                                "t1.schema_name = table_schema"));

//        "select table_name,table_schema from information_schema.tables";
//        "select * (select schema_name from information_schema.schemata) t1 join " +
//                "(select table_name,table_schema from information_schema.tables) t2 on " +
//                "t1.schema_name = table_schema";
    }

}
