package com.spark.study.core

import org.apache.spark.sql.SparkSession

/**
  * untyped操作
  */
object UnTypedOperation {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("UnTypedOperation")
      .master("local")
      .enableHiveSupport()      //添加hive支持
      .config("spark.some.config.option", "config-value")  //必须设置spark sql的元数据仓库的地址
      .getOrCreate()

    import spark.implicits._  //导入spark的隐式转换
    import org.apache.spark.sql.functions._    //导入spark sql的functions

    //dataframe是untyped类型的 ，dataset是typed类型
    //文件加载进来形成两个dataframe
    val employee = spark.read.json("/test_data/employee.json")
    val department = spark.read.json("/test_data/department.json")

    /**
      * select
      * where
      * groupBy
      * agg
      * col
      * join
      */
    employee.where("age>20")
      .join(department,$"depId"===$"id")
      .groupBy(department("name"),employee("gender"))
      .agg(avg(employee("salary")))
      .select($"name",$"gender",$"avg(salary)")
      .show()

    /**
      * 聚合函数:avg,sum,max,min,count
      * countDistinct 去重求数目
      */
    employee.join(department,$"depId"===$"id")
        .groupBy(department("name"))
        .agg(avg(employee("salary")),sum(employee("salary")),max(employee("salary")),count(employee("salary")),
          countDistinct(employee("salary")))
        .show()

    /**
      * 聚合函数：collect_list  collect_set
      * 行转列操作
      * id:1,name:zzz
      * id:1,name:jjj
      * 转换之后变为：
      * id:1,name:[zzz,jjj]
      */
    employee.groupBy(employee("depId"))
        .agg(collect_list(employee("name")),collect_set(employee("name")))
        .show()

    spark.stop()
  }

}
