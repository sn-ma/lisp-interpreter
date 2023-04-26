package snma.lisp_interpreter.model

class Lexer {
    fun parse(str: String): Sequence<Token> = sequence {
        var currToken = TokenType.WHITESPACE
        val buffer = StringBuilder()

        for (char in str) {
            when (currToken) {
                TokenType.WHITESPACE -> when {
                    char.isWhitespace() -> { /* Do nothing */ }
                    char == '(' -> {
                        yield(Token(TokenType.PARENTHESES_OPEN, char.toString()))
                        currToken = TokenType.WHITESPACE
                    }
                    char == ')' -> {
                        yield(Token(TokenType.PARENTHESES_CLOSE, char.toString()))
                        currToken = TokenType.WHITESPACE
                    }
                    else -> {
                        buffer.append(char)
                        currToken = TokenType.LITERAL
                    }
                }
                TokenType.LITERAL -> when {
                    char.isWhitespace() -> {
                        yield(Token(TokenType.LITERAL, buffer.toString()))
                        buffer.clear()
                        currToken = TokenType.WHITESPACE
                    }
                    char == '(' -> {
                        yield(Token(TokenType.LITERAL, buffer.toString()))
                        buffer.clear()
                        yield(Token(TokenType.PARENTHESES_OPEN, char.toString()))
                        currToken = TokenType.WHITESPACE
                    }
                    char == ')' -> {
                        yield(Token(TokenType.LITERAL, buffer.toString()))
                        buffer.clear()
                        yield(Token(TokenType.PARENTHESES_CLOSE, char.toString()))
                        currToken = TokenType.WHITESPACE
                    }
                    else -> {
                        buffer.append(char)
                        currToken = TokenType.LITERAL
                    }
                }
                else -> throw InternalError("Unexpected state $currToken")
            }
        }

        if (buffer.isNotEmpty()) {
            yield(Token(currToken, buffer.toString()))
        }
    }
}