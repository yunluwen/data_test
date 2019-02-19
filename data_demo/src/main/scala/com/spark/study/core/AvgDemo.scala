package com.spark.study.core

import org.apache.spark.sql.SparkSession

object AvgDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("avgDemo")
      .master("local")
      .enableHiveSupport()      //添加hive支持
      .config("spark.some.config.option", "config-value")  //必须设置spark sql的元数据仓库的地址
      .getOrCreate()

    import spark.implicits._  //导入spark的隐式转换
    import org.apache.spark.sql.functions._    //导入spark sql的functions

    //dataframe是untype类型的 ，dataset是type类型
    //文件加载进来形成两个dataframe
    val employee = spark.read.json("/test_data/employee.json")
    val department = spark.read.json("/test_data/department.json")

    employee
      //先进行过滤操作
      .filter("age>20")
      //注意：untype join,两个表的连接条件需要三个等号
      .join(department,$"depId"===$"id")
      //分组
      .groupBy(department("name"),employee("gender"))
      //最后执行聚合函数
      .agg(avg(employee("salary")),avg(employee("age")))
      //将结果显示出来
      .show()

    //dataframe = dataset[row]
    //dataframe的类型是row,所以是untype类型的，弱类型
    //dataset类型通常是我们自定义的case class ,所以是typed类型，强类型

    spark.stop()

  }

}
