package com.pagerank.test

import java.io.FileInputStream
import java.net.URL

import com.session.constants.Constants
import com.pagerank.conf.ConfManager

object IOTest {

  def main(args: Array[String]): Unit = {
    val file_name = "jdbc.properties"
    val file_path : URL = IOTest.getClass.getClassLoader.getResource(file_name)
//    val inputStream = new FileInputStream(file_path.toString)
    println(System.getProperty("user.dir")+"/src/main/resources")
    val path = System.getProperty("user.dir")+"/src/main/resources/"+file_name
    val inputStream = new FileInputStream(path)
    //获取配置信息
    println(ConfManager.apply(inputStream).getBoolean(Constants.SPARK_LOCAL))
    //出现问题，工具类bug
    println(ConfManager.apply(inputStream).getString(Constants.JDBC_DRIVER))
    println(ConfManager.apply(inputStream).getString(Constants.JDBC_URL))

    println(Some("lalala","hahahha")+"\t"+Option(("lalala")))
  }

}
