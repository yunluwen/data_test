package com.spark.study.core

import org.apache.spark.sql.SparkSession

object ActionOperation {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("ActionOperation")
      .master("local")
      .enableHiveSupport()      //添加hive支持
      .config("spark.some.config.option", "config-value")  //必须设置spark sql的元数据仓库的地址
      .getOrCreate()

    import spark.implicits._  //导入spark的隐式转换
    import org.apache.spark.sql.functions._    //导入spark sql的functions

    //dataframe是untype类型的 ，dataset是type类型
    //文件加载进来形成两个dataframe
    val employee = spark.read.json("")

    employee.collect().foreach{ println(_) }   //Array[Row]
    //count:对dataset中的记录数统计个数
    employee.count()
    //获取数据集的第一条数据
    employee.first()
    //遍历,在本地可以看到输出
    //在集群中是看不到输出的，因为输出的结果是在分布式的集群中的
    employee.foreach{ println(_) }
    employee.show()    //默认打印前二十条

    //take获取指定前几条数据
    employee.take(3).foreach{ println(_) }

    spark.stop()

  }

}
