package com.hbase2hive.test

import org.apache.hadoop.hbase.{HBaseConfiguration, HTableDescriptor}
import org.apache.hadoop.hbase.client.Scan
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.protobuf.ProtobufUtil
import org.apache.hadoop.hbase.util.{Base64, Bytes}
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 测试spark2.0读取hbase的数据
  */
object TestHbase2 {

  def main(args: Array[String]): Unit = {
//    注意:在spark 2.0中就不建议使用这种方式了
//    val sparkConf = new SparkConf().setAppName("HBaseTest").setMaster("local")
//    val sc = new SparkContext()(sparkConf)

    val session = SparkSession
                    .builder()
                    .appName("hbaseTest")
                    .master("local[2]")
                    .getOrCreate()
    val sc = session.sparkContext

    val tablename = "account"
    val conf = HBaseConfiguration.create()
    //设置zooKeeper集群地址，也可以通过将hbase-site.xml导入classpath，但是建议在程序里这样设置
    conf.set("hbase.zookeeper.quorum","localhost")
    //设置zookeeper连接端口，默认2181
    conf.set("hbase.zookeeper.property.clientPort", "2181")
    conf.set(TableInputFormat.INPUT_TABLE, tablename)

    val startRowkey="0,110000,20180220"
    val endRowkey="0,110000,20180302"
    //开始rowkey和结束一样代表精确查询某条数据

    //组装scan语句,Hbase扫描器
    //条件扫描
    //val scan=new Scan(Bytes.toBytes(startRowkey),Bytes.toBytes(endRowkey))
    val scan = new Scan()   //扫描全表
    scan.setCacheBlocks(false)
    /*
    scan.addFamily(Bytes.toBytes("ks"));
    scan.addColumn(Bytes.toBytes("ks"), Bytes.toBytes("data"))
    */
    //将scan类转化成string类型
    val proto= ProtobufUtil.toScan(scan)
    val ScanToString = Base64.encodeBytes(proto.toByteArray())
    conf.set(TableInputFormat.SCAN,ScanToString)

    //读取数据并转化成rdd
    val hBaseRDD = sc.newAPIHadoopRDD(conf, classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result])

    val count = hBaseRDD.count()
    println(count)
    hBaseRDD.foreach{case (_,result) =>{
      //获取行键
      val key = Bytes.toString(result.getRow)
      //通过列族和列名获取列
      val citycode = Bytes.toString(    //注意这里类型转换不能出错
        result.getValue("f1".getBytes,"citycode".getBytes))
      val daytime = Bytes.toString(
        result.getValue("f1".getBytes,"daytime".getBytes))

      println("Row key:"+key+" Name:"+citycode+" Age:"+daytime)
    }}

    sc.stop()
  }

}
