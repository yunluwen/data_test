package com.scala.study.collections

import collection.Traversable  //所有集合的父特质

//单线程使用，非线程安全的
import scala.collection.immutable.Traversable  //不可变集合的父特质
import scala.collection.mutable.Traversable    //可变集合的父特质

//并发使用，线程安全
import scala.collection.parallel.immutable
import scala.collection.parallel.mutable


/**
  * 所有集合类的父特质，包含了常用的转换方法和收集方法
  */
object TraversableDemo {

  def main(args: Array[String]): Unit = {

  }


}
