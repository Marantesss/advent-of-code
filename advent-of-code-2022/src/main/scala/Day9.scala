import Utils.Solver
import Utils.Problem
import Utils.Solution
import Utils.Position

object Day9 extends Solver:

    case class Move(offset: Position, times: Int)

    def getTailPosition(head: Position, tail: Position): Position =
        val Position(x, y) = head - tail
        if x.abs <= 1 && y.abs <= 1 then tail else tail + Position(x.sign, y.sign)

    def getTailPositionsAfterMove(head: Position, tail: Position, move: Move): Seq[Position] =
        Seq.range(1, move.times + 1).foldLeft(Seq[Position]())((acc, iter) => {
            val currentTail = if acc.isEmpty then tail else acc.last
            acc :+ getTailPosition(head + move.offset * iter, currentTail)
        })

    def getHeadPosition(head: Position, moves: Seq[Move]): Position = moves.foldLeft(head)((acc, move) => acc + move.offset * move.times)

    def getTailPositionsAfterMoves(head: Position, tail: Position, moves: Seq[Move]) =
        Seq.range(0, moves.length).foldLeft(Seq[Position]())((acc, iter) => {
            val currentMove = moves(iter)
            val currentHead = getHeadPosition(head, moves.take(iter))
            val currentTail = if acc.isEmpty then tail else acc.last
            acc ++ getTailPositionsAfterMove(currentHead, currentTail, currentMove)
        }).distinct

    def parseDirection(direction: String): Position = direction match
        case "R" => Position(1, 0)
        case "L" => Position(-1, 0)
        case "U" => Position(0, 1)
        case "D" => Position(0, -1)
        case _ => throw Error("Invalid direction")
    
    def parseMove(move: String): Move = move match
        case s"$direction $times" => Move(parseDirection(direction), times.toInt)
    
    def parseMoves(content: String): Seq[Move] = content.linesIterator.map(parseMove).toSeq

    def solve(p: Problem): Solution | (Solution, Solution) = 
        val fileContents: String = p.parseInputAsString()
        val moves = parseMoves(fileContents)

        val tailPositions = getTailPositionsAfterMoves(Position(0,0), Position(0,0), moves)

        Solution(tailPositions.length)

end Day9
