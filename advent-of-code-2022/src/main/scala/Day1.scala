import scala.io.Source

object Day1 {
    type Elf = Array[Int]

    val filenameSmall = "Day1_small.txt"
    val filenameLarge = "Day1_large.txt"

    def getFileAsString(filename: String) = Source.fromInputStream(getClass.getResourceAsStream(filename)).mkString.trim

    def parseElves(input: String) = input.split("\n\n").toArray.map(parseElf)

    def parseElf(elf: String): Elf = elf.split("\n").toArray.map(x => x.toInt)

    def getMaxElf(elves: Array[Elf]): Int = elves.map(elf => elf.sum).max

    def getMax3Elves(elves: Array[Elf]): Int = elves.map(elf => elf.sum).sorted.reverse.take(3).sum

    // def main(args:Array[String]): Unit = {
    //     val fileContents: String = getFileAsString(filenameLarge)
    //     val elves = parseElves(fileContents)
    //     println(getMaxElf(elves))
    //     println(getMax3Elves(elves))
    // }
}
