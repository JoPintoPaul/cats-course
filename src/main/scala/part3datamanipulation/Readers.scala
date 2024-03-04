package part3datamanipulation

import cats.Id

object Readers {

  /*
   - configuration file => initial data structure
   - a DB later
   - an HTTP later
   - a business logic layer
   */
  case class Configuration(dbUsername: String, dbPassword: String, host: String, port: Int, numThreads: Int)
  case class DbConnection(username: String, password: String) {
    def getOrderStatus(order: Long): String = "dispatched" // select * from from db table and return the status of the orderId
  }
  case class HttpService(host: String, port: Int) {
    def start(): Unit = println("server started") // this would start the server
  }

  // bootstrap
  val myConfig = Configuration("joanna", "rockthejvm1!", "localhost", 4040, 8)
  // cats Reader
  import cats.data.Reader
  // reader to derive an instance of DbConnection from an instance of Configuration
  val dbReader: Reader[Configuration, DbConnection] = Reader(conf => DbConnection(conf.dbUsername, conf.dbPassword))
//  val aDbConnection = dbReader.run(myConfig)

  // Reader[I, O]
  val joannasOrderStatusReader = dbReader.map(conn => conn.getOrderStatus(123))
  val joannasOrderStatus: Id[String] = joannasOrderStatusReader.run(myConfig)

  /*
  Pattern
  1. you create the initial data structure
  2. you create a reader which specifies how that data structure will be manipulated later
  3. you can then map & flatMap the reader to produce derived information
  4. when you need the final piece of information, you call run on the reader with the initial data structure
 */

  def main(args: Array[String]): Unit = {
  }
}
