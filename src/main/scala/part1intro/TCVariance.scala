package part1intro

object TCVariance {

  import cats.Eq._
  import cats.instances.int._
  import cats.instances.option._
  import cats.syntax.eq._

  val aComparison = Option(2) === Option(3) // note the triple equals for use of Eq
//  val anInvalidComparison = Some(2) === Some(3) // Eq[Some[Int]] not found even though Some is a subtype of Option

  // variance
  class Animal
  class Cat extends Animal

  // covariant type: subtyping is propagated to the generic type
  class Cage[+T]
  val cage: Cage[Animal] = new Cage[Cat]

  // contravariant type: subtyping is propagated BACKWARDS to the generic type
  class Vet[-T]
  val vet: Vet[Cat] = new Vet[Animal] // Cat <: Animal, then Vet[Animal] <: Vet[Cat]

  // general rule: "HAS a T" = covariant, "ACTS on T" = contravariant
  // variance affect how type class instances are being fetched

  // contravariant type class
  trait SoundMaker[-T]
  implicit object AnimalSoundMaker extends SoundMaker[Animal]
  def makeSound[T](implicit soundMaker: SoundMaker[T]) = println("wow")
  makeSound[Animal] // OK - type class instance defined above
  makeSound[Cat] // OK - type class instance for Animal is also applicable to Cat
  // rule 1: contraviant type classes can use superclass instances if nothing is available strictly for that type

  implicit object OptionSoundMaker extends SoundMaker[Option[Int]]
  makeSound[Option[Int]]
  makeSound[Some[Int]]

  // covariant type class
  trait AnimalShow[+T] {
    def show(): String
  }

  implicit object GeneralAnimalShow extends AnimalShow[Animal] {
    override def show(): String = "Animals everywhere!"
  }

  implicit object CatsAnimalShow extends AnimalShow[Cat] {
    override def show(): String = "Cats! Cats everywhere!"
  }

  def organizeShow[T](implicit myEvent: AnimalShow[T]): String = myEvent.show()
  // rule 2: covariant type classes will always use the more specific type class instance for that type
  // but may confuse the compiler if the general type class is also present

  // rule 3: can't have both
  // Cats use invariant type classes for this
  def main(args: Array[String]): Unit = {
    println(organizeShow[Cat])
//    println(organizeShow[Animal]) // will not compile because of rule 2
  }
}
