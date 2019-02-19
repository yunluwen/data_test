package com.spark.study.core

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.Row

object SparkDemo {
  /**
    * 本地存在hadoop的配置文件，运行的时候，所有的文件都默认是hdfs上面的
    *
    */

  case class People(name:String,age:Int)

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("sparkDemo")
      .master("local")
      .enableHiveSupport()      //添加hive支持
      .config("spark.some.config.option", "config-value")  //必须设置spark sql的元数据仓库的地址
      .getOrCreate()

    import spark.implicits._    //导入sparkSession的隐式转换

    val df = spark.read.json("/test_data/people.json")
    df.show()
    df.printSchema()   //打印元数据
    df.select("name").show()
    df.select($"name",$"age"+1).show()
    df.filter($"age" > 21).show()
    df.groupBy("age").count().show()

    //基于dataframe创建临时视图
    df.createOrReplaceTempView("people")
    val sqlDF = spark.sql("select * from people")
    sqlDF.show()

    val peopleDS = Seq(People("aa",12),People("bb",89)).toDS()  //DataSet
    val numDS = Seq(1,2,3,4,5,6).toDS()

    peopleDS.show()
    numDS.show()
    numDS.map(_+1).show()

    //操作hive
    val sqlhiveDF = spark.sql(
      """
        |select sno,grade from test.sc limit 10
      """.stripMargin)

    val sqlhiveDS = sqlhiveDF.map {   //dataframe转换为dataset
      case Row(key:Int,value:Int) => s"KEY:$key,VALUE:$value"
    }
    sqlhiveDS.show()



  }

}
