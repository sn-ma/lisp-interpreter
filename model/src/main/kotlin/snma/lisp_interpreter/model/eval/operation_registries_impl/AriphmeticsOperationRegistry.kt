package snma.lisp_interpreter.model.eval.operation_registries_impl

import snma.lisp_interpreter.model.eval.InterpretationContext
import snma.lisp_interpreter.model.eval.Operation
import snma.lisp_interpreter.model.eval.OperationRegistryImpl
import snma.lisp_interpreter.model.parser.*
import java.math.BigDecimal

private val SumOperation = object : Operation {
    override fun perform(context: InterpretationContext, sExpression: SExpression): LispNumber = when (sExpression) {
        LispNull -> LispNumber(BigDecimal.ZERO)
        is LispNumber -> sExpression
        is LispPair -> {
            val left = context.interpreter.eval(sExpression.left, context)
            check (left is LispNumber) { "Number expected, got $left" }
            val right = perform(context, sExpression.right)
            LispNumber(left.value + right.value)
        }
        is LispAtom -> {
            val value = context.variables[sExpression.name]
            check (value != null) { "Variable ${sExpression.name} not found in the context" }
            check (value is LispNumber) { "Variable ${sExpression.name} is $value, but must be a number"}
            value
        }

        else -> error("Expression of type ${sExpression::class} isn't supported")
    }
}

val ArithmeticOperationRegistry = OperationRegistryImpl(mapOf(
    "+" to SumOperation,
))