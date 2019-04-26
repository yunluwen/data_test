package com.zyh.streaming

import java.text.SimpleDateFormat

import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.watermark.Watermark
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector
/**
  * 测试watermark
  *   watermark是用于处理乱序事件的，而正确的处理乱序事件，
  *   通常用watermark机制结合window来实现。
  *
  * 生成watermark的方式主要有2大类：
  *
  * (1):With Periodic Watermarks
  * (2):With Punctuated Watermarks
  * 第一种可以定义一个允许最大乱序的时间，这种情况应用较多
  *
  *
  *
  * 示例主要针对第一种方式
  */
object WatermarkTest {
  def main(args: Array[String]): Unit = {
    val senv = StreamExecutionEnvironment.getExecutionEnvironment
    //设置时间特性
    senv.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    val data = senv.socketTextStream("localhost",9999)
    //基本数据转换
    val map = data.map(f => {
      val arr = f.split(" ")
      val code = arr(0)
      val time = arr(1).toLong
      (code,time)
    })

    //生成watermark
    val watermark = map.assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks[(String, Long)] {
      var currentMaxTimestamp = 0L    //初始化当前最大时间戳
      val maxOutOfOrderness = 10000L  //最大允许的乱序时间是10s

      var a : Watermark = null        //watermark
      val format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")

      //获取当前watermark
      override def getCurrentWatermark: Watermark = {
        a = new Watermark(currentMaxTimestamp - maxOutOfOrderness)
        a
      }
      //计算最大时间戳
      override def extractTimestamp(t: (String, Long), l: Long): Long = {
        val timestamp = t._2
        currentMaxTimestamp = Math.max(timestamp, currentMaxTimestamp)
        println("timestamp:" + t._1 +","+ t._2 + "|" +format.format(t._2) +","+
          currentMaxTimestamp + "|"+ format.format(currentMaxTimestamp) + ","+ a.toString)
        timestamp
      }
    })

    val window = watermark
      .keyBy(_._1)
      //注意是3秒时间内触发一次窗口操作
      .window(TumblingEventTimeWindows.of(Time.seconds(3)))
      .apply(new WindowFunctionTest)

    window.print()

    senv.execute(this.getClass.getName)

  }

  class WindowFunctionTest extends
  //传入的数据类型，数据返回类型，key的类型，窗口类型
    WindowFunction[(String,Long),(String, Int,String,String,String,String),String,TimeWindow]{

    override def apply(key: String, window: TimeWindow,
                       input: Iterable[(String, Long)],
                       out: Collector[(String, Int, String, String, String, String)]): Unit = {
      val list = input.toList.sortBy(_._2)
      val format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
      out.collect(key,input.size,format.format(list.head._2),
        format.format(list.last._2),format.format(window.getStart),
        format.format(window.getEnd))
    }
  }
}

/**
  * 程序详解
  * （1）接收socket数据
  * （2）将每行数据按照字符分隔，每行map成一个tuple类型（code，time）
  * （3）抽取timestamp生成watermark。并打印（code，time，格式化的time，
  *     currentMaxTimestamp，currentMaxTimestamp的格式化时间，watermark时间）。
  * （4）event time每隔3秒触发一次窗口，输出（code，窗口内元素个数，窗口内最早元素的时间，
  *     窗口内最晚元素的时间，窗口自身开始时间，窗口自身结束时间）
  *
  * 注意：new AssignerWithPeriodicWatermarks[(String,Long)中有
  *      抽取timestamp和生成watermark2个方法，在执行时，它是先抽取timestamp，
  *      后生成watermark，因此我们在这里print的watermark时间，其实是上一条的watermark时间
  *
  */

//000001 1461756872000
//000001 1461756873000
//000001 1461756874000
//000001 1461756876000
//000001 1461756886000
//
//000001 1461756862000
//000001 1461756866000
//000001 1461756872000
//000001 1461756873000