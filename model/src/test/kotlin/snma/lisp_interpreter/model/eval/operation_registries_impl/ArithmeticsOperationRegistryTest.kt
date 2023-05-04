package snma.lisp_interpreter.model.eval.operation_registries_impl

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import snma.lisp_interpreter.model.eval.Interpreter
import snma.lisp_interpreter.model.lexer.Lexer
import snma.lisp_interpreter.model.parser.LispNumber
import snma.lisp_interpreter.model.parser.Parser
import java.math.BigDecimal

private data class ArithmeticsIntegrationTestCase (
    val program: String,
    val expected: BigDecimal,
)

class ArithmeticsOperationRegistryTest : FunSpec({
    sequenceOf(
        ArithmeticsIntegrationTestCase(
            program = "(+ 1 2)",
            expected = 3.toBigDecimal(),
        ),
        ArithmeticsIntegrationTestCase(
            program = "(+)",
            expected = BigDecimal.ZERO,
        ),
        ArithmeticsIntegrationTestCase(
            program = "(+ 1)",
            expected = BigDecimal.ONE,
        ),
        ArithmeticsIntegrationTestCase(
            program = "(+ 1 2 3)",
            expected = 6.toBigDecimal(),
        ),
        ArithmeticsIntegrationTestCase(
            program = "(+ 1 (+ 2 3))",
            expected = 6.toBigDecimal(),
        ),
    ).forEach {
        test(it.program) {
            val tokens = Lexer().parse(it.program).toList()
            val sExpressionInput = Parser().parse(tokens)
            val result = Interpreter(ArithmeticOperationRegistry).eval(sExpressionInput)

            result shouldBe LispNumber(it.expected)
        }
    }
})