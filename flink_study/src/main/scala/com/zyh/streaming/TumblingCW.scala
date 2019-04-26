package com.zyh.streaming

//0.引入必要的编程元素
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}

/**
  * 按数据传递过来的个数进行计算
  * tumbling-count-window (无重叠数据)
  * 每个路口分别统计，收到关于它的5条消息时统计在最近5条消息中，各自路口通过的汽车数量
  */
object TumblingCW {

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

    ds1.print()

    //4.执行统计操作，每个sensorId一个tumbling窗口，窗口的大小为5
    //也就是说，每个路口分别统计，收到关于它的5条消息时统计在最近5条消息中，各自路口通过的汽车数量
    val ds2: DataStream[CarWc] = ds1
      .keyBy("sensorId")
      .countWindow(5)
      .sum("carCnt")

    //5.显示统计结果
    ds2.print()

    //6.触发流计算
    env.execute(this.getClass.getName)

  }
}
