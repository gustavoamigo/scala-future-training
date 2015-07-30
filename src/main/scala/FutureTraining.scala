import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.async.Async._


object FutureTraining extends App {


  def test1 = {
    val future = Future { Thread.sleep(10) ; "This is the future" }

    future.map{
      str => println(str)
    }

    println("This is the past")
  }

  def test2 = {
    val future1 = Future  { Thread.sleep(5) ; "This is 2016" }
    val future2 = Future  { Thread.sleep(5) ; "This is 2017" }

    future1.flatMap { str =>
      println(str)
      future2
    }.map { str =>
      println(str)
    }

  }

  def test3 = {
    val future1 = Future  { Thread.sleep(5) ; "This is 2016" }
    val future2 = Future  { Thread.sleep(5) ; "This is 2017" }

    for {
      str1 <- future1
        _   = println(str1)
      str2 <- future2
        _   = println(str2)
    } yield()

  }

  def test4 = {
    async {
      val future1 = Future  { Thread.sleep(5) ; "This is 2016" }
      val future2 = Future  { Thread.sleep(5) ; "This is 2017" }
      val str1 = await { future1 }
      println(str1)
      val str2 = await { future2 }
      println(str2)
    }

  }

  def test5 = {
    val future1 = Future  { Thread.sleep(5) ; "This is 2015" }
    val future2 = Future  { Thread.sleep(5) ; "This is 2016" }

    // Nasty ugly example, pandas dies whenever you do this
    future1.onSuccess{ case str1 =>
      println(str1)
      future2.onSuccess{ case str2 =>
        println(str2)
      }
    }
  }

  test1

  scala.io.StdIn.readLine()
}
