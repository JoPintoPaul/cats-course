package part4moretypeclasses

object Folding {

  // TODO: Implement these in terms of foldLeft
  object ListExercises {
    def map[A, B](list: List[A])(f: A => B): List[B] = {
      list.foldRight(List.empty[B])((a, currentList) => f(a) :: currentList)
    }
  }


  def main(args: Array[String]): Unit = {

  }}
