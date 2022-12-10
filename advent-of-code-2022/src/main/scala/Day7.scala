import Utils.Solver
import Utils.Problem
import Utils.Solution

case class File(name: String, size: Int)
case class Directory(
    name: String,
    parent: Directory,
    children: Map[String, Directory], // "/" -> Root
    files: Seq[File]
)

sealed trait Command(command: String)
case class CommandChangeDirectory(directory: String) extends Command("cd")
case class CommandList(output: Seq[String]) extends Command("ls")

object Day7 extends Solver:

    final val root = Directory("/", null, null, null)
    val currentDirectory = root

    def parseCommandListOutput(output: String): File | Directory = output match
        case s"dir $dir" => Directory(dir, currentDirectory, Map(), Seq[File]())
        case s"$size $name" => currentDirectory.files = currentDirectory.files ++ Seq(File(name, size.toInt))
        case com: Any => throw Error(s"Invalid command list output: $com")
    
    def doCommand(command: Command) = command match
        case ls: CommandList => ls.output.map(parseCommandListOutput).toSeq
        case cd: CommandChangeDirectory => 
    


    def parseCommand(command: String): Command = command match
        case s"cd $directory" => CommandChangeDirectory(directory.trim)
        case s"ls$output" => CommandList(output.trim.split("\n").toSeq)
        case com: Any => throw Error(s"Invalid command input: $com")

    def parseCommands(content: String): Seq[Command] =
        val c = content.split("\\$ ").drop(1) // remove first element (0: "")
        c.map(parseCommand).toSeq

    def solve(p: Problem): Solution | (Solution, Solution) = 
        val fileContents: String = p.parseInputAsString()

        val commands = parseCommands(fileContents)
        println(commands)

        Solution(0)
end Day7
