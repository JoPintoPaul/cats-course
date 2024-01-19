package part1intro

object TypeClasses {

  case class Person(name: String, age: Int)

  // part 1: type class definition
  trait JSONSerializer[T] {
    def toJson(value: T): String
  }

  // part 2: create implicit type class instances
  implicit object StringSerializer extends JSONSerializer[String] {
    override def toJson(value: String): String =
      s"""
        |"$value"
        |""".stripMargin.trim
  }

  implicit object IntSerializer extends JSONSerializer[Int] {
    override def toJson(value: Int): String =
      s"""
         |"${value.toString}"
         |""".stripMargin.trim
  }

  implicit object PersonSerializer extends JSONSerializer[Person] {
    override def toJson(value: Person): String =
      s"""
        |{ "name": "${value.name}", "age": "${value.age}" }
        |""".stripMargin.trim
  }

  // part 3: offer an API to serialize things to JSON
  def convertListToJson[T](list: List[T])(implicit serializer: JSONSerializer[T]): String = {
    list.map(value => serializer.toJson(value)).mkString("[", ",", "]")
  }

  // part 4: extending the existing types via extension methods
  object JSONSyntax {
    implicit class JSONSerializable[T](value: T)(implicit serializer: JSONSerializer[T]) {
      def toJson(): String = serializer.toJson(value)
    }
  }

  def main(args: Array[String]): Unit = {
    println(convertListToJson(List(Person("Alice", 23), Person("Xavier", 45))))

    val bob = Person("Bob", 35)
    import JSONSyntax._
    println(bob.toJson())
  }
}
