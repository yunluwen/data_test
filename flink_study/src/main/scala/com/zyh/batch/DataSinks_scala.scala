package com.zyh.batch

import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment}
import org.apache.flink.api.scala._   //scala的flink程序必须引入这个

/**
  * 批处理操作常用API
  */
object DataSinks_scala {

  def main(args: Array[String]): Unit = {
//    获取DataSet的执行环境上下文,这个上下文和当前的DataSet有关，不是全局的。
    val env = ExecutionEnvironment.getExecutionEnvironment
//    testGetParallelism(env)
//    testsetParallelism(env)
//    testwriteAsText(env)
    testWriteAsCSV(env)
  }

  /**
    * getParallelism:
    * 获取DataSet的并行度。
    */
  def testGetParallelism(benv:ExecutionEnvironment): Unit ={
    //1.创建一个 DataSet其元素为String类型
    val input0: DataSet[String] = benv.fromElements("A", "B", "C")

    //2.获取DataSet的并行度。
    println(input0.getParallelism)   //1
  }

  /**
    * setParallelism:
    * 设置DataSet的并行度
    * @param benv
    */
  def testsetParallelism(benv:ExecutionEnvironment): Unit ={
    //1.创建一个 DataSet其元素为String类型
    val input0: DataSet[String] = benv.fromElements("A", "B", "C")
    input0.setParallelism(3)
    //2.获取DataSet的并行度。
    println(input0.getParallelism)   //3
  }

  /**
    * writeAsText:
    * 将DataSet写出到存储系统。不同的存储系统写法不一样。
    *
    * hdfs文件路径：
    * hdfs:///path/to/data
    * 本地文件路径：
    * file:///path/to/data  windows系统
    * mac和windows系统，直接写文件路径就可以了
    * @param benv
    */
  def testwriteAsText(benv:ExecutionEnvironment): Unit ={
    //1.创建 DataSet[Student]
    case class Student(age: Int, name: String,height:Double)
    val input: DataSet[Student] = benv.fromElements(
      Student(16,"zhangasn",194.5),
      Student(17,"zhangasn",184.5),
      Student(18,"zhangasn",174.5),
      Student(16,"lisi",194.5),
      Student(17,"lisi",184.5),
      Student(18,"lisi",174.5))

    //2.将DataSet写出到存储系统
    input.writeAsText("/Users/zhangyunhao/test_file/student/students.txt")

    //3.执行程序
    benv.execute()
  }

  /**
    *
    * writeAsCSV:
    * 参数说明：
    * rowDelimiter：行分隔符
    * fieldDelimiter：列分隔符
    *
    * Writes this DataSet to the specified location as CSV file(s).
    *
    * 将DataSet以CSV格式写出到存储系统。路径写法参考writeAsText。
    * ---------------------
    */
  def testWriteAsCSV(benv:ExecutionEnvironment): Unit ={
    //1.创建 DataSet[Student]
    case class Student(age: Int, name: String,height:Double)
    val input: DataSet[Student] = benv.fromElements(
      Student(16,"zhangasn",194.5),
      Student(17,"zhangasn",184.5),
      Student(18,"zhangasn",174.5),
      Student(16,"lisi",194.5),
      Student(17,"lisi",184.5),
      Student(18,"lisi",174.5))

    //2.将DataSet写出到存储系统
    input. writeAsCsv(
      "/Users/zhangyunhao/test_file/student/csv/students.csv",
      "#","|")

    //3.执行程序
    benv.execute()

  }

   //------------------------------------------------------//
   //           下面的这些操作还有问题，待解决                  //
  /**
    * self ：获取dataset本身
    * @param benv
    */
  def testSelf(benv:ExecutionEnvironment): Unit ={
    //1.创建一个 DataSet其元素为String类型
    val input: DataSet[String] = benv.fromElements("A", "B", "C", "D", "E", "F")

    //2.获取input本身
    val s=input

    //3.比较对象引用
    println(s==input)
  }

  /**
    * countElementsPerPartition:
    * 获取DataSet的每个分片中元素的个数。
    * @param benv
    */
  def testcountElementsPerPartition(benv:ExecutionEnvironment): Unit ={
    //1.创建一个 DataSet其元素为String类型
    val input: DataSet[String] = benv.fromElements("A", "B", "C", "D", "E", "F")

    //2.设置分片前
    val p0=input.getParallelism
//    val c0=input.countElementsPerPartition
//    c0.collect

    //2.设置分片后
    //设置并行度为3，实际上是将数据分片为3
    input.setParallelism(3)
    val p1=input.getParallelism
//    val c1=input.countElementsPerPartition
//    c1.collect
  }

  /**
    * checksumHashCode:
    * 获取DataSet的hashcode和元素的个数
    * @param benv
    */
  def testchecksumHashCode(benv:ExecutionEnvironment): Unit ={
    //1.创建一个 DataSet其元素为String类型
    val input: DataSet[String] = benv.fromElements("A", "B", "C", "D", "E", "F")

    //2.获取DataSet的hashcode和元素的个数
    //input.checksumHashCode
  }

  /**
    * zipWithIndex:
    * 元素和元素的下标进行zip操作。
    * @param benv
    */
  def testzipWithIndex(benv:ExecutionEnvironment): Unit ={
    //1.创建一个 DataSet其元素为String类型
    val input: DataSet[String] = benv.fromElements("A", "B", "C", "D", "E", "F")

    //2.元素和元素的下标进行zip操作。
    //val result: DataSet[(Long, String)] = input.zipWithIndex

    //3.显示结果
    //result.collect
  }

}
