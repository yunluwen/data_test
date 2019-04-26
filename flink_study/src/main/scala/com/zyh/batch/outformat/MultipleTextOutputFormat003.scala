package com.zyh.batch.outformat

import org.apache.hadoop.mapred.lib.MultipleTextOutputFormat

/**
  * 使用DataSet的key做为文件名称，文件内容以键值对的形式存在，将DataSet输出到多个文件中。
  */
class MultipleTextOutputFormat003[K, V] extends MultipleTextOutputFormat[K, V] {
  /**
    * 此方法用于产生文件名称,这里将name_key直接作为文件名称
    *
    * @param key   DataSet的key
    * @param value DataSet的value
    * @param name  DataSet的partition的id(从1开始)
    * @return file的name
    */
  override def generateFileNameForKeyValue(key: K, value: V, name: String): String =
    ( name + "_" +key).asInstanceOf[String]

  /**
    * 此方法用于产生文件内容中的key，这里文件内容中的key是就是DataSet的key
    *
    * @param key   DataSet的key
    * @param value DataSet的value
    * @return file的key
    */
  override def generateActualKey(key: K, value: V): K = key.asInstanceOf[K]

  /**
    * 此方法用于产生文件内容中的value，这里文件内容中的value是就是DataSet的value
    *
    * @param key   DataSet的key
    * @param value DataSet的value
    * @return file的value
    */
  override def generateActualValue(key: K, value: V): V = value.asInstanceOf[V]
}