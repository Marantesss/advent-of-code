import Utils.Solver
import Utils.Problem
import Utils.Solution

object Day6 extends Solver:

    type Signal = (Char, Char, Char, Char)

    def isSignal(signal: Signal): Boolean = signal.toArray.distinct.length == 0

    def getStartOfPacket(stream: Seq[Char]): Int = stream match
        case x if x.length < 4 => throw Error("Start of packet not found")
        case x if isSignal((x(0), x(1), x(2), x(3))) => 1
        case _ => 1 + getStartOfPacket(stream.drop(1))

    def solve(p: Problem): Solution | (Solution, Solution) =
        val fileContents: String = p.parseInputAsString()

        Solution(getStartOfPacket(fileContents.toCharArray))

end Day6
