import Utils.Solution
import Utils.Problem
import Utils.Solver


object Day1 extends Solver:
    // Array is mutable, use sequence instead
    type Elf = Seq[Int]
    
    def parseElves(input: String) = input.split("\n\n").toSeq.map(parseElf)

    def parseElf(elf: String): Elf = elf.split("\n").toSeq.map(x => x.toInt)

    def getMaxElf(elves: Seq[Elf]): Int = elves.map(elf => elf.sum).max

    def getMax3Elves(elves: Seq[Elf]): Int = elves.map(elf => elf.sum).sorted.reverse.take(3).sum

    def solve(p: Problem): Solution | (Solution, Solution) =
        val fileContents: String = p.parseInputAsString()
        val elves = parseElves(fileContents)
        return (Solution(getMaxElf(elves)), Solution(getMax3Elves(elves)))

end Day1
