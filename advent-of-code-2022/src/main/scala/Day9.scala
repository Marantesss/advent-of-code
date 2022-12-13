import Utils.Solver
import Utils.Problem
import Utils.Solution

/**
  * GridZippers: https://www.47deg.com/blog/game-of-life-scala/
  */
object Day9 extends Solver:

    def solve(p: Problem): Solution | (Solution, Solution) = 
        val fileContents: String = p.parseInputAsString()
        Solution(0)

end Day9
