package snma.lisp_interpreter.model.lexer

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private data class LexerTestCase(
    val string: String,
    val tokens: List<Token>,
)

class LexerTest : FunSpec({
    listOf(
        LexerTestCase(
            string = "(+ 1 20)",
            tokens = listOf(
                Token(TokenType.PARENTHESES_OPEN, "("),
                Token(TokenType.LITERAL, "+"),
                Token(TokenType.LITERAL, "1"),
                Token(TokenType.LITERAL, "20"),
                Token(TokenType.PARENTHESES_CLOSE, ")"),
            ),
        ),
        LexerTestCase(
            string = "(+  1  20)",
            tokens = listOf(
                Token(TokenType.PARENTHESES_OPEN, "("),
                Token(TokenType.LITERAL, "+"),
                Token(TokenType.LITERAL, "1"),
                Token(TokenType.LITERAL, "20"),
                Token(TokenType.PARENTHESES_CLOSE, ")"),
            ),
        ),
        LexerTestCase(
            string = "(+ 10 (* 20 30))",
            tokens = listOf(
                Token(TokenType.PARENTHESES_OPEN, "("),
                Token(TokenType.LITERAL, "+"),
                Token(TokenType.LITERAL, "10"),
                Token(TokenType.PARENTHESES_OPEN, "("),
                Token(TokenType.LITERAL, "*"),
                Token(TokenType.LITERAL, "20"),
                Token(TokenType.LITERAL, "30"),
                Token(TokenType.PARENTHESES_CLOSE, ")"),
                Token(TokenType.PARENTHESES_CLOSE, ")"),
            ),
        ),
    ).forEach { (string, expectedTokens) ->
        test(string) {
            val lexer = Lexer()
            lexer.parse(string).toList() shouldBe expectedTokens
        }
    }
})