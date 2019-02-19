package com.spark.study.core

import org.apache.spark.sql.SparkSession

/**
  * wc 2.0
  */
object WordCount {

  case class Person(name:String,age:Long)

  def main(args: Array[String]): Unit = {

    val sparkSession = SparkSession.builder
      .master("local")
      .appName("my-spark-app")
      .config("spark.some.config.option", "config-value")
      .getOrCreate()

    // 导入spark的隐式转换
    import sparkSession.implicits._
    // 导入spark sql的functions
    import org.apache.spark.sql.functions._

    val personDF = sparkSession.read.json("test_data/people.json")
    val text = sparkSession.read.textFile("test_data/data.txt")

    personDF.show()
    text.show()
    // personDF.collect().foreach (println)
    // println(personDF.count())

//    val personDS = personDF.as[Person]
    // personDS.show()
    // personDS.printSchema()
    //val dataframe=personDS.toDF()

//    personDF.createOrReplaceTempView("persons")
//    sparkSession.sql("select * from persons where age > 20").show()
//    sparkSession.sql("select * from persons where age > 20").explain()
//
//    val personScoresDF= sparkSession.read.json("peopleScores.json")
    // personDF.join(personScoresDF,$"name"===$"n").show()
//    personDF.filter("age > 20").join(personScoresDF,$"name"===$"n").show()
//
//    personDF.filter("age > 20")
//      .join(personScoresDF,$"name"===$"n")
//      .groupBy(personDF("name"))
//      .agg(avg(personScoresDF("score")),avg(personDF("age")))
//      .explain()
    //.show()

    sparkSession.stop()
  }

}
