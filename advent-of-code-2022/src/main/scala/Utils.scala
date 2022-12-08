package Utils

import scala.io.Source

/**
  * 
  *
  * @param value solution
  */
final case class Solution(
    value: Int,
):
    override def toString(): String = s"Solution: $value"

/**
  * 
  *
  * @param filename the name of the file inside <code>src/main/resources/</code>
  */
final case class Problem(
    filename: String,
):
    def parseInputAsString() =
        Source
            .fromFile(s"src/main/resources/$filename")
            .mkString
            .trim

trait Solver:
    def solve(p: Problem): Solution | (Solution, Solution)

