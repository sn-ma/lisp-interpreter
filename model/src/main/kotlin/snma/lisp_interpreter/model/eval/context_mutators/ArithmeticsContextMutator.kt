package snma.lisp_interpreter.model.eval.context_mutators

import snma.lisp_interpreter.model.LispNumber
import snma.lisp_interpreter.model.SExpression
import java.math.BigDecimal

class ArithmeticsContextMutator : ContextMutator {
    override fun invoke(contextMap: MutableMap<String, SExpression>) {
        contextMap["+"] = lispReduceFunction(LispNumber(BigDecimal.ZERO)) { a, b ->
            LispNumber(a.value + b.value)
        }
        contextMap["*"] = lispReduceFunction(LispNumber(BigDecimal.ONE)) { a, b ->
            LispNumber(a.value * b.value)
        }
        contextMap["-"] = SUBTRACT_FUNCTION
        contextMap["/"] = DIVIDE_FUNCTION
    }
}