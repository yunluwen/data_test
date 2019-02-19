package com.scala.study.demo

/**
  * implicit demo
  * 个人理解：隐式转换主要增加某个特定类型(只能带有单个参数)的功能
  * 注意事项： 隐式类的主构造函数参数有且仅有一个！！！之所以只能有一个参数，是因为隐式转换是将一种类型
  * 转换为另外一种类型，源类型与目标类型是一一对应的
  *
  * 隐式转换可以实现类似python装饰器的功能
  *
  * 隐式转换是使用implicit修饰的带有单个参数的普通函数。
  * 这种函数将自动应用，将值从一种类型转换为另一种类型。
  *
  * 隐式转换可以为现有的类库添加功能
  *
  *
  */
object ImplicitDemo {

  def main(args: Array[String]): Unit = {
    println(quote("hello lalala..."))
    println(smaller(22,88))

//    import Test2._    //--将整个object导进来
//    println(1.add(999))  //--看隐式类的构造器是不是传入int型参数，是就可以调用隐式类中的方法

  }

  /**
    * 隐式参数
    * 函数或方法可以带有一个标记为implicit的参数列表。该情况下，编译器会查找缺省值，提供给该函数或方法。
    *
    * 编译器的查找位置：
    * 当前作用域所有可用单个标识符指代的满足类型要求的val和def
    *
    * @param left
    * @param right
    */
  case class Delimiters(left: String, right: String)

  def quote(what: String)(implicit delims: Delimiters) =
    delims.left + what + delims.right

  implicit val quoteDelimiters = Delimiters("<<", ">>")

  /**
    * 利用隐式参数进行隐式转换
    *
    * 获取两个数的小值
    * @param a
    * @param b
    * @param order
    * @tparam T
    * @return
    */
//  由于不知道a和b两种类型是否有<比较符，所以编辑器不通过
//  def smaller[T](a: T, b: T) = if (a < b) a else b

  def smaller[T](a: T, b: T)(implicit order: T => Ordered[T])
    = if (a < b) a else b

}

/**
  * 隐式类的使用
  */
//object Test2{
//  implicit class AAA(i: Int){
//    def add(j:Int)= i+j
//  }
//}




