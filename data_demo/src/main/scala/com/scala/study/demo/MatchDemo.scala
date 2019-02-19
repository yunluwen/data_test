package com.scala.study.demo

/**
  * match case
  *
  * case class的一些性质
  *
  * 1.定义为case class 的类在实例化时，可以不使用new 关键字。
  *
  * case class People（name:String, age:Int）
  * val p = People("zs", 30)
  * 2. 定义为case class 的类 默认实现了 equals 和hashcode 方法
  *
  * 3. 默认是可序列化的
  *
  * 4. 其构造函数的参数 是public 访问的。如  p.name
  *
  * 5.支持模式匹配
  */
object MatchDemo {

  //case class 是一个模式类，而case object 是全局的一个唯一的实例
  //模式类之间不能互相继承，必须统一继承一个抽象类或者trait
  //模式类之间不能互相继承，必须统一继承一个抽象类或者trait
  class DataFrameWork
  //模式类，与普通类没什么区别，只是一定要有参数!!!
  case class ComputationFramework(name:String,popular:Boolean)extends DataFrameWork
  case class StorageFramework(name:String,popular:Boolean)extends DataFrameWork

  def main(args: Array[String]): Unit = {
    getSalary("spark",89)
    getMatchType("1")
    getMatchType(1)
    getMatchCollection(Array("a"))

    getBigDataType(ComputationFramework("Spark",true))

    val _map = Map("Spark"->"hot","hadoop"->"half_hot")
    getValue("Spark",_map)

  }

  def getSalary(name:String,age:Int){
    name match{
      //从前往后匹配
      case "Spark"=>println("$150000/year")
      case "Hadoop"=>println("$100000/year")
      //加入判断条件(用变量接受参数)
      case _name if age>=5 =>println(name+":"+age+" $140000/year")
      case _  =>println("$90000/year")            //都不匹配时
    }
  }

  //对类型进行匹配
  def getMatchType(msg:Any){
    msg match{
      case i : Int=>println("Integer")
      case s : String=>println("String")
      case d : Double=>println("Double")
      case _=>println("Unknow type")
    }
  }

  //对数组的匹配
  def getMatchCollection(msg:Array[String]){
    msg match{

      case Array("Scala")=>println("one element")
      case Array("Scala","Java")=>println("two element")
      //以Scala开头的数组(可有多个成员)
      case Array("Scala",_*)=>println("many element begin from scala")
      case _=>println("Unknow type")
    }
  }

  //对类的匹配，可以运用在简单工厂模式中
  def getBigDataType(data:DataFrameWork){  //父类
    data match{
      case ComputationFramework(name,popular)=>  //子类
        println("computationFramework  "+"name:"+name+" popular:"+popular)
      case StorageFramework(name,popular)=>
        println("StorageFramework  "+"name:"+name+" popular:"+popular)
      case _ =>println("Some other type")

    }
  }

  /**
    * 对map的匹配
    * 特别注意some和none的使用
    */
  def getValue(key:String,content:Map[String,String]){
    content.get(key) match{
      case Some(value)=>println(value)
      case None=>println("No found!!")
    }
  }

}
