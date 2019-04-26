package com.zyh.batch

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.core.fs.FileSystem.WriteMode

import org.apache.flink.api.scala._

object WordCount_scala {

  def main(args: Array[String]) {
    //获取运行环境(本地)。提交远程会出异常
    //val env = ExecutionEnvironment.createLocalEnvironment(1)

    //将代码提交到集群上的时候，这样设置运行时环境，动态转换运行环境
    val env = ExecutionEnvironment.getExecutionEnvironment
    //远程环境
//    val env = ExecutionEnvironment.createRemoteEnvironment(
//      "localhost",
//      6123,
//      "/Users/zhangyunhao/IdeaProjects/flink_study/target/flink_study-1.6.1-jar-with-dependencies.jar")

    //从本地读取文件
    val text = env.readTextFile("/Users/zhangyunhao/test_file/ceshi_hdfs/read.txt")

    //单词统计
    val counts = text.flatMap { _.toLowerCase.split(" ") filter { _.nonEmpty } }
      .map { (_, 1) }
      .groupBy(0)
      .sum(1)

    //输出结果
    counts.print()

    //保存结果到txt文件
    counts.writeAsText("/Users/zhangyunhao/test_file/ceshi_hdfs/output.txt", WriteMode.OVERWRITE)
    env.execute("Scala WordCount Example")

  }
}