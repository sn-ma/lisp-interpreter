package snma.lisp_interpreter.model.parser

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import snma.lisp_interpreter.model.lexer.*
import snma.lisp_interpreter.model.lexer.Number

private data class ParserTestCase(
    val name: String,
    val tokens: List<Token>,
    val sExpressionExpected: SExpression
)

class ParserTest: FunSpec({
    sequenceOf(
        ParserTestCase(
            name = "42",
            tokens = listOf(
                Number(42.toBigDecimal()),
            ),
            sExpressionExpected = LispNumber(42.toBigDecimal())
        ),
        ParserTestCase(
            name = "(+ 1 2)",
            tokens = listOf(
                ParenthesesOpen,
                Identifier("+"),
                Number(1.toBigDecimal()),
                Number(2.toBigDecimal()),
                ParenthesesClose,
            ),
            sExpressionExpected = LispPair(
                LispAtom("+"),
                LispPair(
                    LispNumber(1.toBigDecimal()),
                    LispPair(
                        LispNumber(2.toBigDecimal()),
                        LispNull,
                    )
                )
            ),
        ),
        ParserTestCase(
            name = "(+ 1 2 3)",
            tokens = listOf(
                ParenthesesOpen,
                Identifier("+"),
                Number(1.toBigDecimal()),
                Number(2.toBigDecimal()),
                Number(3.toBigDecimal()),
                ParenthesesClose,
            ),
            sExpressionExpected = LispPair(
                LispAtom("+"),
                LispPair(
                    LispNumber(1.toBigDecimal()),
                    LispPair(
                        LispNumber(2.toBigDecimal()),
                        LispPair(
                            LispNumber(3.toBigDecimal()),
                            LispNull,
                        )
                    )
                )
            ),
        ),
        ParserTestCase(
            name = "(+ (* 1 2) (/ 3 4))",
            tokens = listOf(
                ParenthesesOpen,
                Identifier("+"),
                ParenthesesOpen,
                Identifier("*"),
                Number(1.toBigDecimal()),
                Number(2.toBigDecimal()),
                ParenthesesClose,
                ParenthesesOpen,
                Identifier("/"),
                Number(3.toBigDecimal()),
                Number(4.toBigDecimal()),
                ParenthesesClose,
                ParenthesesClose,
            ),
            sExpressionExpected = LispPair(
                LispAtom("+"),
                LispPair(
                    LispPair(
                        LispAtom("*"),
                        LispPair(
                            LispNumber(1.toBigDecimal()),
                            LispPair(
                                LispNumber(2.toBigDecimal()),
                                LispNull,
                            ),
                        ),
                    ),
                    LispPair(
                        LispPair(
                            LispAtom("/"),
                            LispPair(
                                LispNumber(3.toBigDecimal()),
                                LispPair(
                                    LispNumber(4.toBigDecimal()),
                                    LispNull,
                                ),
                            ),
                        ),
                        LispNull,
                    ),
                )
            ),
        ),
    ).forEach {
        test(it.name) {
            Parser().parse(it.tokens) shouldBe it.sExpressionExpected
        }
    }
})
