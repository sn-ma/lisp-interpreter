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
                ParenthesesOpen,
                Identifier("+"),
                Number(1.toBigDecimal()),
                Number(20.toBigDecimal()),
                ParenthesesClose,
            ),
        ),
        LexerTestCase(
            string = "(+  1  20)",
            tokens = listOf(
                ParenthesesOpen,
                Identifier("+"),
                Number(1.toBigDecimal()),
                Number(20.toBigDecimal()),
                ParenthesesClose,
            ),
        ),
        LexerTestCase(
            string = "(+ 10 (* 20 30))",
            tokens = listOf(
                ParenthesesOpen,
                Identifier("+"),
                Number(10.toBigDecimal()),
                ParenthesesOpen,
                Identifier("*"),
                Number(20.toBigDecimal()),
                Number(30.toBigDecimal()),
                ParenthesesClose,
                ParenthesesClose,
            ),
        ),
        LexerTestCase(
            string = "(foo)",
            tokens = listOf(
                ParenthesesOpen,
                Identifier("foo"),
                ParenthesesClose,
            ),
        ),
        LexerTestCase(
            string = "(foo 1)",
            tokens = listOf(
                ParenthesesOpen,
                Identifier("foo"),
                Number(1.toBigDecimal()),
                ParenthesesClose,
            ),
        ),
        LexerTestCase(
            string = "(foo \"abc\")",
            tokens = listOf(
                ParenthesesOpen,
                Identifier("foo"),
                StringLiteral("abc"),
                ParenthesesClose,
            ),
        ),
    ).forEach { (string, expectedTokens) ->
        test(string) {
            val lexer = Lexer()
            lexer.parse(string).toList() shouldBe expectedTokens
        }
    }
})