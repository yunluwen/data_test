package com.pagerank.utils

import com.session.constants.Constants

/**
  * 文件路径工具类
  */
object PathUtils {

  def getFilePath(fileName:String): String ={
//    val fileURL = PathUtils.getClass.getClassLoader.getResource(fileName)
    val filePath = System.getProperty("user.dir") + "/data/" + fileName
    filePath
  }

  def main(args: Array[String]): Unit = {
    println(getFilePath("user_info.txt"))
  }
}
