import java.util.concurrent.Executors

import util.NamedThreadFactory

import scala.concurrent.{ExecutionContext, Future}

object FutureExecutionContextTraining extends App{

  val threadPoolExecutor  = Executors.newFixedThreadPool(10, new NamedThreadFactory("training"))
  implicit val executionContext: ExecutionContext = ExecutionContext.fromExecutorService(threadPoolExecutor)
  val future = Future { println(s"My thread is ${Thread.currentThread().getName()}") ; "Done" }

  future
    .map(println)
    .andThen{
    case _ => threadPoolExecutor.shutdown()
  }

}
