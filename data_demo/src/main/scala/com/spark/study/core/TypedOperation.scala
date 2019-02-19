package com.spark.study.core

import org.apache.spark.sql.SparkSession

import scala.collection.mutable.ArrayBuffer

/**
  * typed操作，dataframe不能执行，需要dataframe转换为dataset
  */
object TypedOperation {

  case class Employee(name:String,age:Long,depId:Long,gender:String,salary:Double)
  case class Department(id:Long,name:String)

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("typedOperation")
      .master("local")
      .enableHiveSupport()      //添加hive支持
      .config("spark.some.config.option", "config-value")  //必须设置spark sql的元数据仓库的地址
      .getOrCreate()

    import spark.implicits._  //导入spark的隐式转换
    import org.apache.spark.sql.functions._    //导入spark sql的functions

    //dataframe是untype类型的 ，dataset是type类型
    //文件加载进来形成两个dataframe
    val employee = spark.read.json("/test_data/employee.json")
    //将dataframe转换为dataset
    val employeeDS = employee.as[Employee]

    /**
      * coalesce 和 repartition 都是用来重新定义分区的
      * 区别在于：coalesce只能用来减少分区数量，而且可以选择不发生shuffle
      * repartition可以增加分区，也可以减少分区，必须会发生shuffle,相当于进行了一次重分区操作
      */
    val employeeDSRepartition = employeeDS.repartition(7)
    println(employeeDSRepartition.rdd.partitions.size)  //分区的个数
    val employeeDSCoalesce = employeeDSRepartition.coalesce(3)
    println(employeeDSCoalesce.rdd.partitions.size)

    /**
      * distinct去重操作,根据完整的内容进行比对和去重
      * dropDuplicates，根据指定的字段进行去重
      * 都是会进行shuffle的
      */
    employeeDS.distinct().show()
    employeeDS.dropDuplicates(Seq("name"))

    /**
      * except:获取在当前dataset中有，但是在另一个dataset中没有的元素
      * filter:根据我们自己的逻辑，如果返回true就保留该元素，否则过滤掉
      * intersect:获取两个数据的交集
      */
    val employee2 = spark.read.json("/test_data/department.json")
    //将dataframe转换为dataset
    val employeeDS2 = employee.as[Employee]
    employeeDS.except(employeeDS2).show()

    employeeDS.filter(employee => employee.age>30 ).show()

    employeeDS.intersect(employeeDS2).show()

    /**
      * map：将数据中的每条数据都做一个映射，返回一条新数据
      * flatmap:数据集中的每条数据都可以返回多条数据
      * mapPartitions:一次性对一个partition中的数据进行处理
      */
//    employeeDS.map(employee => (employee,1))
//    employeeDS.flatMap(employee => ArrayBuffer(employee.name,employee.age))
//    employeeDS.mapPartitions(employees => {
//      val result = ArrayBuffer[(String,Double)]()
//      while(employees.hasNext) {
//        val emp = employees.next()
//        result.append((emp.name,emp.salary+1000))
//      }
//      result.iterator
//    }).show()

    /**
      * joinWith
      */
    val department = spark.read.json("")
    val departmentDS = department.as[Department]

    employeeDS.joinWith(departmentDS,$"deptId"===$"deptId").show()

    /**
      * sort排序
      * desc:降序
      * asc:升序
      */
    employeeDS.sort($"salary".desc).show()

    /**
      * randomSplit
      * sample:随机按比例抽取数据
      */
    val employeeDsArr = employeeDS.randomSplit(Array(3,10,20))
    employeeDsArr.foreach{ println(_) }
    //抽取百分之三十的比例的数据
    val employeeDSSample = employeeDS.sample(false,0.3).show()

    spark.stop()
  }

}
