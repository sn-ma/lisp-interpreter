package snma.lisp_interpreter.model.parser

interface Node

data class Branch(
    val nodes: List<Node>
) : Node

data class Leaf(
    val str: String
) : Node
