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

  // Define our own
  trait Tree[+T]
  object Tree {
    // step 2: Using "smart" constructors
    def leaf[T](value: T): Tree[T] = Leaf(value)
    def branch[T](value: T, left: Tree[T], right: Tree[T]): Tree[T] = Branch(value, left, right)
  }
  case class Leaf[+T](value: T) extends Tree[T]
  case class Branch[+T](value: T, left: Tree[T], right: Tree[T]) extends Tree[T]

  implicit object TreeFunctor extends Functor[Tree] {

    override def map[A, B](tree: Tree[A])(func: A => B): Tree[B] = {
      tree match {
        case Leaf(value) => Leaf(func(value))
        case Branch(value, left, right) => Branch(func(value), map(left)(func), map(right)(func))
      }
    }
  }

  // extension method - map
  import cats.syntax.functor._
  val tree: Tree[Int] = Tree.branch(40, Tree.branch(5, Tree.leaf(10), Tree.leaf(30)), Tree.leaf(20))
  val incrementedTree = tree.map(_ + 1)

  // Note that `F[_]: Functor` means that it is CONTEXT BOUND
  def do10xShorter[F[_]: Functor](container: F[Int]): F[Int] = container.map(_ * 10)

  def main(args: Array[String]): Unit = {
    println(do10xInts(List(1, 2, 3)))
    println(do10xInts(Try(4)))
    println(do10xInts(Option(5)))
//    println(do10xInts[Tree](Branch(15, Leaf(25), Leaf(35))))

    println(do10xInts(Tree.branch(15, Tree.leaf(25), Tree.leaf(35))))
  }
}
