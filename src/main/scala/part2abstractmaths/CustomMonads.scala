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


  def main(args: Array[String]): Unit = {
  }
}
