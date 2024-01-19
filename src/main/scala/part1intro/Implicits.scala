package part1intro

object Implicits {

  // implicit classes are one argment wrappers over values
  // implicit classes take ONLY A SINGLE ARGUMENT

  case class Person(name: String) {
    def greet(): String = s"Hi, name is $name"
  }

  implicit class ImpersonableString(name: String) {
    def greet(): String = Person(name).greet()
  }

  val greeting: String = "Peter".greet()

  // an implicit argument is used to PROVE THE EXISTENCE of a type
  trait JSONSerializer[T] {
    def toJson(value: T): String
  }

  // implicit methods
  // All case classes are subtypes of Product :-o
  // implicit methods are used to PROVE THE EXISTENCE of a type
  implicit def oneArgCaseClassSerialiser[T <: Product] = new JSONSerializer[T] {
    override def toJson(value: T): String = {
      s"""
        |{
        |  "${value.productElementName(0)}" : "${value.productElement(0)}"
        |}
        |""".stripMargin
    }
  }

  // Ordering of implicits scope:
  // 1. local scope
  // 2. the imported scope
  // 3. the companion objects of the types involved in the method call

  def main(args: Array[String]): Unit = {

  }

}
