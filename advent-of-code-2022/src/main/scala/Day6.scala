import Utils.Solver
import Utils.Problem
import Utils.Solution

object Day6 extends Solver:

    final val PacketMarkerSize = 4
    type PacketMarker = (Char, Char, Char, Char)

    final val MessageMarkerSize = 14
    type MessageMarker = (Char, Char, Char, Char, Char, Char, Char, Char, Char, Char, Char, Char, Char, Char)

    def isPacketMarker(marker: PacketMarker): Boolean = marker.toArray.distinct.length == PacketMarkerSize
    def isMessageMarker(marker: MessageMarker): Boolean = marker.toArray.distinct.length == MessageMarkerSize

    def getStartOfPacket(stream: Seq[Char]): Int = stream match
        case x if x.length < PacketMarkerSize => throw Error("Start of packet not found")
        case x if isPacketMarker((x(0), x(1), x(2), x(3))) => PacketMarkerSize
        case _ => 1 + getStartOfPacket(stream.drop(1))

    def getStartOfMessage(stream: Seq[Char]): Int = stream match
        case x if x.length < MessageMarkerSize => throw Error("Start of packet not found")
        case x if isMessageMarker((x(0), x(1), x(2), x(3), x(4), x(5), x(6), x(7), x(8), x(9), x(10), x(11), x(12), x(13))) => MessageMarkerSize
        case _ => 1 + getStartOfMessage(stream.drop(1))

    def solve(p: Problem): Solution | (Solution, Solution) =
        val fileContents: String = p.parseInputAsString()

        (Solution(getStartOfPacket(fileContents.toCharArray)), Solution(getStartOfMessage(fileContents.toCharArray)))

end Day6
