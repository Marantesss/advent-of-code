import Utils.Solver
import Utils.Problem
import Utils.Solution

object Day4 extends Solver:
    type Elf = Set[Int]
    type ElfPair = (Elf, Elf)

    def overlaps(pair: ElfPair): Boolean = pair(0).subsetOf(pair(1)) || pair(1).subsetOf(pair(0))

    def slightOverlaps(pair: ElfPair): Boolean = (pair(0) & pair(1)).size != 0

    // 2-4
    def parseElf(elf: String): Elf =
        val elfSeq = elf.split("-").map(_.toInt)
        Set.range(elfSeq(0), elfSeq(1) + 1)

    // 2-4,6-8
    def parseElfPairs(pair: String): ElfPair = 
        val elfPair = pair.split(",").map(parseElf)
        (elfPair(0), elfPair(1))

    def parseContent(content: String): Seq[ElfPair] = content.split("\n").map(parseElfPairs).toSeq

  
    def solve(p: Problem): Solution | (Solution, Solution) = 
        val fileContents: String = p.parseInputAsString()
        val pairs = parseContent(fileContents)

        val overlapCount = pairs.count(overlaps)
        val slightOverlapCount = pairs.count(slightOverlaps)

        (Solution(overlapCount), Solution(slightOverlapCount))
end Day4
