package part4moretypeclasses

object Semigroupals {

  // A higher kinded type class which can tuple elements
  // This is done with the `product` method
  // Monads extend Semigroupal, with `product` implemented in terms of map/flatMap

  trait MySemigroupal[F[_]] {
    def product[A, B](fa: F[A], fb: F[B]): F[(A, B)]
  }

  import cats.Semigroupal
  import cats.instances.option._

  val optionSemigroupal = Semigroupal[Option]
  val aTupleOption = optionSemigroupal.product(Some(123), Some("a string")) // this will some Some(123, "a string")

  // Monads extend Semigroupals

  // example: Validated
  import cats.data.Validated
  import cats.instances.list._
  type ErrorOr[T] = Validated[List[String], T]
  val validatedSemigroupal = Semigroupal[ErrorOr]
  val invalidCombination = validatedSemigroupal.product(
    Validated.invalid(List("I've got a bad feeling about this", "frozen in carbonite")),
    Validated.invalid(List("it's a trap"))
  )

  // Todo 2: define a Semigroupal[List] which zips
  val zipListSemigroupal = Semigroupal[List] = new Semigroupal[List] {
    override def product[A, B](fa: List[A], fb: List[B]): List[(A, B)] = fa.zip(fb)
  }


  def main(args: Array[String]): Unit = {
    println(invalidCombination)
  }
}
