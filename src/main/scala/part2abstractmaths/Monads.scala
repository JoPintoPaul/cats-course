package part2abstractmaths

// Monads are a higher-kinded type class that provdes
// 1. a `pure` method to wrap a normal value into a monadic value
// 2. a `flatMap` method to transform monadic values in sequence

object Monads {
  // consist of 2 methods:
  // The method wrapping into a monadic (M) value
  // and the `flatMap` method to transform M values into other M values

  trait MyMonad[M[_]] {
    def pure[A](value: A): M[A]
    def flatMap[A, B](ma: M[A])(f: A => M[B]): M[B]

    // this is an exercise, not core
    // this shows that Monads extends Functors
    def map[A, B](ma: M[A])(f: A => B): M[B] =
      flatMap(ma)(a => pure(f(a)))
  }

  // Cats Monad
  import cats.Monad
  import cats.instances.option._

  val optionMonad = Monad[Option]
  val anOption = optionMonad.pure(4)
  val aTransformedOption = optionMonad.flatMap(anOption)(x => if (x % 3 == 0) None else Some(x + 1))

  // specialized API
  def getPairsList(ints: List[Int], chars: List[Char]): List[(Int, Char)] =
    ints.flatMap(i => chars.map(c => (i, c)))

  // if specialized, need to do do the same for other monads
  def getOptionList(int: Option[Int], char: Option[Char]): Option[(Int, Char)] =
    int.flatMap(i => char.map(c => (i, c)))

  // generalized API
  def getPairs[M[_], A, B](ma: M[A], mb: M[B])(implicit monad: Monad[M]): M[(A, B)] =
    monad.flatMap(ma)(a => monad.map(mb)(b => (a, b)))

  // extension methods - weirder imports
  import cats.syntax.applicative._ // pure is here
  val oneOption: Option[Int] = 1.pure[Option]

  import cats.syntax.flatMap._ // flatMap is here
  val oneOptionTransformed: Option[Int] = oneOption.flatMap(x => (x + 1).pure[Option])

  // things that we can do because Monads extend Functors
  import cats.syntax.functor._
  val oneOptionMapped = oneOption.map(x => x + 1)
  val composedOption = for {
    one <- 1.pure[Option]
    two <- 2.pure[Option]
  } yield one + two

  def getPairsForComprehension[M[_], A, B](ma: M[A], mb: M[B])(implicit monad: Monad[M]): M[(A, B)] =
    for {
      a <- ma
      b <- mb
    } yield (a, b)

  def main(args: Array[String]): Unit = {
  }
}
