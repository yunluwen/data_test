package com.zyh.batch

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._
/**
  * flink容错
  * flink支持容错设置,当操作失败了，可以在指定重试的启动时间和重试的次数.有两种设置方式
  * 1.通过配置文件，进行全局的默认设定
  * 2.通过程序的api进行设定。
  */
object FaultTolerance001 {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    //失败重试3次
    env.setNumberOfExecutionRetries(3)
    //重试时延 5000 milliseconds
    env.getConfig.setExecutionRetryDelay(5000)
    val ds1 = env.fromElements(2, 5, 3, 7, 9)
    ds1.map(_ * 2).print()
  }

}
