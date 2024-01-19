package part1intro

object Essentials {

  // values
  val aBoolean: Boolean = false

  // expressions are EVALUATED to a value
  val anIfExpression = if (2 > 3) "bigger" else "smaller"

  // units vs instructions
  val theUnit = println("Hello, Scala!")

  // partial functions
  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 43
    case 8 => 56
    case 100 => 999
  }

  // advanced Scala
  trait HigherKindedType[F[_]]
  trait SequenceChecker[F[_]] {
    def isSequential(): Boolean
  }

  val listChecker = new SequenceChecker[List] {
    override def isSequential(): Boolean = true
  }

  def main(args: Array[String]): Unit = {

  }
}
