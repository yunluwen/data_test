package com.zyh.batch


import org.apache.flink.api.common.operators.base.JoinOperatorBase.JoinHint
import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment}
import org.apache.flink.util.Collector   //注意使用的是flink的Collector

/**
  * 测试操作算子
  */
import org.apache.flink.api.scala._   //scala的flink程序必须引入这个

case class Student(age: Int, name: String,height:Double)

object TransformationOperation_scala {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
//    testMath(env)
//    testMap(env)
//    testMapPartition(env)
//    testFilter(env)
//    testReduce(env)
//    testGroupBy(env)
//    testReduceGroup(env)
//    testSortGroup(env)
//    testMinMaxBy(env)
//    testDistinct(env)
//    testJoin(env)
//    testLeftOuterJoin(env)
//    testRightOuterJoin(env)
//    testFullOuterJoin(env)
//    testCross(env)
    testUnion(env)
    testFirst(env)

  }

  def testMath(benv:ExecutionEnvironment): Unit ={
    //1.创建DataSet[Student]
    val input: DataSet[Student] = benv.fromElements(Student(16,"zhangasn",194.5),
      Student(17,"zhangasn",184.5),
      Student(18,"zhangasn",174.5),
      Student(16,"lisi",194.5),
      Student(17,"lisi",184.5),
      Student(18,"lisi",174.5))

    println(input.getType())

    //2.获取age最小的元素
    val output0: DataSet[Student] = input.min(0)
    output0.print()

    //3.获取age最小的元素
    val output1: DataSet[Student] = input.min("age")
    output1.print()

    //4.获取age最大的元素
    val output2: DataSet[Student] = input.max(0)
    output2.print()

    //5.获取age最大的元素
    val output3: DataSet[Student] = input.max("age")
    output3.print()

    //2.fieldIndex=0的列进行sum
    val s0=input.sum(0)
    s0.collect

    //3.fieldName="age"的列进行sum
    val s1=input.sum("age")
    s1.collect

    //4.fieldName="height"的列进行sum
    val s2=input.sum("height")
    s2.collect
  }

  def testMap(benv:ExecutionEnvironment): Unit ={
    //1.创建一个 DataSet其元素为String类型
    val input: DataSet[String] = benv.fromElements("A B C D E F G")

    //2.获取DataSet的元素的类型信息
    println(input.getType)    //可以用于调试程序

    //1.创建一个DataSet其元素为Int类型
    val input2: DataSet[Int] = benv.fromElements(23, 67, 18, 29, 32, 56, 4, 27)

    //2.将DataSet中的每个元素乘以2
    val result=input2.map(_*2)

    //3.将DataSet中的每个元素输出出来
    println(result.collect)

    //1.创建一个DataSet[(Int, Int)]
    val intPairs: DataSet[(Int, Int)] = benv.fromElements((18,4),(19,5),(23,6),(38,3))
    //2.键值对的key+value之和生成新的dataset
    val intSums = intPairs.map { pair => pair._1 + pair._2 }
    //3.显示结果
    println(intSums.collect)

    /**
      * 测试flatMap
      */
    val output = input.flatMap{ line => line.split(" ")}
    println(output.collect())

  }

  def testMapPartition(benv:ExecutionEnvironment): Unit ={
    //1.创建一个 DataSet其元素为String类型
    val input: DataSet[String] = benv.fromElements("zhangsan boy", "lisi is a girl so sex")

    //2.获取partition的个数
    val result=input.mapPartition{in => Some(in.size)}

    //3.将结果显示出来
    println(result.collect)

  }

  def testFilter(benv:ExecutionEnvironment): Unit ={
    //1.创建一个 DataSet其元素为String类型
    val input: DataSet[String] = benv.fromElements("zhangsan boy", "lisi is a girl so sex")
    //过滤出包含boy的字符
    val output = input.filter{ _.contains("boy") }
    println(output.collect())

    //1.创建一个DataSet[Int]
    val intNumbers: DataSet[Int] =  benv.fromElements(2,4,6,2,3,7)
    //2.过滤偶数
    val naturalNumbers = intNumbers.filter { _ %2== 0 }
    //3.显示结果
    println(naturalNumbers.collect)
  }

  def testReduce(benv:ExecutionEnvironment): Unit ={
    //Int类型的DataSet做reduce
    val a: DataSet[Int] = benv.fromElements(2,5,9,8,7,3)
    val b: DataSet[Int] = a.reduce { _ + _ }
    println(b.collect)

    //String类型的DataSet做reduce
    val a1: DataSet[String] = benv.fromElements("zhangsan boy", " lisi girl")
    val b1:DataSet[String] = a1.reduce { _ + _ }
    println(b1.collect)
  }

  /**
    * 测试group By
    * @param benv
    */
  def testGroupBy(benv:ExecutionEnvironment): Unit ={
    //1.定义 class (示例class,必须定义在函数的内部，不然算子内部可能无法访问到元素)
    case class WC(val word: String, val salary: Int)
    //2.定义DataSet[WC]
    val words: DataSet[WC] = benv.fromElements(
      WC("LISI",600),WC("LISI",400),WC("WANGWU",300),WC("ZHAOLIU",700))

    //3.使用groupBy算子
    val wc1 = words.groupBy("word").reduce {
      (w1,w2) => new WC(w1.word,w1.salary + w2.salary)
    }
    println(wc1.collect())

    //4.使用自定义的reduce方法,使用key-selector
    val wc2 = words.groupBy { _.word } reduce {
      (w1, w2) => new WC(w1.word, w1.salary + w2.salary)
    }
    println(wc2.collect())

    //1.定义 case class
    case class Student(val name: String, addr: String, salary: Double)

    //2.定义DataSet[Student]
    val tuples:DataSet[Student] = benv.fromElements(
      Student("lisi","shandong",2400.00),Student("zhangsan","henan",2600.00),
      Student("lisi","shandong",2700.00),Student("lisi","guangdong",2800.00))

    //3.使用自定义的reduce方法,使用多个Case Class Fields name
    val reducedTuples1 = tuples.groupBy("name", "addr").reduce {
      (s1, s2) => Student(s1.name+"-"+s2.name,s1.addr+"-"+s2.addr,s1.salary+s2.salary)
    }

    //4.使用自定义的reduce方法,使用多个Case Class Fields index
    val reducedTuples2 = tuples.groupBy(0, 1).reduce {
      (s1, s2) => Student(s1.name+"-"+s2.name,s1.addr+"-"+s2.addr,s1.salary+s2.salary)
    }

    //5.使用自定义的reduce方法,name和index混用
    val reducedTuples3 = tuples.groupBy(0, 1).reduce {
      (s1, s2) => Student(s1.name+"-"+s2.name,s1.addr+"-"+s2.addr,s1.salary+s2.salary)
    }


    //6.显示结果
    println(reducedTuples1.collect)
    println(reducedTuples2.collect)
    println(reducedTuples3.collect)
  }

  /**
    * 分组reduce
    * @param benv
    */
  def testReduceGroup(benv:ExecutionEnvironment): Unit ={
    //1.定义 DataSet[(Int, String)]
    val input: DataSet[(Int, String)] = benv.fromElements(
      (20,"zhangsan"),(22,"zhangsan"),
      (22,"lisi"),(20,"zhangsan"))

    val group = input.groupBy(1).reduceGroup {
      //将相同的元素用set去重
      (in,out: Collector[(Int, String)]) =>    //注意这里的使用方式
        in.toSet foreach (out.collect)
    }
    println(group.collect())

    //操作case class
    //1.定义case class
    case class Student(age: Int, name: String)

    //2.创建DataSet[Student]
    val input2: DataSet[Student] = benv.fromElements(
      Student(20,"zhangsan"),
      Student(22,"zhangsan"),
      Student(22,"lisi"),
      Student(20,"zhangsan"))
    //3.以age进行分组，然后对分组进行reduceGroup
    val output = input2.groupBy(_.age).reduceGroup {
      //将相同的元素用set去重
      (in, out: Collector[Student]) =>
        in.toSet foreach (out.collect)
    }

    //4.显示结果
    println(output.collect)
  }

  /**
    * 分组排序
    * @param benv
    */
  def testSortGroup(benv:ExecutionEnvironment): Unit ={
    //1.创建 DataSet[(Int, String)]
    val input: DataSet[(Int, String)] = benv.fromElements(
      (20,"zhangsan"),
      (22,"zhangsan"),
      (22,"lisi"),
      (22,"lisi"),
      (22,"lisi"),
      (18,"zhangsan"),
      (18,"zhangsan"))

    //2.用int分组，用int对分组进行排序
    import org.apache.flink.api.common.operators.Order   //注意手动引入这个枚举类
    val sortdata = input.groupBy(0).sortGroup(0,Order.ASCENDING)

    //3.对排序好的分组进行reduceGroup
    val outputdata =sortdata.reduceGroup {
      //将相同的元素用set去重
      (in, out: Collector[(Int, String)]) =>
        in.toSet foreach (out.collect)
    }
    println(outputdata.collect())
  }

  /**
    * MinBy:
    *   在分组后的数据中，获取每组最小的元素。
    * MaxBy:
    *   在分组后的数据中，获取每组最大的元素。
    * @param benv
    */
  def testMinMaxBy(benv:ExecutionEnvironment): Unit ={
    //1.定义case class
    case class Student(age: Int, name: String,height:Double)

    //2.创建DataSet[Student]
    val input: DataSet[Student] = benv.fromElements(
      Student(16,"zhangasn",194.5),
      Student(17,"zhangasn",184.5),
      Student(18,"zhangasn",174.5),
      Student(16,"lisi",194.5),
      Student(17,"lisi",184.5),
      Student(18,"lisi",174.5))

    //获取每组age最小的数据
    val output1 = input.groupBy(1).minBy(0)
    val out1 = input.groupBy(_.name).minBy(0)
    //获取每组age和身高最小的数据 (按顺序来)
    val output2 = input.groupBy(1).minBy(2,0)
    val out2 = input.groupBy(1).maxBy(0,2)
    println(output1.collect())
    println(output2.collect())

    //获取每组age最大的元素
    val output3 = input.groupBy(1).maxBy(0)
    val out3 = input.groupBy(_.name).maxBy(0)
    val output4 = input.groupBy(1).maxBy(0,2)
    val out4 = input.groupBy(1).maxBy(2,0)
    println(output3.collect())
    println(output4.collect())
  }

  /**
    * distinct算子:去重操作
    * @param benv
    */
  def testDistinct(benv:ExecutionEnvironment): Unit ={
    //1.创建一个 DataSet其元素为String类型
    val input: DataSet[String] = benv.fromElements("lisi","zhangsan", "lisi","wangwu")
    //2.元素去重
    val result=input.distinct()
    //3.显示结果
    println(result.collect)

    //多项目的去重，不指定比较项目，默认是全部比较
    //1.创建DataSet[(Int, String, Double)]
    val input2: DataSet[(Int, String, Double)] =  benv.fromElements(
      (2,"zhagnsan",1654.5),(3,"lisi",2347.8),(2,"zhagnsan",1654.5),
      (4,"wangwu",1478.9),(5,"zhaoliu",987.3),(2,"zhagnsan",1654.0))
    //2.元素去重
    val output = input2.distinct()
    //3.显示结果
    println(output.collect)

    //多项目的去重，指定比较项目
    //1.创建DataSet[(Int, String, Double)]
    val input3: DataSet[(Int, String, Double)] =  benv.fromElements(
      (2,"zhagnsan",1654.5),(3,"lisi",2347.8),(2,"zhagnsan",1654.5),
      (4,"wangwu",1478.9),(5,"zhaoliu",987.3),(2,"zhagnsan",1654.0))
    //2.元素去重:指定比较第0和第1号元素,去掉第0和第1号元素相同的元素
    val output2 = input3.distinct(0,1)
    //3.显示结果
    println(output2.collect)

    //case class的去重，指定比较项目
    //1.创建case class Student
    case class Student(name : String, age : Int)

    //2.创建DataSet[Student]
    val input4: DataSet[Student] =  benv.fromElements(
      Student("zhangsan",24),Student("zhangsan",24),Student("zhangsan",25),
      Student("lisi",24),Student("wangwu",24),Student("lisi",25))

    //3.去掉age重复的元素
    val age_r = input4.distinct("age")
    println(age_r.collect)

    //4.去掉name重复的元素
    val name_r = input4.distinct("name")
    println(name_r.collect)

    //5.去掉name和age重复的元素
    val all_r = input4.distinct("age","name")
    println(all_r.collect)

    //6.去掉name和age重复的元素
    val all = input4.distinct()
    println(all.collect)

    //7.去掉name和age重复的元素
    val all0 = input4.distinct("_")
    println(all0.collect)

    /**
      * 根据表达式进行去重
      */
    //1.创建DataSet[Int]
    val input5: DataSet[Int] = benv.fromElements(3,-3,4,-4,6,-5,7)
    //2.根据表达式，本例中是根据元素的绝对值进行元素去重
    val output5 = input5.distinct {x => Math.abs(x)}
    //3.显示结果
    println(output5.collect)
  }

  /**
    * 重点：join算子
    * 将两个DataSet进行join操作
    * @param benv
    */
  def testJoin(benv:ExecutionEnvironment): Unit ={
    //1.创建一个 DataSet其元素为[(Int,String)]类型
    val input1: DataSet[(Int, String)] =  benv.fromElements(
      (2,"zhagnsan"),(3,"lisi"),(4,"wangwu"),(5,"zhaoliu"))
    //2.创建一个 DataSet其元素为[(Double, Int)]类型
    val input2: DataSet[(Double, Int)] =  benv.fromElements(
      (1850.98,4),(1950.98,5),(2350.98,6),(3850.98,3))
    /**
      * 注意这里的条件：
      * 两个DataSet进行join操作，条件是input1(0)==input2(1)
      */
    val result = input1.join(input2).where(0).equalTo(1)
    println(result.collect())

    //1.定义case class
    case class Rating(name: String, category: String, points: Int)
    //2.定义DataSet[Rating]
    val ratings: DataSet[Rating] = benv.fromElements(
      Rating("moon","youny1",3),Rating("sun","youny2",4),
      Rating("cat","youny3",1),Rating("dog","youny4",5))
    //3.创建DataSet[(String, Double)]
    val weights: DataSet[(String, Double)] = benv.fromElements(
      ("youny1",4.3),("youny2",7.2),
      ("youny3",9.0),("youny4",1.5))
    //4.使用方法进行join 条件 ratings(category) == weights(0)
    val weightedRatings = ratings.join(weights).where("category").equalTo(0) {
      //要返回的数据
      (rating, weight) => (rating.name, rating.points + weight._2)
    }
    println(weightedRatings.collect())

    /**
      * 第三个例子
      */
    val weightedRatings2 = ratings.join(weights).where("category").equalTo(0) {
      (rating, weight, out: Collector[(String, Double)]) =>
        //返回的操作
        if (weight._2 > 0.1) out.collect(rating.name, rating.points * weight._2)
    }
    println(weightedRatings2.collect)

    //在执行join操作时暗示数据大小，可以帮助flink优化它的执行策略，提高执行效率。
    //1.定义DataSet[(Int, String)]
    val input4: DataSet[(Int, String)] =
    benv.fromElements((3,"zhangsan"),(2,"lisi"),(4,"wangwu"),(6,"zhaoliu"))

    //2.定义 DataSet[(Int, String)]
    val input5: DataSet[(Int, String)] =
      benv.fromElements((4000,"zhangsan"),(70000,"lisi"),(4600,"wangwu"),(53000,"zhaoliu"))

    // 3.暗示第二个输入很小
    val result1 = input4.joinWithTiny(input5).where(1).equalTo(1)
    println(result1.collect)

    // 4.暗示第二个输入很大
    val result2 = input4.joinWithHuge(input5).where(1).equalTo(1)
    println(result2.collect)

    //flink有很多种执行join的策略，你可以指定一个执行策略，以便提高执行效率。
    //2.暗示input2很小
    val result3 = input4.join(input5, JoinHint.BROADCAST_HASH_FIRST).where(1).equalTo(1)
    //3.显示结果
    result3.collect

    /**
      * 暗示有如下选项：
      * 1.JoinHint.OPTIMIZER_CHOOSES:
      * 没有明确暗示，让系统自行选择。
      * 2.JoinHint.BROADCAST_HASH_FIRST
      * 把第一个输入转化成一个哈希表，并广播出去。适用于第一个输入数据较小的情况。
      * 3.JoinHint.BROADCAST_HASH_SECOND:
      * 把第二个输入转化成一个哈希表，并广播出去。适用于第二个输入数据较小的情况。
      * 4.JoinHint.REPARTITION_HASH_FIRST:（defalut）
      *     1.如果输入没有分区，系统将把输入重分区。
      *     2.系统将把第一个输入转化成一个哈希表广播出去。
      *     3.两个输入依然比较大。
      *     4.适用于第一个输入小于第二个输入的情况。
      * 5.JoinHint.REPARTITION_HASH_SECOND:
      *     1.如果输入没有分区，系统将把输入重分区。
      *     2.系统将把第二个输入转化成一个哈希表广播出去。
      *     3.两个输入依然比较大。
      *     4.适用于第二个输入小于第一个输入的情况。
      * 6.JoinHint.REPARTITION_SORT_MERGE:
      *     1.如果输入没有分区，系统将把输入重分区。
      *     2.如果输入没有排序，系统将吧输入重排序。
      *     3.系统将合并两个排序好的输入。
      *     4.适用于一个或两个分区已经排序好的情况。
      */

  }

  def testLeftOuterJoin(benv:ExecutionEnvironment): Unit ={
    //1.定义case class
    case class Rating(name: String, category: String, points: Int)

    //2.定义 DataSet[Rating]
    val ratings: DataSet[Rating] = benv.fromElements(
      Rating("moon","youny1",3),Rating("sun","youny2",4),
      Rating("cat","youny3",1),Rating("dog","youny4",5),Rating("tiger","youny4",5))

    //3.定义DataSet[(String, String)]
    val movies: DataSet[(String, String)]  = benv.fromElements(
      ("moon","ok"),("dog","good"),
      ("cat","notbad"),("sun","nice"),("water","nice"))

    /**
      * 注意这里，可以设置返回多个值
      */
    val result = movies.leftOuterJoin(ratings).where(0).equalTo("name"){
      (m,r) => (m._1,m._2,if (r == null) -1 else r.points)
    }
    println(result.collect())


    val result1 = movies.leftOuterJoin(ratings).where(0).equalTo("name"){
      (m,r) => (m._1,if (r == null) -1 else r.points)
    }
    println(result1.collect())

    //4.两个dataset进行左外连接，指定连接暗示，并指定连接方法
    val result2 = movies.leftOuterJoin(ratings, JoinHint.REPARTITION_SORT_MERGE)
      .where(0).equalTo("name"){
      (m, r) => (m._1, if (r == null) -1 else r.points)
    }
    //5.显示结果
    println(result2 .collect)

    /**
      * 左外连接支持以下项目：
      *     JoinHint.OPTIMIZER_CHOOSES
      *     JoinHint.BROADCAST_HASH_SECOND
      *     JoinHint.REPARTITION_HASH_SECOND
      *     JoinHint.REPARTITION_SORT_MERGE
      * ---------------------
      */
  }

  /**
    * 右外连接
    * @param benv
    */
  def testRightOuterJoin(benv:ExecutionEnvironment): Unit ={
    //1.定义case class
    case class Rating(name: String, category: String, points: Int)

    //2.定义 DataSet[Rating]
    val ratings: DataSet[Rating] = benv.fromElements(
      Rating("moon","youny1",3),Rating("sun","youny2",4),
      Rating("cat","youny3",1),Rating("dog","youny4",5),Rating("tiger","youny4",5))

    //3.定义DataSet[(String, String)]
    val movies: DataSet[(String, String)]  = benv.fromElements(
      ("moon","ok"),("dog","good"),
      ("cat","notbad"),("sun","nice"),("water","nice"))

    val result = ratings.rightOuterJoin(movies).where("name").equalTo(0) {
      (r, m) =>
        //注意这里：如果使用左外连接或者右外连接的时候，需要判断一边的数据是否为空，防止空指针异常
        (if (r == null) -1 else r.name, if (r == null) -1 else r.points,
          m._2)
    }
    println(result.collect())

    //3.两个dataset进行右外连接，暗示连接方式，指定连接方法
    val result1 = movies.rightOuterJoin(ratings,JoinHint.BROADCAST_HASH_FIRST).where(0).equalTo("name"){
      (m, r) => (if (m == null) -1 else m._1, r.points)
    }
    //5.显示结果
    println(result1.collect)

    /**
      * 右外连接支持以下项目：
      *     JoinHint.OPTIMIZER_CHOOSES
      *     JoinHint.BROADCAST_HASH_FIRST
      *     JoinHint.REPARTITION_HASH_FIRST
      *     JoinHint.REPARTITION_SORT_MERGE
      * ---------------------
      */
  }

  /**
    * 全外连接：保留两边的全部
    * join属于内连接，只保留相同的部分
    * @param benv
    */
  def testFullOuterJoin(benv:ExecutionEnvironment): Unit ={
    //1.定义case class
    case class Rating(name: String, category: String, points: Int)

    //2.定义 DataSet[Rating]
    val ratings: DataSet[Rating] = benv.fromElements(
      Rating("moon","youny1",3),Rating("sun","youny2",4),
      Rating("cat","youny3",1),Rating("dog","youny4",5),Rating("tiger","youny4",5))

    //3.定义DataSet[(String, String)]
    val movies: DataSet[(String, String)]  = benv.fromElements(
      ("moon","ok"),("dog","good"),
      ("cat","notbad"),("sun","nice"),("water","nice"))

    val result = ratings.fullOuterJoin(movies).where("name").equalTo(0){
      (r,m) => (if(r==null) -1 else r.name,if(m == null) -1 else m._2)
    }
    println(result.collect())

    //3.两个dataset进行全外连接，指定连接方法
    val result1 = movies.fullOuterJoin(ratings,JoinHint.REPARTITION_SORT_MERGE).where(0).equalTo("name"){
      (m, r) => (if(m == null) -1 else m._1, if (r == null) -1 else r.points)
    }

    //5.显示结果
    println(result1.collect)

    /**
      * 全外连接支持以下项目：
      *     JoinHint.OPTIMIZER_CHOOSES
      *     JoinHint.BROADCAST_HASH_FIRST
      *     JoinHint.REPARTITION_HASH_FIRST
      *     JoinHint.REPARTITION_SORT_MERGE
      * ---------------------
      */
  }

  /**
    * cross算子：
    * 交叉。拿第一个输入的每一个元素和第二个输入的每一个元素进行交叉操作。
    */
  def testCross(benv:ExecutionEnvironment): Unit ={
    //1.定义两个DataSet
    val coords1 = benv.fromElements((1,4,7),(2,5,8),(3,6,9))
    val coords2 = benv.fromElements((10,40,70),(20,50,80),(30,60,90))
    //2.交叉两个DataSet[Coord]
    val result1 = coords1.cross(coords2)
    //3.显示结果
    println(result1.collect)


    //1.定义 case class
    case class Coord(id: Int, x: Int, y: Int)
    //2.定义两个DataSet[Coord]
    val coords3: DataSet[Coord] = benv.fromElements(Coord(1,4,7),Coord(2,5,8),Coord(3,6,9))
    val coords4: DataSet[Coord] = benv.fromElements(Coord(10,40,70),Coord(20,50,80),Coord(30,60,90))
    //3.交叉两个DataSet[Coord]
    val result2 = coords3.cross(coords4)
    //4.显示结果
    println(result2.collect)

    /**
      * 自定义操作
      */
    //3.交叉两个DataSet[Coord]，使用自定义方法
    val r = coords3.cross(coords4) {
      (c1, c2) =>{
        val dist =(c1.x + c2.x) +(c1.y + c2.y)
        (c1.id, c2.id, dist)
      }
    }
    //4.显示结果
    println(r.collect)

    /**
      * 优化操作：
      * crossWithTiny:
      * 暗示第二个输入较小的交叉。
      * 拿第一个输入的每一个元素和第二个输入的每一个元素进行交叉操作。
      */
    //3.交叉两个DataSet[Coord]，暗示第二个输入较小
    val result3 = coords1.crossWithTiny(coords2)
    //4.显示结果
    println(result3.collect)

    /**
      * crossWithHuge:
      * 暗示第二个输入较大的交叉。
      * 拿第一个输入的每一个元素和第二个输入的每一个元素进行交叉操作。
      */
    //3.交叉两个DataSet[Coord]，暗示第二个输入较大
    val result4 = coords1.crossWithHuge(coords2)
    //4.显示结果
    println(result4.collect)
  }

  /**
    * union算子：
    * 合并多个DataSet。
    */
  def testUnion(benv:ExecutionEnvironment): Unit ={
    //1.定义 case class
    case class Student(val name: String, addr: String, salary: Double)

    //2.定义三个DataSet[Student]
    val tuples1 = benv.fromElements(
      Student("lisi-1","shandong",2400.00),Student("zhangsan-1","henan",2600.00))

    val tuples2 = benv.fromElements(
      Student("lisi-2","shandong",2400.00),Student("zhangsan-2","henan",2600.00))

    val tuples3 = benv.fromElements(
      Student("lisi-3","shandong",2400.00),Student("zhangsan-3","henan",2600.00))

    //3.将三个DataSet合并起来
    val unioned = tuples1.union(tuples2).union(tuples3)

    //4.显示结果
    println(unioned.collect)
  }

  /**
    * first算子：
    * 取前n个元素
    * @param benv
    */
  def testFirst(benv:ExecutionEnvironment): Unit ={
    //1.定义 case class
    case class Student(val name: String, addr: String, salary: Double)

    //2.定义DataSet[Student]
    val in: DataSet[Student] = benv.fromElements(
      Student("lisi","shandong",2400.00),Student("zhangsan","hainan",2600.00),
      Student("lisi","shandong",2400.00),Student("zhangsan","hainan",2600.00),
      Student("xiaoqi","guangdong",2400.00),Student("xiaoqi","henan",2600.00))

    //3.取前2个元素
    val out1 = in.first(2)
    println(out1.collect)

    //3.取每组前1个元素 ???
    val out2 = in.groupBy(0).first(1)
    println(out2.collect)

    //3.取每组排序前n个元素 ???
    import org.apache.flink.api.common.operators.Order
    val out3 = in.groupBy(0).sortGroup(1, Order.ASCENDING).first(3)
    println(out3.collect)
  }


}
