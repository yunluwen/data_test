package com.zyh.streaming

//0.引入必要的编程元素
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.api.windowing.time.Time

/**
  * 单位时间内的流计算
  * sliding-time-window (有重叠数据)
  */
object SlidingTW {

  def main(args: Array[String]): Unit = {

    //1.创建运行环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    //2.定义数据流来源
    val text = env.socketTextStream("localhost", 9999)

    //3.转换数据格式，text->CarWc
    case class CarWc(sensorId: Int, carCnt: Int)
    val ds1: DataStream[CarWc] = text.map {
      (f) => {
        val tokens = f.split(",")
        CarWc(tokens(0).trim.toInt, tokens(1).trim.toInt)
      }
    }
    //4.执行统计操作，每个sensorId一个sliding窗口，窗口时间10秒,滑动时间5秒
    //也就是说，每5秒钟统计一次，在这过去的10秒钟内，各个路口通过红绿灯汽车的数量。
    val ds2: DataStream[CarWc] = ds1
      .keyBy("sensorId")
      .timeWindow(Time.seconds(10), Time.seconds(5))
      .sum("carCnt")

    //5.显示统计结果
    ds2.print()

    //6.触发流计算
    env.execute(this.getClass.getName)
  }

}
