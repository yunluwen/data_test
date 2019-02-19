package com.scala.study.string

/**
  * String and StringBuilder
  */
object StringDemo {

  def main(args: Array[String]): Unit = {
    testType()
    testEq()
    testGenerate()

  }

  def testType(): Unit ={

    println("hello world".getClass.getName)  //java.lang.String
    println("hello".length)
    "hello".foreach(println)
    for(c <- "hello") println(c)
    "hello".getBytes.foreach(println)  //字节序列
    println("hello world".filter(_ != 'l'))
    println("hello world".capitalize)  //首字母大写
    println("hello".drop(2))   //删除前几个元素
    println("hello".take(2))   //获取前两个元素
    println("HELLO".toLowerCase)  //大小写
    println("hello".toUpperCase)
  }

  /**
    * 测试相等性
    */
  def testEq(): Unit ={
    val s1 = "hello"
    val s2 = "hello"
    val s3 = "h" + "ello"
    println(s1 == s2)
    println(s2 == s3) //使用==的优点是即使字符串为空，也不会抛出空指针异常
    //注意一个为null的字符串调用方法会出现空指针异常
    //忽略大小写比较两个字符串
    println("hellO".equalsIgnoreCase("hello"))

  }

  def testGenerate(): Unit ={
    val foo =
      """
        |this is three
        |string
        |ha ha ha!!!
      """.stripMargin.replace("\n"," ")
    println(foo)

    "hello world".split(" ").map(_.trim)



  }

}
