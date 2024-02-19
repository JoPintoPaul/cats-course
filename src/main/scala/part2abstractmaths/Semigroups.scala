package part2abstractmaths

object Semigroups {

  //Semigroups COMBINE elements of the same type
  import cats.Semigroup
  import cats.instances.int._
  import cats.instances.string._

  val naturalIntSemigroup = Semigroup[Int]
  val intCombination = naturalIntSemigroup.combine(2, 46) // addition

  val naturalStringCombination = Semigroup[String]
  val stringCombination = naturalStringCombination.combine("I love ", "Cats") // concatenation

  // specific API
  def reduceInts(list: List[Int]): Int = list.reduce(naturalIntSemigroup.combine)
  def reduceStrings(list: List[String]): String = list.reduce(naturalStringCombination.combine)

  // general API
  def reduceThings[T](list: List[T])
                     (implicit semigroup: Semigroup[T]): T = list.reduce(semigroup.combine)

  case class Expense(id: Long, amount: Double)
  implicit val semigroupExpense: Semigroup[Expense] = Semigroup.instance[Expense] { (exp1, exp2) =>
    Expense(if (exp1.id > exp2.id) exp1.id else exp2.id, exp1.amount + exp2.amount) }

  def main(args: Array[String]): Unit = {
    println(intCombination) // will print 48
    println(stringCombination) // will print "I love Cats"

    val numbers = (1 to 10).toList
    val strings = List("I ", "joying ", "semigroups")

    // specific API usage
    println(reduceInts(numbers))
    println(reduceStrings(strings))

    // general API usage
    println(reduceThings(numbers))
    println(reduceThings(strings))

    import cats.instances.option._
    val optNumbers: List[Option[Int]] = numbers.map(Option(_))

    // needs both Semigroup[Int] and Semigroup[Option]
    // will only return Some if both combined elements are Some()
    println(reduceThings(optNumbers))
  }
}

