package com.scala.study.demo

import org.apache.spark.SparkContext
import com.session.init.Init

/**
  * ordered 和 ordering比较器
  */
object OrderDemo {

  def main(args: Array[String]): Unit = {
    testOrdering()
    val sc = Init.initSparkContext()._1
    testSortRDD(sc)
  }

  /**
    * 测试变长参数
    * @param args
    */
  def testArgs(args: String*): Unit ={
    for(value <- args) {
      println(value)
    }
  }

  /**
    * 测试Ordering
    */
  def testOrdering(): Unit ={
    import scala.util.Sorting
    val pairs = Array(("a", 5, 2), ("c", 3, 1), ("b", 1, 3))

    // sort by 2nd element
    Sorting.quickSort(pairs)(Ordering.by[(String, Int, Int), Int](_._2))
    println(pairs(0))    //排序之后不会返回一个新的数组，说明数组是可变的
    // sort by the 3rd element, then 1st
    Sorting.quickSort(pairs)(Ordering[(Int, String)].on(x => (x._3, x._1)))
    println(pairs(0))
  }

  /**
    * 测试
    * @param name
    * @param age
    */
  case class Person(name: String, age: Int) {
    override def toString = {
      "name: " + name + ", age: " + age
    }
  }

  /**
    * 隐式转换在main执行之前执行，可作为初始化使用，相当于java的静态代码块
    * 隐式转换为可比较对象
    */
  implicit object PersonOrdering extends Ordering[Person] {
    override def compare(p1: Person, p2: Person): Int = {
      p1.name == p2.name match {
        case false => -p1.name.compareTo(p2.name)
        case _ => p1.age - p2.age
      }
    }
  }

  val p1 = new Person("rain", 13)
  val p2 = new Person("rain", 14)
  import Ordered._
  println(p1 < p2) // True

  /**
    * 使用sorted函数做排序，则需要指定Ordering隐式参数：就是上面的
    * implicit object PersonOrdering extends Ordering[Person]
    */
  val p3 = new Person("Lily", 15)
  val list = List(p1, p2, p3)
  println(list.sorted)

  /**
    * 若使用sortWith，则需要定义返回值为Boolean的比较函数：
    */
  println(list.sortWith { (p1: Person, p2: Person) =>
    p1.name == p2.name match {
      case false => -p1.name.compareTo(p2.name) < 0
      case _ => p1.age - p2.age < 0
    }
  })

  /**
    * 若使用sortBy，也需要指定Ordering隐式参数：就是上面的
    * implicit object PersonOrdering extends Ordering[Person]
    */
  println(list.sortBy[Person](t => t))

  /**
    * RDD的sortBy函数，提供根据指定的key对RDD做全局的排序。
    */
  def testSortRDD(sc: SparkContext): Unit ={
    val rdd = sc.parallelize(Array(new Person("rain", 24),
      new Person("rain", 22), new Person("Lily", 15)))

    /**
      * 指定Ordering隐式参数
      */
    implicit object PersonOrdering extends Ordering[Person] {
      override def compare(p1: Person, p2: Person): Int = {
        p1.name == p2.name match {      //先根据name排序，如果name相同的话，再按照年龄排序
          case false => -p1.name.compareTo(p2.name)
          case _ => p1.age - p2.age
        }
      }
    }

    rdd.sortBy[Person](t => t).foreach(f => {
      println(f)
    })
  }


}
