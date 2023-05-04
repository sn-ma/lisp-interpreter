package snma.lisp_interpreter.model

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import snma.lisp_interpreter.model.eval.Interpreter
import snma.lisp_interpreter.model.eval.OperationRegistryBundle
import snma.lisp_interpreter.model.eval.operation_registries_impl.ArithmeticOperationRegistry
import snma.lisp_interpreter.model.eval.operation_registries_impl.SystemOperationRegistry
import snma.lisp_interpreter.model.lexer.Lexer
import snma.lisp_interpreter.model.parser.LispNumber
import snma.lisp_interpreter.model.parser.Parser
import snma.lisp_interpreter.model.parser.SExpression
import java.math.BigDecimal

private data class IntegrationTestCase (
    val program: String,
    val expected: SExpression,
)

class IntegrationTest : FunSpec({
    val operationRegistry = OperationRegistryBundle(setOf(
        SystemOperationRegistry,
        ArithmeticOperationRegistry,
    ))

    sequenceOf(
        IntegrationTestCase(
            program = "(+ 1 2)",
            expected = LispNumber(3.toBigDecimal()),
        ),
        IntegrationTestCase(
            program = "(+)",
            expected = LispNumber(BigDecimal.ZERO),
        ),
        IntegrationTestCase(
            program = "(+ 1)",
            expected = LispNumber(BigDecimal.ONE),
        ),
        IntegrationTestCase(
            program = "(+ 1 2 3)",
            expected = LispNumber(6.toBigDecimal()),
        ),
        IntegrationTestCase(
            program = "(+ 1 (+ 2 3))",
            expected = LispNumber(6.toBigDecimal()),
        ),
        IntegrationTestCase(
            program = "(+ 1 (* 2 3))",
            expected = LispNumber(7.toBigDecimal()),
        ),
        IntegrationTestCase(
            program = "(let ((a 1)) (+ a 2))",
            expected = LispNumber(3.toBigDecimal()),
        ),
        IntegrationTestCase(
            program = "(let ((a 1) (b 2)) (+ a b))",
            expected = LispNumber(3.toBigDecimal()),
        ),
    ).forEach {
        test("${it.program} => ${it.expected}") {
            val tokens = Lexer().parse(it.program).toList()
            val sExpressionInput = Parser().parse(tokens)
            val result = Interpreter(operationRegistry).eval(sExpressionInput)

            result shouldBe it.expected
        }
    }
})