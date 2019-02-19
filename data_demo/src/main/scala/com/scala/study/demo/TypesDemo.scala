package com.scala.study.demo

/**
  * 类型参数化和变化型注解
  */
object TypesDemo {

//  def main(args: Array[String]): Unit = {
//    var fruitBox: Box[Fruit] = new AppleBox(new Apple())
//    var fruit: Fruit = fruitBox.fruit
//    println(fruit.name)
//
//    var apple: Apple = new Apple
//    var box: Box2[Fruit] = new Box2[Orange](new Orange)
//    box = box.method(apple)
//  }


}

abstract class Fruit {
  def name: String
}
class Orange extends Fruit {
  def name = "orange"
}
class Apple extends Fruit {
  def name = "apple"
}

abstract class Box3[+F <: Fruit] {      //协变类型
  def fruit: F
  def contains(aFruit: Fruit) = fruit.name.equals(aFruit.name)
}

class OrangeBox2(orange: Orange) extends Box[Orange] {
  def fruit = orange
}

class AppleBox2(apple: Apple) extends Box[Apple] {
  def fruit = apple
}

class Box2[+T](fruit: T) {
  def method[U >: T](p: U) = { new Box2[U](p) }
}

