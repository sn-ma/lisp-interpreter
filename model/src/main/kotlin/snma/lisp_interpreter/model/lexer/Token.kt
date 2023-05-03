package snma.lisp_interpreter.model.lexer

import java.math.BigDecimal

//enum class TokenType {
//    PARENTHESES_OPEN,
//    PARENTHESES_CLOSE,
//    STRING_LITERAL,
//    IDENTIFIER,
//    NUMBER,
//}
//
//data class Token(
//    val type: TokenType,
//    val str: String,
//)

sealed interface Token

object ParenthesesOpen: Token

object ParenthesesClose: Token

data class StringLiteral (
    val value: String,
): Token

data class Identifier (
    val name: String,
): Token

data class Number (
    val value: BigDecimal,
): Token
