package com.pagerank.test

import com.pagerank.utils.JDBCManager

/**
  * jdbc
  */
object JdbcManagerTest {

  def main(args: Array[String]): Unit = {
    val params: Array[AnyRef] = Array()
    val result = JDBCManager.jdbcPool().executeQuery("desc springboot.user",params)
    val metaData = result.getMetaData
    val colsLen = metaData.getColumnCount
    println(colsLen)
    while(result.next()){
      for(i <- 1 until colsLen+1){
        println(metaData.getColumnName(i))
        //println(result.getString(metaData.getColumnName(i)))
//        println(result.getString(i))
      }
//      println(result.getString(1))   //列名
//      println(result.getString(2))   //判断是否是无符号int型
//      println(result.getString(3))   //
//      println(result.getString(4))   //是否主键
//      println(result.getString(5))   //默认值
//      println(result.getString(6))   //自增等其他相应规则
      println("-------------")
//      val colsName = metaData.getColumnName(1)
//      println(colsName)
//      println(result.getObject(colsName))
    }
  }

}
