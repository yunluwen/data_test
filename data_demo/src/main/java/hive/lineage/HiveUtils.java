package hive.lineage;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class HiveUtils {

    private String driver = "";
    private String url = "";
    private String user = "";
    private String password = "";

    private Connection conn = null;

    public HiveUtils(String driver,String url,String user,String password) {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    private Boolean login() {
        authKrbs();   //线上环境加上kerbos验证
        try {
            Class.forName(driver);
            try {
                conn = DriverManager.getConnection(this.url, this.user, this.password);
                return conn != null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    /**
     * 执行hive操作
     * @param command
     * @throws Exception
     */
    public void exec_auth_cmd(String command) throws Exception {
        try {
            if (login()) {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(command);
                stmt.close();
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * 传递映射表的表名
     * @param tableName
     */
    public void deleteTable(String tableName){
        String sql = "";
    }

    //kerberos验证
    private void authKrbs() {
        Configuration conf = new Configuration();
        conf.setBoolean("hadoop.security.authorization", true);
        conf.set("hadoop.security.authentication", "kerberos");

        UserGroupInformation.setConfiguration(conf);
        try {
            UserGroupInformation.loginUserFromKeytab(
                    "hive@TAL100TEST.COM", "/home/hive.keytab");
            System.out.println("Succeeded in authenticating through Kerberos!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
