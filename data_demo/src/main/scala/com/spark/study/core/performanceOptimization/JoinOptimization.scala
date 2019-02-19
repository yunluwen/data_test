package com.spark.study.core.performanceOptimization

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ArrayBuffer

/**
  * join Optimization
  */
object JoinOptimization {

  def main(args: Array[String]): Unit = {
    /**
      * optimization1 : 设置更多的资源，spark_submit给executor设置更多的内存和core
      * optimization2 :
      *
      * data skew :
      *
      */

    val conf = new SparkConf().setMaster("local")
      .setAppName("JoinOptimization")

    val sc = new SparkContext(conf)
    var data = new ArrayBuffer[String](9)
    data.append("zhang","liu","ming","li","wang","wang","zhao","xue","ming")
    val lines = sc.parallelize(data)
    //数据量特别大的时候使用count(distinct),容易导致数据倾斜
    //spark sql 或者 hive sql中要避免使用count(distinct(字段))这种用法
    val dis = lines.distinct().count()  //去重后的数目,count distinct
    println(dis)

//    groupBy(function)
//    function返回key，传入的RDD的各个元素根据这个key进行分组
//    分成两组，自定义分组规则
    val group = lines.groupBy(name => {
      if(name.startsWith("z")){
        "zzz"
      }else{
        "xxx"
      }
    }).collect()
    for(gro <- group) {
      println(gro)
    }

    val pairs = lines.map(word => {
      (word,1)
    })

    val groups = pairs.groupByKey()
        .map(group => {
          (group._1,1)
        }).collect()

    for(arr <- groups) {
      println(arr)
    }



    sc.stop()

  }
}
