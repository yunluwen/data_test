package com.scala.study.demo

/**
  * 类型参数化和变化型注解
  *
  * 总结 : 参数是逆变的或者不变的，返回值是协变的或者不变的。
  *
  * 函数在参数上是逆变的，在返回值上则是协变的。通常而言，对于某个对象消费的值适用逆变，而对于它产出的值适用协变。
  * 如果一个对象同时消费和产出某值，则类型应该保持不变。这通常适用于可变数据结构，比如标准库中的Array、ArrayBuffer、ListBuffer等。
  */
object ChangeTest {

  def main(args: Array[String]): Unit = {
    //如果Box类变不使用协变的话，会造成类型不匹配的异常
//    var fruitBox: Box[Fruit] = new AppleBox(new Apple)
//    var fruit: Fruit = fruitBox.fruit
//    println(fruit.name)

//    val test = new Test[Fruit]
//    println(test.method(new Apple()).name)
//    println(test.method2(new Orange()).name)   //orange是fruit的下界
//    println(test.method3(new Orange()).name)
//
//    val test2 = new Test[Orange]
//    println(test2.method3(new Fruit {          //orange是fruit的上界
//      override def name: String = "mihoutao"
//    }).name)

    val test3 = new Test2[Orange]    //协变
    println(test3.method(new Apple()).name)

    val test4 = new Test3[Fruit]     //逆变,传入的参数需要是Fruit的子类
    println(test4.method(new Orange()).name)

  }


}

/**
  * 假设声明了class Orange extends Fruit，而后class Box[A]中的A可以有前缀 + 或 -
  *  A，没有任何注解，是不变型。该状态下，Box[Orange]和Box[Fruit]没有任何继承关系
  *  +A，是协变类型。此时，Box[Orange]是Box[Fruit]的子类型，并且变量声明val f: Box[Fruit] = new Box[Orange]()是允许的
  *  -A，是逆变类型。此时，Box[Fruit]是Box[Orange]的子类型，并且变量声明val f: Box[Orange] = new Box[Fruit]()是允许的
  * ---------------------
  *
  * @tparam F
  */
abstract class Box[+F <: Fruit] {   //协变
  def fruit: F
  def contains(aFruit: Fruit) = fruit.name.equals(aFruit.name)
}

class OrangeBox(orange: Orange) extends Box[Orange] {
  def fruit = orange
}

class AppleBox(apple: Apple) extends Box[Apple] {
  def fruit = apple
}

/**
  * U >: T，定义了 T 为 U 的下界，结果，U 必须是 T 的超类型
  * U <: T,定义了 T 为 U 的上界 ，T必须是U的超类型
  *
  * 下面的几种变化型参数均为合法的语法格式：
  *
  * abstract class Box[+A]{ def foo(): A }
  * abstract class Box[-A]{ def foo(a: A) }
  * abstract class Box[+A]{ def foo[B >: A](b: B) }
  * abstract class Box[-A]{ def foo[B <: A](): B}
  * ---------------------
  */

/**
  * 为了对用到类型参数的地方进行分类，编译器首先从类型参数的声明开始，然后进入更深的内嵌层。
  * 处于声明类的最顶层被划为正，默认情况下，更深的内嵌层的地方的分类会与它外层一致，
  *   但仍有几种特殊情况可以使得类型参数的类型发生翻转：
  * @tparam T
  */
class Test[T]{     //不变型:返回值是T的父类或者子类都可以  //协变型:要求返回值是T的父类  //逆变型:要求返回值是T的子类
  /**
    * 参数T在逆变的位置:
    *   方法的值参数位置的参数类型
    *
    */
  def method(param: T): T ={
    param
  }

  /**
    * 参数T在逆变的位置:
    *   方法的类型参数子句位置的参数类型
    *
    * @param param
    * @tparam U 是 T 的下界
    */
  def method2[U <: T](param:U): U ={
    param
  }

  /**
    * 参数T在协变的位置：
    *   方法的值参数位置的参数类型
    *   方法的类型参数子句位置的参数类型
    *
    * @param param
    * @tparam U
    * @return
    */
  def method3[U >: T](param:U): U ={
    param
  }
}

/**
  * 应用场景：给一个函数参数（或变量）赋一个函数值。
  * 输入参数类型 - 不变规则：给一个函数参数赋一个函数值时，传入函数的输入参数类型，可以是函数参数对应的泛型参数类型。
  * 输入参数类型 - 逆变规则：给一个函数参数赋一个函数值时，传入函数的输入参数类型，可以是函数参数对应的泛型参数类型的父类。
  * 输入参数类型 - 协变不能规则：给一个函数参数赋一个函数值时，传入函数的输入参数类型，不能是函数参数对应的泛型参数类型的子类。
  * 输出参数类型 - 不变规则：给一个函数参数赋一个函数值时，传入函数的返回值类型，可以是函数参数对应的泛型参数类型。
  * 输出参数类型 - 协变规则：给一个函数参数赋一个函数值时，传入函数的返回值类型，可以是函数参数对应的泛型参数类型的子类。
  * 输出参数类型 - 逆变不能规则：给一个函数参数赋一个函数值时，传入函数的返回值类型，不能是函数参数对应的泛型参数类型的父类。
  */
class Test2[+T]{   //协变:返回T的父类

  def method[U >: T](param : U): U ={
    param
  }

}

class Test3[-T]{   //逆变:返回T的子类

  def method[U <: T](param : U): U ={
    param
  }

}