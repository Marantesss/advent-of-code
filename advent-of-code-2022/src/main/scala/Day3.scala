import Utils.Solver
import Utils.Problem
import Utils.Solution

object Day3 extends Solver:

    type Rucksack = (Seq[Char], Seq[Char])

    def getDuplicateItemType(r: Rucksack): Char = r(0).find(item => r(1).contains(item)).get

    // Lowercase item types a through z have priorities 1 through 26.
    // Uppercase item types A through Z have priorities 27 through 52.
    // 'a'.toInt = 97
    // 'A'.toInt = 65
    final val UpperCharOffset = 65 - 27
    final val LowerCharOffset = 97 - 1
    
    def getItemPriority(item: Char): Int = if item.isLower then item.toInt - LowerCharOffset else item.toInt - UpperCharOffset

    def parseRucksack(s: String): Rucksack = (s.substring(0, s.length / 2).toCharArray, s.substring(s.length / 2).toCharArray)

    def parseContent(content: String): Seq[Rucksack] = content.split("\n").map(parseRucksack).toSeq

    def solve(p: Problem): Solution | (Solution, Solution) = 
        val fileContents: String = p.parseInputAsString()
        val rucksacks = parseContent(fileContents)

        val duplicateItems = rucksacks.map(getDuplicateItemType)
        val priority = duplicateItems.map(getItemPriority)

        return (Solution(priority.sum), Solution(0))

  
end Day3
