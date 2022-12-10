import Utils.Solver
import Utils.Problem
import Utils.Solution
import Utils.Solver
import scala.collection.mutable.Stack

object Day5 extends Solver:

    // "[A] [B] [C]"
    // "[A] " -> 4 chars
    final val CrateStringSize = 4
    // Stacks start at 1 in the input file
    final val StartStackIndex = 1

    type Crate = Char
    type CStack = Stack[Crate]
    type Warehouse = Seq[CStack]
    // (Number of crates, From Stack, To Stack)
    type Move = (Int, Int, Int)

    // "[A]" => 'A'
    // "" => ' '
    def parseCrate(crate: String): Crate = crate match
        case s"[$cr]" => cr.head
        case "" => ' '
        case _ => throw Error("Invalid crate string")

    // "[A] [B] [C]" => Seq("[A]", "[B]", "[C]")
    // "    [B]    " => Seq("", "[B]", "")
    def parseCrateLine(line: String): Seq[Crate] = line.grouped(CrateStringSize).map(_.trim).map(parseCrate).toSeq

    // "move 1 from 2 to 1" => (1, 1, 0)
    def parseMove(move: String): Move = move match
        case s"move $crate from $fromStack to $toStack" => (crate.toInt, fromStack.toInt - StartStackIndex, toStack.toInt - StartStackIndex)
        case _ => throw Error("Invalid move string")

    def parseMoves(moves: String): Seq[Move] = moves.split("\n").map(parseMove).toSeq

    def parseWarehouse(warehouse: String): Warehouse = populateWarehouse(warehouse.split("\n").dropRight(1).map(parseCrateLine))

    def populateWarehouse(stackLines: Seq[Seq[Crate]]): Warehouse =
        val warehouse = Seq.fill(stackLines(0).length)(Stack[Crate]())
        stackLines.reverse.foreach(line => line.zipWithIndex.foreach({
            case (_: ' ', index) => None
            case (crate, index) => warehouse(index).push(crate)
        }))
        warehouse

    def parseContent(content: String): (Warehouse, Seq[Move]) =
        val (contentWarehouse, contentMoves) = (content.split("\n\n")(0), content.split("\n\n")(1))
        (parseWarehouse(contentWarehouse), parseMoves(contentMoves))

    def makeMove(warehouse: Warehouse, move: Move): Unit =
        val (numberOfCrates, fromIndex, toIndex) = move
        1 to numberOfCrates foreach { _ => warehouse(toIndex).push(warehouse(fromIndex).pop())}

    def getSolution(warehouse: Warehouse): String = warehouse.map(_.head).mkString

    def solve(p: Problem): Solution | (Solution, Solution) = 
        val fileContents: String = p.parseInputAsString(false)
        val (warehouse, moves) = parseContent(fileContents)

        moves.foreach(move => makeMove(warehouse, move))
        Solution(getSolution(warehouse))

    // NOT USED
    // saving this cause scala pattern matching is pretty cool
    def parseContentLine(line: String): Move | Int | Seq[Crate] | Null = line match
        case s"    $_" | s"[$_" => parseCrateLine(line)
        case s" $_" => parseStackLine(line)
        case s"move$_" => parseMove(line)
        case "" => null
        case _ => throw Error("Invalid input string")

    // NOT USED
    // " 1   2   3 " => 3
    def parseStackLine(line: String): Int = line.grouped(CrateStringSize).length
end Day5
