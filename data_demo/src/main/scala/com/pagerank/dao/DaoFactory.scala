package com.pagerank.dao

import com.pagerank.utils.JDBCManager

/**
  * 数据库操作简单工厂
  */
abstract class DaoFactory {
  /**
    * 执行数据插入
    * @param params
    */
  def insert(params: Array[AnyRef] = Array()):Unit
  def findAll():Unit
  def findClickCountByMultiKey(date: String, userid: Long, adid: Long): Int
}

/**
  * pagerank job Dao
  */
class PageRank extends DaoFactory {

  override def insert(params: Array[AnyRef] = Array()):Unit = {
    var sql = "insert into spark_pro.page_rank (task_id,page_rank)values()"   //sql语句可以添加到配置文件中，防止数据库表变更对代码造成影响
    JDBCManager.jdbcPool().executeUpdate(sql)
  }

  override def findAll(): Unit = {}
  override def findClickCountByMultiKey(date: String, userid: Long, adid: Long): Int ={ 1 }
}

/**
  * adClick job Dao
  */
class AdClick extends DaoFactory {
  override def insert(params: Array[AnyRef]): Unit = {}

  /**
    * 未实现的方法
    */
  override def findAll(): Unit = {

  }

  override def findClickCountByMultiKey(date: String, userid: Long, adid: Long): Int ={ 1 }
}

object DaoFactory {
  def apply(style:String) = style match {
    case "PageRank" => new PageRank
    case "AdClick" => new AdClick
  }

}
