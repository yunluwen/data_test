package com.scala.study.http

//import com.mashape.unirest.http.Unirest
//
//class HttpUtils(url:String) {
//
//  def doGet(): Unit ={
//    val jsonResponse = Unirest.get(url).asJson()
//    val jsonNode = jsonResponse.getBody
//    val obj = jsonNode.getObject
//    println(obj)
//  }
//}
//
//object HttpUtils{
//  def main(args: Array[String]): Unit = {
//    val httpUtils = new HttpUtils("http://10.33.15.106:6666/metrics")
//    httpUtils.doGet()
//  }
//}