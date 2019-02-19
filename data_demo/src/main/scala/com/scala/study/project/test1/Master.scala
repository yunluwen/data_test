package com.scala.study.project.test1

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

//todo:利用akka的actor模型实现2个进程间的通信-----Master端
class Master extends Actor{

  //构造代码块先被执行
  println("master constructor invoked")

  //prestart方法会在构造代码块执行后被调用，并且只被调用一次
  override def preStart(): Unit = {
    println("preStart method invoked")
  }

  //receive方法会在prestart方法执行后被调用，表示不断的接受消息
  override def receive: Receive = {
    case "connect" =>{
      println("a client connected")

      //master发送注册成功信息给worker
      sender ! "success"
    }
  }
}


object Master {

  def main(args: Array[String]): Unit = {
    //master的ip地址
    val host = "localhost"
    //master的port端口
    val port= 9999

    //准备配置文件信息
    val configStr=
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
      """.stripMargin

    //配置config对象 利用ConfigFactory解析配置文件，获取配置信息
    val config=ConfigFactory.parseString(configStr)

    // 1、创建ActorSystem,它是整个进程中老大，它负责创建和监督actor，它是单例对象
    val masterActorSystem = ActorSystem("masterActorSystem",config)
    // 2、通过ActorSystem来创建master actor
    val masterActor: ActorRef = masterActorSystem.actorOf(Props(new Master),
      "masterActor")
    // 3、向master actor发送消息
    //masterActor ! "connect"
  }
}
