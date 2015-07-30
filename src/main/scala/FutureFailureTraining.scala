import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}

object FutureFailureTraining extends App{

  def test1 = {
    val future = Future[String] { Thread.sleep(10) ; throw new Exception("No future at all") }
    future.map(str => println(str))
  }

  def test2 = {
    val future = Future[String] { Thread.sleep(10) ; throw new Exception("No future at all") }
    future
      .map {
        str => println(str) }
      .recover {
        case e => println(e)
    }
  }

  def test3 = {
    val future = Future[String] { Thread.sleep(10) ; throw new IllegalArgumentException("Recovered from this") }
    future.recover {
        case e:IllegalArgumentException => e.getMessage
    }.map {
      str => println(str)
    }
  }

  def test4 = {
    val theFuture1 = Future[String] { Thread.sleep(10) ; throw new IllegalArgumentException("Recovered from this") }
    val theFuture2 = Future { "Fall back to safe place" }
    theFuture1
      .fallbackTo(theFuture2)
      .map(println)
  }

  def test5 = {
    def log(str: String) = println (str)
    val future = Future[String] { Thread.sleep(10) ; /*throw new Exception("No future at all")*/ "Done" }

    future
      .andThen {
        case Success(s) => log (s"log::Success $s")
        case Failure(e) => log (s"log::Failure $e")
      }.map(println)
  }

  test1

  scala.io.StdIn.readLine()
}
