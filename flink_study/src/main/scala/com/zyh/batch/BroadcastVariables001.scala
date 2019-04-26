package com.zyh.batch

import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment}
import org.apache.flink.configuration.Configuration
import java.util
import org.apache.flink.api.scala._
/**
  * 广播变量：
  * flink支持将变量广播到worker上，以供程序运算使用。
  *
  * 注意：
  *
  * 1：广播出去的变量存在于每个节点的内存中，所以这个数据集不能太大。
  *    因为广播出去的数据，会常驻内存，除非程序执行结束。
  *
  * 2：广播变量在初始化广播出去以后不支持修改，这样才能保证每个节点的数据都是一致的。
  *
  * 个人建议：如果数据集在几十兆或者百兆的时候，可以选择进行广播，如果数据集的大小上G的话，就不建议进行广播了。
  */
object BroadcastVariables001 {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    //1.准备工人数据（用于map）
    case class Worker(name: String, salaryPerMonth: Double)
    val workers: DataSet[Worker] = env.fromElements(
      Worker("zhagnsan", 1356.67),
      Worker("lisi", 1476.67)
    )
    //2准备统计数据(用于广播，通过withBroadcastSet进行广播)
    case class Count(name: String, month: Int)
    val counts: DataSet[Count] = env.fromElements(
      Count("zhagnsan", 4),
      Count("lisi", 5)
    )

    //3.使用map数据和广播数据进行计算
    workers.map(new RichMapFunction[Worker,Worker] {
      private var cwork: util.List[Count] = null

      override def open(parameters: Configuration): Unit = {
        super.open(parameters)
        // 3.1 访问广播数据
        cwork = getRuntimeContext.getBroadcastVariable[Count]("countWorkInfo")
      }

      override def map(w: Worker): Worker = {
        //3.2解析广播数据
        var i = 0
        while (i < cwork.size()) {
          val c = cwork.get(i)
          i += 1
          if (c.name.equalsIgnoreCase(w.name)) {
            //有相应的信息的返回值
            return Worker(w.name, w.salaryPerMonth * c.month)
          }
        }
        //无相应的信息的返回值
        Worker("###", 0)
      }
    }).withBroadcastSet(counts, "countWorkInfo").print()
  }
}
