package snma.lisp_interpreter.model

enum class TokenType {
    WHITESPACE,
    PARENTHESES_OPEN,
    PARENTHESES_CLOSE,
    LITERAL,
}

data class Token(
    val type: TokenType,
    val str: String,
)

