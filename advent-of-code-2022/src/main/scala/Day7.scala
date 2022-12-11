import Utils.Solver
import Utils.Problem
import Utils.Solution

object Day7 extends Solver:

    enum LSResult:
        case Directory(name: String)
        case File(name: String, size: Int)

    enum Command:
        case CD(directory: String)
        case LS(results: Seq[LSResult])

    sealed trait FileSystemEntry:
        lazy val totalSize: Int

    case class File(size: Int) extends FileSystemEntry:
        override lazy val totalSize: Int = size

    case class Directory(items: Map[String, FileSystemEntry]) extends FileSystemEntry:
        override lazy val totalSize: Int = items.values.map(_.totalSize).sum

    /**
      * https://en.wikipedia.org/wiki/Zipper_(data_structure)
      *
      * @param name current directory
      * @param siblings any entries in the current directory, can be Files or Directories
      * @param parent parent directory, null for root "/"
      */
    case class FileSystemZipperContext(name: String, siblings: Map[String, FileSystemEntry], parent: Option[FileSystemZipperContext])

    /**
      * 
      *
      * @param current current files and directories in current directory
      * @param context current file system context
      */
    case class FileSystemZipper(current: Map[String, FileSystemEntry], context: Option[FileSystemZipperContext]):
        /**
          * cd ..
          *
          * @return
          */
        def back(): FileSystemZipper = 
            val FileSystemZipperContext(name, siblings, parent) = context.get
            FileSystemZipper(siblings + (name -> Directory(current)), parent)

        /**
          * cd /
          *
          * @return
          */
        def root(): FileSystemZipper = context match
            case None => this // we are already at root directory
            case Some(_) => this.back().root() // go back and try again
        
        /**
          * cd $name
          *
          * @return
          */
        def toDirectory(name: String): FileSystemZipper =
            val newCurrent = current(name) match
                case Directory(items) => items
                case File(name) => throw Error(s"Invalid 'cd' to file '$name'")
            val newContext = FileSystemZipperContext(name, current - name, context)
            FileSystemZipper(newCurrent, Some(newContext))


    final val DiskSize = 70_000_000
    final val UpdateSize = 30_000_000

    /**
     * Breadth first search
     *
     * @param entry FileSystemEntry
     * @return
     */
    def buildIterator(entry: FileSystemEntry): Iterator[Directory] = entry match
        case File(_) => Iterator()
        case dir@Directory(items) => Iterator.single(dir) ++ items.values.iterator.flatMap(buildIterator)


    def getTotalSizeOfSmallDirectories(root: FileSystemEntry) = buildIterator(root).map(_.totalSize).filter(_ <= 100_000).sum
    def getTotalSizeOfSmallestDirectoryToDelete(root: FileSystemEntry) =
        val sizeNeeded = UpdateSize - (DiskSize - root.totalSize)
        buildIterator(root).map(_.totalSize).filter(_ >= sizeNeeded).min

    /**
      * Builds FileSystemZipper
      *
      * @param commands list of commands
      * @return Root directory
      */
    def interpretCommands(commands: Seq[Command]): FileSystemEntry = Directory(
        commands.foldLeft(FileSystemZipper(Map.empty, None))(interpretCommand).root().current
    )

    def interpretCommand(zipper: FileSystemZipper, command: Command): FileSystemZipper = command match
        case Command.CD("/") => zipper.root()
        case Command.CD("..") => zipper.back()
        case Command.CD(directory) => zipper.toDirectory(directory)
        case Command.LS(results) => 
            val newCurrent = results.map(_ match
                case LSResult.File(name, size) => name -> File(size)
                case LSResult.Directory(name) => name -> Directory(Map[String, FileSystemEntry]())
            ).toMap[String, FileSystemEntry]
            zipper.copy(newCurrent)
            

    def parseConsoleLine(accumulator: Seq[Command], command: String): Seq[Command] = command match
        case s"$$ cd $directory" => accumulator :+ Command.CD(directory)
        case s"$$ ls" => accumulator :+ Command.LS(Nil)
        case line => accumulator match
            case accumulator :+ Command.LS(results) =>
                val newResult = line match
                    case s"dir $name" => LSResult.Directory(name)
                    case s"$size $name" => LSResult.File(name, size.toInt)
                    case _ => throw Error("Invalid output after 'ls' command")
                accumulator :+ Command.LS(newResult +: results)
            case _ => throw Error("Invalid output")


    def parseCommands(content: String): Seq[Command] =
        content.split("\n").foldLeft(Seq[Command]())(parseConsoleLine)

    def solve(p: Problem): Solution | (Solution, Solution) = 
        val fileContents: String = p.parseInputAsString()

        val commands = parseCommands(fileContents)
        val root = interpretCommands(commands)

        (
            Solution(getTotalSizeOfSmallDirectories(root)),
            Solution(getTotalSizeOfSmallestDirectoryToDelete(root)),
        )
end Day7
