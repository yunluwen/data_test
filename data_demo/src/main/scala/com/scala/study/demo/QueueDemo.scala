package com.scala.study.demo

/**
  */
object QueueDemo {
  def main(args: Array[String]): Unit = {

  }
}

/**
  * 私有构造器和工厂方法
  * 第一种方式
  * @param loading
  * @param trailing
  * @tparam T

class Queue[+T] private (private[this] var loading: List[T],
                private[this] var trailing: List[T]){

  private def mirror() =
    if (loading.isEmpty){
      while(!trailing.isEmpty){
        loading = trailing.head :: loading
        trailing = trailing.tail
      }
    }


  def head:T = {
    mirror()
    loading.head
  }

  def tail:Queue[T] = {
    mirror()
    new Queue(loading.tail,trailing)
  }

  def append[U >: T](x:U)=
    new Queue(loading, x:: trailing)

}

object Queue{
  def apply[T](xs: T*) = new Queue[T](xs.toList,Nil)
}

*/
/**
  * 第二种方式
  * @tparam T
  */
trait Queue2[T] {
  def head: T
  def tail: Queue2[T]
  def append(x: T): Queue2[T]

}

object Queue2 {
//  def apply[T](xs: T*) = new Queue[T](xs.toList,Nil)

  private class QueueImpl[T](private val loading: List[T],
                             private val trailing: List[T]) extends Queue2[T]{
    def mirror =
      if (loading.isEmpty)
        new QueueImpl(trailing.reverse,Nil)
      else
        this

    def head: T = mirror.loading.head

    def tail: QueueImpl[T] = {
      val q = mirror
      new QueueImpl(q.loading.tail,q.trailing)
    }

    def append(x: T): Queue2[T] =
      new QueueImpl(loading,x:: trailing)

  }
}
