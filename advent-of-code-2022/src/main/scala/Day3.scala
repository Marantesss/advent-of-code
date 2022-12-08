import Utils.Solver
import Utils.Problem
import Utils.Solution

object Day3 extends Solver:

    type Rucksack = (Seq[Char], Seq[Char])

    def getDuplicateItemType(r: Rucksack): Char = r(0).find(item => r(1).contains(item)).get

    def getFlattenRucksack(r: Rucksack): Seq[Char] = r(0) ++ r(1)

    // Lowercase item types a through z have priorities 1 through 26.
    // Uppercase item types A through Z have priorities 27 through 52.
    // 'a'.toInt = 97
    // 'A'.toInt = 65
    final val UpperCharOffset = 65 - 27
    final val LowerCharOffset = 97 - 1
    
    def getItemPriority(item: Char): Int = if item.isLower then item.toInt - LowerCharOffset else item.toInt - UpperCharOffset

    def parseRucksack(s: String): Rucksack = (s.substring(0, s.length / 2).toCharArray, s.substring(s.length / 2).toCharArray)

    def parseContent(content: String): Seq[Rucksack] = content.split("\n").map(parseRucksack).toSeq

    // part 2
    type ElfGroup = (Rucksack, Rucksack, Rucksack)

    def parseElfGroups(rucksacks: Seq[Rucksack]): Seq[ElfGroup] =
        if rucksacks.isEmpty
            then Seq()
            else Seq((rucksacks(0), rucksacks(1), rucksacks(2))) ++ parseElfGroups(rucksacks.drop(3))

    def getElfGroupType(group: ElfGroup): Char = 
        val r0 = getFlattenRucksack(group(0))
        val r1 = getFlattenRucksack(group(1))
        val r2 = getFlattenRucksack(group(2))

        r0.find(item => r1.contains(item) && r2.contains(item)).get

    def solve(p: Problem): Solution | (Solution, Solution) = 
        val fileContents: String = p.parseInputAsString()
        val rucksacks = parseContent(fileContents)
        val elfGroups = parseElfGroups(rucksacks)

        return (
            Solution(rucksacks.map(getDuplicateItemType).map(getItemPriority).sum),
            Solution(elfGroups.map(getElfGroupType).map(getItemPriority).sum)
        )
        

  
end Day3
