package Utils

import scala.io.Source

/**
  * 
  *
  * @param value solution
  */
final case class Solution(
    value: Any,
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
    def parseInputAsString(trim: Boolean = true) =
        val contentString = Source.fromFile(s"src/main/resources/$filename").mkString
        if trim then contentString.trim else contentString

trait Solver:
    def solve(p: Problem): Solution | (Solution, Solution)
