package part1intro

object CatsIntro {

  // this will compile, with a warning, but will always be false
  val aComparison = 2 == "three"

  // Eq: used to enforce type safe equality
  // part 1: type class import
  import cats.Eq

  // part 2: import type class instances for the types you need
  import cats.instances.int._

  // part 3: use the type classes API
  val intEquality = Eq[Int]
  val aTypeSqafeComparison = intEquality.eqv(2, 3)
  // val unUnsafeComparison = intEquality(2, "three") // will not compile

  // part 4 - use extension methods (if applicable)
  import cats.syntax.eq._
  val anotherTypeSafeComparison = 2 === 3 // note TRIPLE equals three
  val newComparison = 2 =!= 3
//  val invalidComparison = 2 === "three" // will not compile

  // part 5: extending type class operations to composite types, e.g. lists
  import cats.instances.list._ // to bring Eq[List[Int]] into scope
  val aListComparison = List(2) === List(3) // retruns false

  // part 6 - create a type class instance for custom type
  case class ToyCar(model: String, price: Double)
  implicit val toyCarEq: Eq[ToyCar] = Eq.instance[ToyCar] { (car1, car2) =>
    car1.price == car2.price
  }

  val compareTwoToyCars = ToyCar("Ferrari", 29.99) === ToyCar("VW Beetle", 29.99)

  /* Cats Organization
     Most important functionalities are type classes

     import cats.YourTypeClass
     import cats.instances.yourType._
     import cats.syntax.yourTypeClass._

     If you're struggling, you can import the world with:
     import cats._
     import cats.implicits._
   */
}
