package snma.lisp_interpreter.model.lexer

import kotlin.reflect.KClass

class Lexer {
    private fun StringBuilder.toNumber(charPosition: Int): Number =
        Number(toString()
            .toBigDecimalOrNull()
            ?: throw LexerException("Can't parse as number: '$this'", charPosition)
        )

    fun parse(str: String): Sequence<Token> = sequence {
        var currToken: KClass<out Token>? = null
        val buffer = StringBuilder()

        str.forEachIndexed { charIndex, char ->
            when (currToken) {
                null -> when {
                    char.isWhitespace() -> { /* Do nothing */ }
                    char == '(' -> {
                        yield(ParenthesesOpen)
                        currToken = null
                    }
                    char == ')' -> {
                        yield(ParenthesesClose)
                        currToken = null
                    }
                    char == '"' -> {
                        currToken = StringLiteral::class
                    }
                    char.isDigit() -> {
                        buffer.append(char)
                        currToken = Number::class
                    }
                    else -> {
                        buffer.append(char)
                        currToken = Identifier::class
                    }
                }
                StringLiteral::class -> {
                    when (char) {
                        '"' -> {
                            yield(StringLiteral(buffer.toString()))
                            buffer.clear()
                            currToken = null
                        }
                        else -> {
                            buffer.append(char)
                        }
                    }
                }
                Number::class -> when {
                    char.isDigit() || char == '.' -> {
                        buffer.append(char)
                    }
                    char.isWhitespace() || char == '(' || char == ')' -> {
                        yield(buffer.toNumber(charIndex))
                        buffer.clear()
                        currToken = null
                        when {
                            char == '(' -> {
                                yield(ParenthesesOpen)
                            }
                            char == ')' -> {
                                yield(ParenthesesClose)
                            }
                            char.isWhitespace() -> { /* Do nothing */ }
                            else -> throw InternalError("Unexpected char '$char' after number")
                        }
                    }
                    else -> {
                        throw LexerException("Unexpected symbol in number: '$char'", charIndex)
                    }
                }
                Identifier::class -> when {
                    char.isLetterOrDigit() || char == '_' -> {
                        buffer.append(char)
                    }
                    else -> {
                        yield(Identifier(buffer.toString()))
                        buffer.clear()
                        when {
                            char == '(' -> {
                                yield(ParenthesesOpen)
                                currToken = null
                            }
                            char == ')' -> {
                                yield(ParenthesesClose)
                                currToken = null
                            }
                            char.isWhitespace() -> {
                                currToken = null
                            }
                            else -> {
                                throw LexerException("Unexpected symbol in identifier: $char", charIndex)
                            }
                        }
                    }
                }
                else -> throw InternalError("Unexpected state $currToken")
            }
        }

        if (buffer.isNotEmpty()) {
            when (currToken) {
                StringLiteral::class -> {
                    yield(StringLiteral(buffer.toString()))
                }
                Identifier::class -> {
                    yield(Identifier(buffer.toString()))
                }
                Number::class -> {
                    yield(buffer.toNumber(str.length - 1))
                }
            }
        }
    }
}

class LexerException(message: String, charPosition: Int): Exception("$message at symbol $charPosition")