package com.zyh.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * HBase工具类
 * 获取表:
 *      String tableName = "table_a";
 *      Table table = connection.getTable( TableName.valueOf(tableName));
 */
public class HBaseUtils {

    private static Configuration conf;
    private static Connection connection = null;
    static {
        conf = HBaseConfiguration.create();
        //hbase
        conf.set("hbase.zookeeper.quorum", "127.0.0.1");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        //conf.set("zookeeper.znode.parent", "/hbase");
        //conf.set("hbase.client.keyvalue.maxsize", "1048576000"); //1G
        //kerberos
        conf.setBoolean("hadoop.security.authorization", true);
        conf.set("hadoop.security.authentication", "kerberos");
        conf = HBaseConfiguration.create(conf);
        UserGroupInformation.setConfiguration(conf);
        try {
            authKrbs();
            connection = ConnectionFactory.createConnection(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量获取hbase中的数据:
     * 将rowkey存放到Get中，list存放get
     * 通过get获取执行结果，存到list中
     * @param name 表名
     * @param rowkeyList 行键列表
     * @return result
     * @throws IOException
     */
    public static List<String> qurryTableGetBatch(String name,List<String> rowkeyList)
            throws IOException {
        Table table = connection.getTable( TableName.valueOf(name));
        List<Get> getList = new ArrayList<Get>();
        for (String rowkey : rowkeyList){
            Get get = new Get(Bytes.toBytes(rowkey));
            getList.add(get);
        }
        List<String> list = new ArrayList<String>();
        Result[] results = table.get(getList);
        for (Result result : results){
            for (Cell kv : result.rawCells()) {
                String value = Bytes.toString(CellUtil.cloneValue(kv));
                list.add(value);
            }
        }
        table.close();
        return list;
    }

    /**
     * 批量写入数据
     * @param name 表名
     * @param list 要插入的put集合
     */
    public static void queryTablePutBatch(String name,List<Put> list)
            throws IOException{
        Table table = connection.getTable( TableName.valueOf(name));
        table.put(list);
        table.close();
    }

    /**
     * kerberos认证
     */
    private static void authKrbs() {
        try {
            UserGroupInformation.loginUserFromKeytab(
                    "hbase@TAL100TEST.COM", "/home/***.keytab");
            System.out.println("Succeeded in authenticating through Kerberos!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
