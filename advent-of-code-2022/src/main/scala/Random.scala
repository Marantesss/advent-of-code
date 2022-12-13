object Random {
    case class Tree(data: Int, left: Tree = null, right: Tree = null)

    val tree = Tree(
        1,
        Tree(
            2,
            Tree(4),
            Tree(5),
        ),
        Tree(
            3,
            Tree(6),
            Tree(7),
        )
    )

     val dfs = Seq.unfold(Seq(tree)) { t =>
            println(t)
            t.headOption.flatMap(n => Some(n.data, Seq(n.left, n.right).filter(_ != null) ++ t.tail))
        }
}
