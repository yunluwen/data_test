package com.spark.study.core

import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel

object BasicOperation {

  case class Employee(name:String,age:Long,deptId:Long,gender:String,salary:Double)

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("BasicOperation")
      .master("local")
      .enableHiveSupport()      //添加hive支持
      .config("spark.some.config.option", "config-value")  //必须设置spark sql的元数据仓库的地址
      .getOrCreate()

    import spark.implicits._  //导入spark的隐式转换
    import org.apache.spark.sql.functions._    //导入spark sql的functions

    //dataframe是untype类型的 ，dataset是type类型
    //文件加载进来形成两个dataframe
    val employee = spark.read.json("")

    employee.cache()   //直接持久化到内存中
    employee.persist(StorageLevel.MEMORY_ONLY)  //相当于cache,直接持久化到内存中
//    StorageLevel 设置多个持久化级别

    //创建临时视图，对数据执行sql
    employee.createOrReplaceTempView("employee")
    spark.sql(
      """
        |select * from employee
        |where age>30
      """.stripMargin).show()

    //获取spark sql 的执行计划
    spark.sql(
      """
        |select * from employee
        |where age>30
      """.stripMargin).explain()

    //打印元数据
    employee.printSchema()

    val employeThen30DF = spark.sql(
      """
        |select * from employee
        |where age>30
      """.stripMargin)

    employeThen30DF.write.json("")   //将结果写入一个json文件中

    //dataframe转换为dataset
    val employeeDS = employee.as[Employee]
    employeeDS.show()
    employeeDS.printSchema()

    //dataset转换为dataframe
    val employeeDF = employee.toDF()
    employeeDF.show()
    employeeDF.printSchema()

    spark.stop()
  }
}
