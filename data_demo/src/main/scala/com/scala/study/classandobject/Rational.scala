package com.scala.study.classandobject

/**
  * that 的用法
  * @param n
  * @param d 不在类作用域范围内，无法通过that.d访问，需要定义类的成员变量进行访问
  *          Scala 也使用 this 来引用当前对象本身，一般来说访问类成员时无需使用 this
  */
class Rational (n:Int, d:Int) {  //主构造函数
  require(d!=0)
  val number =n
  val denom =d
  override def toString = number + "/" +denom
  def add(that:Rational)  =
    new Rational(
      this.number * that.denom + that.number* this.denom,
      this.denom * that.denom
    )

  def lessThan(that:Rational) =
    this.number * that.denom < that.number * this.denom
  //如果需要引用对象自身，this 就无法省略
  def max(that:Rational) =
    if(lessThan(that)) that else this

  //Scala 定义辅助构造函数使用 this(…)的语法，所有辅助构造函数名称为 this。
  def this(n:Int) = this(n,1)   //从构造函数
  //在 Scala 中也只有主构造函数才能调用基类的构造函数，这种限制有它的优点，
  // 使得 Scala 构造函数更加简洁和提高一致性。


}