package com.scala.study.demo

/**
  * 伴生对象
  * 私有构造函数，防止外部实例化
  *
  * @param name
  */
class ObjectTest private (val name : String){
  private def getUniqueSkill() = name + "...lalala..."+ ObjectTest.uniqueSkill
}

object ObjectTest {
  private val uniqueSkill = "scala!"
  private val obj = new ObjectTest("zyh")
  def printUniqueSkill() = println(obj.getUniqueSkill())
}

object TestObject {
  def main(args: Array[String]): Unit = {
    ObjectTest.printUniqueSkill()
  }
}

/**
  * * 对象私有属性测试
  * * 对于class,会自动创建getter和setter方法
  * * 总结：
  * * 1.如果字段前没有任何修饰符(如var age)，则字段是私有的，getter和setter方法是公有的
  * *
  * * 2.如果字段是私有的(如private var age)，则getter和setter方法也是私有的
  * *
  * * 3.如果字段是val(如val  age)，则只有getter方法被生成  （scala会生成私有的final字段和getter，但没有setter）
  * *
  * * 4.如果你不需要任何getter和setter，可以将字段声明为private[this](如private[this] var age)
  * * ---------------------
  */
class PrivateTest{
  private val age = 0  //必须赋值，否则编译不过,私有属性无法访问到，则getter和setter方法也是私有的
  //类的方法可以访问这个类的所有对象的私有字段(或私有属性)
  def isYunner(other:PrivateTest)= other.age<age

  //private[this]限定了这个类的方法只能访问自己对象的属性private name,不能访问其他对象(即使这个对象是根据这个类)
  //生成的属性，即这个属性只能是当前对象私有，isxiaoming()不能访问别的对象的私有成员。
  private[this] val name = "zhangsan"   //在类中每个实例对象只能访问到自己的这个属性，别的对象无法访问到
  def isxiaoming() = name

}

object PrivateTestObj{
  def main(args: Array[String]): Unit = {
    val obj = new PrivateTest
    println(obj.isxiaoming())
  }
}





















