import Utils.Solver
import Utils.Problem
import Utils.Solution



object Day2 extends Solver:

    trait Play:
        val points: Int
    
    case class Rock() extends Play:
        val points = 1
    
    case class Paper() extends Play:
        val points = 2
    
    case class Scissors() extends Play:
        val points = 3
    
    // Left Play is opponent
    // Right Play is me
    type Round = (Play, Play)
    type Game = Seq[Round]
    
    final val Win = 6
    final val Draw = 3
    final val Lose = 0

    def getRoundScore(r: Round): Int = r match
        // I Lose
        // cannot be done (type matching does not work) :(
        // case p: (Scissors, Paper) | p: (Rock, Scissors) | p: (Paper, Rock) => Lose - p(0).points
        case (_: Scissors, p: Paper) => Lose + p.points
        case (_: Rock, s: Scissors) => Lose + s.points
        case (_: Paper, r: Rock) => Lose + r.points
        // I Win
        case (_: Paper, s: Scissors) => Win + s.points
        case (_: Scissors, r: Rock) => Win + r.points
        case (_: Rock, p: Paper) => Win + p.points
        // Draw
        case (_: Rock, r: Rock) => Draw + r.points
        case (_: Paper, p: Paper) => Draw + p.points
        case (_: Scissors, s: Scissors) => Draw + s.points
        // error
        case _ => throw Error("Invalid round type")

    def parsePlay(play: String): Play = play match
        case "A" | "X" => Rock()
        case "B" | "Y" => Paper()
        case "C" | "Z" => Scissors()
        case _ => throw Error("Invalid play string")
    
    def parseGame(contents: String): Game = contents.split("\n").map(r => r.split(" ")).map{
        case Array(p1: String, p2: String) => (parsePlay(p1), parsePlay(p2))
        case _ => throw Error("Invalid round string")
    }

    def getGameScore(game: Game): Int = game.map(getRoundScore).sum

    // Part 2
    def getWinningPlay(p: Play): Play = p match
        case _: Rock => Paper()
        case _: Paper => Scissors()
        case _: Scissors => Rock()
        case _ => throw Error("Invalid play type")

    def getLosingPlay(p: Play): Play = p match
        case _: Rock => Scissors()
        case _: Paper => Rock()
        case _: Scissors => Paper()
        case _ => throw Error("Invalid play type")

    def getDrawPlay(p: Play): Play = p match
        case _: Rock => Rock()
        case _: Paper => Paper()
        case _: Scissors => Scissors()
        case _ => throw Error("Invalid play type")

    def getCorrectHand(p: Play, outcome: String): Play = outcome match
        case "X" => getLosingPlay(p)
        case "Y" => getDrawPlay(p)
        case "Z" => getWinningPlay(p)
        case _ => throw Error("Invalid outcome")

    def parseGamePart2(contents: String): Game = contents.split("\n").map(r => r.split(" ")).map{
            case Array(p1: String, p2: String) => (parsePlay(p1), getCorrectHand(parsePlay(p1), p2))
            case _ => throw Error("Invalid round string")
        }
    
    def solve(p: Problem): Solution | (Solution, Solution) = 
        val fileContents: String = p.parseInputAsString()

        val game1 = parseGame(fileContents)
        
        val game2 = parseGamePart2(fileContents)
        
        return (Solution(getGameScore(game1)), Solution(getGameScore(game2)))
end Day2

