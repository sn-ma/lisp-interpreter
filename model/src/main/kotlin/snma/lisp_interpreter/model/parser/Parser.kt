package snma.lisp_interpreter.model.parser

import snma.lisp_interpreter.model.lexer.*
import snma.lisp_interpreter.model.lexer.Number

class Parser(
    private val nullString: String = "null",
) {
    private class ParserCallResult (
        val sExpression: SExpression,
        val nextTokenIndex: Int,
    )

    fun parse(tokens: List<Token>): SExpression {
        return parseExpression(tokens, 0).sExpression
    }

    private fun parseExpression(tokens: List<Token>, startIndex: Int) : ParserCallResult {
        if (tokens[startIndex] == ParenthesesOpen) {
            return parseParenthesis(tokens, startIndex)
        }
        val expression = when (val token = tokens[startIndex]) {
            is StringLiteral -> {
                LispString(token.value)
            }
            is Number -> {
                LispNumber(token.value)
            }
            is Identifier -> {
                if (token.name.equals(nullString, true)) {
                    LispNull
                } else {
                    LispAtom(token.name)
                }
            }
            else -> throw ParserException("Unexpected token (string, number, atom or null expected)", startIndex)
        }
        return ParserCallResult(expression, startIndex + 1)
    }

    private fun parseParenthesis(tokens: List<Token>, startIndex: Int) : ParserCallResult {
        if (tokens[startIndex] != ParenthesesOpen) {
            throw ParserException("Expected parenthesis open", startIndex)
        }
        val innerExpressions = mutableListOf<SExpression>()
        var currentIndex = startIndex + 1
        while (tokens[currentIndex] != ParenthesesClose) {
            val parsedInner = parseExpression(tokens, currentIndex)
            innerExpressions.add(parsedInner.sExpression)
            currentIndex = parsedInner.nextTokenIndex
            if (currentIndex == tokens.size) {
                throw ParserException("')' expected", currentIndex)
            }
        }

        return ParserCallResult(innerExpressions.combine(), currentIndex + 1)
    }

    private fun List<SExpression>.combine(): SExpression = if (isEmpty()) {
        LispNull
    } else {
        LispPair(
            this[0],
            this.subList(1, size).combine(),
        )
    }
}

class ParserException (
    message: String,
    tokenIndex: Int,
) : Exception("$message at position $tokenIndex")