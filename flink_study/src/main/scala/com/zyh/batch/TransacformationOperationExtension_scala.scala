package com.zyh.batch

import org.apache.flink.api.scala._   //scala的flink程序必须引入这个
import org.apache.flink.api.scala.extensions._  //使用扩展必须引入这个

/**
  * 算子操作扩展
  */
object TransacformationOperationExtension_scala {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    testMapwith(env)
    testfilterWith(env)
    testreduceWith(env)
    testFlatMapWith(env)
  }

  /**
    * mapWith:
    * 可以使用偏函数进行map操作。
    * @param benv
    */
  def testMapwith(benv:ExecutionEnvironment): Unit ={
    //2.创建DataSet[Point]
    case class Point(x: Double, y: Double)
    val ds = benv.fromElements(Point(1, 2), Point(3, 4), Point(5, 6))

    //3.使用mapWith进行元素转化
    val r=ds.mapWith {
      case Point(x, y) => Point( x*2,y+1)
    }

    //4.显示结果
    println(r.collect)

    //3.使用mapWith进行元素转化
    val r1=ds.mapWith {
      case Point(x, _) => x*2
    }
    //4.显示结果
    println(r1.collect)
  }

  /**
    * filterWith:
    * 可以使用片函数进行filter操作。
    */
  def testfilterWith(benv:ExecutionEnvironment): Unit ={
    //2.创建DataSet[Point]
    case class Point(x: Double, y: Double)
    val ds = benv.fromElements(Point(1, 2), Point(3, 4), Point(5, 6))

    //3.使用filterWith进行元素过滤
    val r=ds.filterWith {
      case Point(x, y) => x > 1 && y <5
    }

    //4.显示结果
    println(r.collect)

    //3.使用filterWith进行元素过滤
    val r1=ds.filterWith {
      case Point(x, _) => x > 1
    }
    //4.显示结果
    println(r1.collect)
  }

  /**
    * reduceWith:
    * 可以使用偏函数进行reduce操作。
    */
  def testreduceWith(benv:ExecutionEnvironment): Unit ={
    //2.创建DataSet[Point]
    case class Point(x: Double, y: Double)
    val ds = benv.fromElements(Point(1, 2), Point(3, 4), Point(5, 6))

    //3.使用reduceWith进行元素的merger
    val r=ds.reduceWith {
      case (Point(x1, y1), (Point(x2, y2))) => Point(x1 + x2, y1 + y2)
    }
    //4.显示结果
    println(r.collect)
  }

  /**
    * flatmapWith:
    * 可以使用偏函数进行flatMapWith操作。
    * @param benv
    */
  def testFlatMapWith(benv:ExecutionEnvironment): Unit ={
    //2.创建DataSet[Point]
    case class Point(x: Double, y: Double)
    val ds = benv.fromElements(Point(1, 2), Point(3, 4), Point(5, 6))

    //3.使用reduceWith进行元素的merger
    val r0=ds.flatMapWith {
      case Point(x, y) => Seq("x" -> x, "y" -> y)
    }

    //4.显示结果
    println(r0.collect)
  }


}
