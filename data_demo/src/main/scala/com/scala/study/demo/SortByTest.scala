package com.scala.study.demo

import scala.collection.mutable.ListBuffer

/**
  * ListBuffer的SortBy
  */
object SortByTest {

  case class Person(name:String,age:Int,sex:String)

  def main(args: Array[String]): Unit = {
    val person1 = new Person("a",12,"f")
    val person2 = new Person("b",11,"m")
    val person22 = new Person("bb",18,sex = "f")
    val person3 = new Person("c",18,"m")
    val person4 = new Person("u",15,"f")
    val person5 = new Person("f",16,"m")

    var list = new ListBuffer[Person]
    list.+=(person1,person2,person3,person4,person5,person22)
    //自定义规则使用
//    println(list.sortBy(sortRule)(Ordering.Tuple2(Ordering.Int.reverse,Ordering.String.reverse)))
    //ListBuffer(Person(c,18,m), Person(bb,18,f), Person(f,16,m), Person(u,15,f), Person(a,12,f), Person(b,11,m))


    //直接调用使用 Ordering.Int.reverse设置排序降序排序，从大到小
    println(list.sortBy(f => {
      f.age
    })(Ordering.Int.reverse))



  }

  /**
    * 排序规则，操作的是对象的某几个属性
    * @param person
    * @return
    */
  def sortRule(person:Person):(Int,String) = {
    (person.age,person.sex)
  }

  def getRuleOne(person:Person):Int = {
    person.age
  }
}
