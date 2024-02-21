package part2abstractmaths

import scala.util.Try

// HIGHER-KINDED TYPES: Types that themselves take types

object Functors {

  // Functors are a pattern that generalise a `map` function

  // Higher-kinded type class
  trait MyFunctor[F[_]] {
    def map[A, B](initialValue: F[A])(aIntoB: A => B): F[B]
  }

  import cats.Functor
  import cats.instances.list._ // includes Functor[List]
  val listFunctor = Functor[List]
  val incrementedNumbers = listFunctor.map(List(1, 2, 3))(_ + 1)

  import cats.instances.option._ // includes Functor[Option]
  val optionFunctor = Functor[Option]
  val incrementedOption = optionFunctor.map(Option(2))(_ + 1)

  import cats.instances.try_._

  // generalising an API
  def do10XList(list: List[Int]): List[Int] = list.map(_ * 10)
  def do10XOption(option: Option[Int]): Option[Int] = option.map(_ * 10)
  def to10xTry(attempt: Try[Int]): Try[Int] = attempt.map(_ * 10)

  def do10xInts[F[_]](container: F[Int])(implicit functor: Functor[F]): F[Int] = functor.map(container)(_ * 10)

  def main(args: Array[String]): Unit = {
    println(do10xInts(List(1, 2, 3)))
    println(do10xInts(Try(4)))
    println(do10xInts(Option(5)))
  }
}
