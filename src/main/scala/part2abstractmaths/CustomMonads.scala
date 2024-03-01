package part2abstractmaths

import scala.annotation.tailrec

object CustomMonads {

  import cats.Monad
  implicit object OptionMonad extends Monad[Option] {
    override def pure[A](x: A): Option[A] = Option(x)
    override def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] = fa.flatMap(f)
    // What is this, and why do we need it? Use for iteration methods
    @tailrec
    override def tailRecM[A, B](a: A)(f: A => Option[Either[A, B]]): Option[B] = f(a) match {
      case None => None
      case Some(Left(v)) => tailRecM(v)(f)
      case Some(Right(b)) => Some(b)
    }
  }

  // Add a custom monad of type Identity - doesn't do much, just an example implementation
  type Identity[T] = T
  implicit object IdentityMonad extends Monad[Identity] {
    override def pure[A](x: A): Identity[A] = x
    override def flatMap[A, B](a: Identity[A])(f: A => Identity[B]): Identity[B] = f(a)
    @tailrec
    override def tailRecM[A, B](a: A)(f: A => Identity[Either[A, B]]): Identity[B] = f(a) match {
        case Left(v) => tailRecM(v)(f)
        case Right(b) => b
      }
  }

  // harder example
  sealed trait Tree[+A]
  final case class Leaf[+A](value: A) extends Tree[A]
  final case class Branch[+A](left: Tree[A], right: Tree[A]) extends Tree[A]

  implicit object TreeMonad extends Monad[Tree] {
    override def pure[A](value: A): Tree[A] = Leaf(value)
    override def flatMap[A, B](tree: Tree[A])(func: A => Tree[B]): Tree[B] = tree match {
      case Leaf(value) => func(value)
      case Branch(left, right) => Branch(flatMap(left)(func), flatMap(right)(func))
    }
    override def tailRecM[A, B](value: A)(func: A => Tree[Either[A, B]]): Tree[B] = {
      def stackRec(tree: Tree[Either[A, B]]): Tree[B] = {
        tree match {
          case Leaf(Left(value)) => stackRec(func(value))
          case Leaf(Right(value)) => Leaf(value)
          case Branch(left, right) => Branch(stackRec(left), stackRec(right))
        }
      }
      stackRec(func(value))
    }
  }

  def main(args: Array[String]): Unit = {
    val tree: Tree[Int] = Branch(Leaf(10), Leaf(20))
    val changedTree = TreeMonad.flatMap(tree)(value => Branch(Leaf(value + 1), Leaf(value + 2)))
    println(changedTree)
  }
}
