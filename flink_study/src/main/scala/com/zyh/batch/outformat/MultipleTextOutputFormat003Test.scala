package com.zyh.batch.outformat

//import org.apache.flink.api.scala.hadoop.mapred.HadoopOutputFormat
import org.apache.flink.api.scala.{ExecutionEnvironment, _}
import org.apache.hadoop.fs.Path
import org.apache.hadoop.mapred.{FileOutputFormat, JobConf}

object MultipleTextOutputFormat003Test {
  def main(args: Array[String]) {

    //1.创建批处理环境
    val env = ExecutionEnvironment.getExecutionEnvironment

    //2.准备数据
    val data1 = env.fromCollection(List(("zhangsan", "120"), ("lisi", "123"),
      ("zhangsan", "309"), ("lisi", "207"), ("wangwu", "315")))
    data1.setParallelism(4)

    //3.多路径输出的HadoopOutputFormat
    val multipleTextOutputFormat = new MultipleTextOutputFormat003[String, String]()
    val jobConf = new JobConf()
    val filePath = "hdfs://qingcheng12:9000/output/flink/MultipleTextOutputFormat/scala/003"
    FileOutputFormat.setOutputPath(jobConf, new Path(filePath))
//    val format = new HadoopOutputFormat[String, String](multipleTextOutputFormat, jobConf)
//
//    //4.将数据输出出去
//    data1.output(format)

    //5.触发批处理执行
    env.execute()
  }
}
