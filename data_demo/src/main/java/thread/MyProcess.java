package thread;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * 测试Process
 */
public class MyProcess {

    public static void main(String[] args) {
        Process process = null;
        InputStream inputStream = null;
        try{
            process = Runtime.getRuntime().exec(//"/Users/zhangyunhao/bigdata_install_dic/sqoop/bin/sqoop version");
                    "/Users/zhangyunhao/bigdata_install_dic/sqoop/bin/sqoop" +
                    " import -D sqoop.hbase.add.row.key=true\n" +
                    "--connect jdbc:mysql://127.0.0.1:3306/springboot\n" +
                    "--username root --password 123456\n" +
                    "--table USER --columns ID,NAME\n" +
                    "--hbase-create-table\n" +
                    "--hbase-table t_users --column-family info --hbase-row-key ID\n" +
                    "-m 1");
            process.waitFor();
            inputStream = process.getInputStream();
            byte[] b = new byte[1024];
            int size = 0;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            while ((size = inputStream.read(b)) > 0) {
                outputStream.write(b, 0, size);
            }
            System.out.println(outputStream.toString("utf-8"));
        }catch (Exception e){

        }finally {
            if(null != process){
                process.destroy();
            }
        }
    }

}
