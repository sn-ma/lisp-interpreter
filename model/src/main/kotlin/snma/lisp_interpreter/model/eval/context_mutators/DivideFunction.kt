package snma.lisp_interpreter.model.eval.context_mutators

import snma.lisp_interpreter.model.*
import snma.lisp_interpreter.model.eval.InterpretationContext
import snma.lisp_interpreter.model.eval.toJvmList
import java.math.BigDecimal

val DIVIDE_FUNCTION = object : LispFunction {
    override fun eval(args: SExpression, context: InterpretationContext): SExpression {
        if (args is LispNull) {
            return LispNumber(BigDecimal.valueOf(Double.POSITIVE_INFINITY))
        }
        check (args is LispPair) { "Lisp expected" }

        val argsList = args.toJvmList()
        check (argsList.all { it is LispNumber }) {
            "Only numbers are expected for division function"
        }
        return when (argsList.size) {
            1 -> LispNumber(BigDecimal.ONE / (argsList[0] as LispNumber).value)
            else -> LispNumber((argsList[0] as LispNumber).value / argsList
                .subList(1, argsList.size)
                .asSequence()
                .map { (it as LispNumber).value }
                .reduce {acc, number -> acc * number }
            )
        }
    }
}
