import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object FutureFailureTraining extends App{
  def test1 = {
    val future = Future[String] { Thread.sleep(10) ; throw new Exception("No future") }
    future.map(str => println(str))
  }

  def test2 = {
    val future = Future[String] { Thread.sleep(10) ; throw new Exception("No future") }
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
      str => println(str) }
  }

  def test4 = {
    val future1 = Future[String] { Thread.sleep(10) ; throw new IllegalArgumentException("Recovered from this") }
    val future2 = Future { "Fall back to safe place" }
    future1
      .fallbackTo(future2)
      .map(println)
  }

  test4

  scala.io.StdIn.readLine()
}
