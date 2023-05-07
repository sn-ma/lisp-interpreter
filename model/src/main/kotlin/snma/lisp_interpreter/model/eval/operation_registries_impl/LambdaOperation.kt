package snma.lisp_interpreter.model.eval.operation_registries_impl

import snma.lisp_interpreter.model.LispPair
import snma.lisp_interpreter.model.SExpression
import snma.lisp_interpreter.model.eval.InterpretationContext
import snma.lisp_interpreter.model.eval.LispUserFunction
import snma.lisp_interpreter.model.eval.Operation

val lambdaOperation = object : Operation {
    override fun perform(context: InterpretationContext, sExpression: SExpression): SExpression {
        check (sExpression is LispPair) { "Lambda requires a list of arguments" }
        return LispUserFunction(sExpression.left, sExpression.right)
    }
}