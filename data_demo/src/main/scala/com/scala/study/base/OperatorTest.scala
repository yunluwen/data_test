package com.scala.study.base

/**
  * scala中:: , +:, :+, :::, ++的区别
  */
object OperatorTest {

  //获取一个对象的类型
  def manOf[T: Manifest](t: T): Manifest[T] = manifest[T]

  def main(args: Array[String]): Unit = {

    /**
      * :: 该方法被称为cons，意为构造，向队列的头部追加数据，创造新的列表。
      * 用法为 x::list,其中x为加入到头部的元素，无论x是列表与否，
      * 它都只将成为新生成列表的第一个元素，也就是说新生成的列表长度为list的长度＋1(btw, x::list等价于list.::(x))
      */
    val a = "A"::"B"::"C"::"D"::"E"::"F"::"G"::Nil
    println(manOf(a))
    println(a)

    /**
      * :+和+: 两者的区别在于:+方法用于在尾部追加元素，+:方法用于在头部追加元素，和::很类似，
      * 但是::可以用于pattern match(模式匹配) ，而+:则不行. 关于+:和:+,只要记住冒号永远靠近集合类型就OK了。
      */
    val b = "A"+:"B"+:Nil
    println(manOf(b))
    println(b)

    val c = Nil:+"A":+"B"
    println(manOf(c))
    println(c)

    /**
      * ++ 该方法用于连接两个集合，list1++list2
      */
    val d = a++b
    println(manOf(d))
    println(d)

    /**
      * ::: 该方法只能用于连接两个List类型的集合
      */
    val e = a ::: b
    println(manOf(e))      //scala.collection.immutable.List[java.lang.String]
    println(e)             //List(A, B, A, B)

    val f = a :: b
    println(manOf(f))      //scala.collection.immutable.List[Object with java.io.Serializable]
    println(f)             //List(List(A, B), A, B)


    /**
      * ::用于模式匹配
      */
    listMatch(a)

  }

  def listMatch(list: List[String]): String ={
    list match {
      case Nil => null
      case head :: tail => {     //  （List的头一个元素） 和  （除第一个元素外的其他元素）
        println(head)            //A
        println(tail)            //List(B, C, D, E, F, G)
        head
      }
    }
  }

}
