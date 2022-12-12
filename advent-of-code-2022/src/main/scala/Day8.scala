import Utils.Solver
import Utils.Problem
import Utils.Solution

object Day8 extends Solver:

    type Position = (Int, Int)
    type Grid[A] = Seq[Seq[A]]

    type Forest = Grid[Int]

    // Basic grid manipulation
    def getTree(forest: Forest, position: Position): Int = forest(position(1))(position(0))
    def getRow(forest: Forest, position: Position): Seq[Int] = forest(position(1)).toSeq
    def getCol(forest: Forest, position: Position): Seq[Int] = forest.map(_(position(0))).toSeq
    def getNumRows(forest: Forest): Int = forest.length
    def getNumCols(forest: Forest): Int = forest(0).length

    // Also basic sub tree manipulation
    def getRowLeft(forest: Forest, position: Position): Seq[Int] = getRow(forest, position).dropRight(getNumCols(forest) - position(0))
    def getRowRight(forest: Forest, position: Position): Seq[Int] = getRow(forest, position).drop(position(0) + 1)

    def getColUp(forest: Forest, position: Position): Seq[Int] = getCol(forest, position).dropRight(getNumRows(forest) - position(1))
    def getColDown(forest: Forest, position: Position): Seq[Int] = getCol(forest, position).drop(position(1) + 1)

    // calculating visibility
    def isVisibleLeft(forest: Forest, position: Position): Boolean = getRowLeft(forest, position).filter(tree => tree >= getTree(forest, position)).length == 0
    def isVisibleRight(forest: Forest, position: Position): Boolean = getRowRight(forest, position).filter(tree => tree >= getTree(forest, position)).length == 0
    def isVisibleUp(forest: Forest, position: Position): Boolean = getColUp(forest, position).filter(tree => tree >= getTree(forest, position)).length == 0
    def isVisibleDown(forest: Forest, position: Position): Boolean = getColDown(forest, position).filter(tree => tree >= getTree(forest, position)).length == 0

    def isVisible(forest: Forest, position: Position): Boolean = isVisibleLeft(forest, position) || isVisibleRight(forest, position) || isVisibleUp(forest, position) || isVisibleDown(forest, position)

    def getVisiblePositions(forest: Forest): Seq[Position] = getAllPositions(forest).filter(position => isVisible(forest, position))

    // calculating viewing distance
    //
    // tree = 5 => of row Seq(2, *5*, 1, 6, 2)
    // > we can see 1 tree (Seq(*2*, 5, 1, 6, 2)) to the left
    // > we can see 2 trees (Seq(2, 5, *1*, *6*, 2)) to the right
    // rowLeft = Seq(2)
    // rowRight = Seq(1, 6, 2)
    // rowLeft.span(_ < tree) => (Seq(2), Seq()) // last tree was reached, we can only see tree 2
    // rowRight.span(_ < tree) => (Seq(1), Seq(6, 2)) // we can see tree 1, but we can still see tree 6
    def getViewingDistanceTreesLeft(forest: Forest, position: Position): Seq[Int] = 
        val (in, out) = getRowLeft(forest, position).reverse.span(_ < getTree(forest, position))
        if out.length == 0 then in else in ++ out.take(1)

    def getViewingDistanceTreesRight(forest: Forest, position: Position): Seq[Int] =
        val (in, out) = getRowRight(forest, position).span(_ < getTree(forest, position))
        if out.length == 0 then in else in ++ out.take(1)
        
    def getViewingDistanceTreesUp(forest: Forest, position: Position): Seq[Int] =
        val (in, out) = getColUp(forest, position).reverse.span(_ < getTree(forest, position))
        if out.length == 0 then in else in ++ out.take(1)

    def getViewingDistanceTreesDown(forest: Forest, position: Position): Seq[Int] =
        val (in, out) = getColDown(forest, position).span(_ < getTree(forest, position))
        if out.length == 0 then in else in ++ out.take(1)

    def getViewingScore(forest: Forest, position: Position): Int =
        (getViewingDistanceTreesLeft(forest, position) ++
            getViewingDistanceTreesRight(forest, position) ++
            getViewingDistanceTreesUp(forest, position) ++
            getViewingDistanceTreesDown(forest, position)).length

    def getMaxViewingScore(forest: Forest): Int = getAllPositions(forest).map(position => getViewingScore(forest, position)).max

    def getAllPositions(forest: Forest): Seq[Position] = Seq.range(0, getNumCols(forest)).map(x => Seq.range(0, getNumRows(forest)).map(y => (x, y))).flatten

    def parseRow(row: String): Seq[Int] = row.toCharArray.map(_.asDigit).toSeq
    def parseForest(content: String): Forest = content.split("\n").map(parseRow)

    def solve(p: Problem): Solution | (Solution, Solution) =
        val fileContents = p.parseInputAsString()
        val forest = parseForest(fileContents)

        (
            Solution(getVisiblePositions(forest).length),
            Solution(getMaxViewingScore(forest)),
        )

  
end Day8
