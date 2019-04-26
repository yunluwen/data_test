package com.zyh.batch

import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment}
import org.apache.flink.configuration.Configuration
import org.apache.flink.api.scala._

import scala.collection.mutable.ListBuffer
import scala.io.Source
/**
  * flink分布式缓存
  */
object DistributedCache001 {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    //1.准备缓存数据
    val path = "/Users/zhangyunhao/test_file/test_flink/worker.txt"
    env.registerCachedFile(path,"myTestFile")   //注册缓存文件

    //2、准备人工数据
    case class Worker(name:String,salaryOfMonth:Double)
    val workers:DataSet[Worker] = env.fromElements(
      new Worker("zhangsan",1000),
      new Worker("lisi",2000)
    )

    //3、使用缓存数据和人工数据做计算
    workers.map(new MyMapper()).print()
    class MyMapper() extends RichMapFunction[Worker, Worker]{
      private var lines: ListBuffer[String] = new ListBuffer[String]

      //3.1在open方法中获取缓存文件
      override def open(parameters: Configuration): Unit = {
        super.open(parameters)
        //获取缓存文件
        val myFile = getRuntimeContext.getDistributedCache.getFile("myTestFile")
        //从缓存文件中获取文件的内容
        val lines = Source.fromFile(myFile.getAbsolutePath).getLines()
        lines.foreach(f = line => {
          this.lines.append(line)
        })
      }

      override def map(worker: Worker): Worker = {
        var name = ""
        var month = 0
        //分解文件中的内容
        for (s <- this.lines) {
          val tokens = s.split(":")
          if (tokens.length == 2) {
            name = tokens(0).trim
            if (name.equalsIgnoreCase(worker.name)) {
              month = tokens(1).trim.toInt
            }
          }
          //找到满足条件的信息
          if (name.nonEmpty && month > 0.0) {
            return Worker(worker.name, worker.salaryOfMonth * month)
          }
        }
        //没有满足条件的信息
        Worker(worker.name, worker.salaryOfMonth * month)
      }
    }

  }
}
