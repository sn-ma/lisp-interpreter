package snma.lisp_interpreter.model.parser

import snma.lisp_interpreter.model.lexer.Token
import snma.lisp_interpreter.model.lexer.TokenType

class Parser {
    private class ParseCallResult(
        val node: Node,
        val nextTokenIndex: Int,
    )

    fun parse(tokens: List<Token>): Node {
        return parseImpl(tokens, 0).apply {
            check(nextTokenIndex == tokens.size) {
                "Expression too long"
            }
        }.node
    }

    private fun parseImpl(tokens: List<Token>, startIndex: Int): ParseCallResult {
        check(tokens[startIndex].type == TokenType.PARENTHESES_OPEN)
        val leaves = mutableListOf<Node>()
        var idx = startIndex + 1
        while (idx < tokens.size) {
            val token = tokens[idx]
            when (token.type) {
                TokenType.LITERAL -> {
                    leaves.add(Leaf(token.str))
                    ++idx
                }
                TokenType.PARENTHESES_OPEN -> {
                    val result = parseImpl(tokens, idx)
                    leaves.add(result.node)
                    idx = result.nextTokenIndex
                }
                TokenType.PARENTHESES_CLOSE -> {
                    return ParseCallResult(Branch(leaves), idx + 1)
                }
                else -> {
                    error("Unexpected token $token")
                }
            }
        }
        error("Parentheses close expected")
    }
}