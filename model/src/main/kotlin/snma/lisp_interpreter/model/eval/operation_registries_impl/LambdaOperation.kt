package snma.lisp_interpreter.model.eval.operation_registries_impl

import snma.lisp_interpreter.model.LispNull
import snma.lisp_interpreter.model.LispPair
import snma.lisp_interpreter.model.SExpression
import snma.lisp_interpreter.model.eval.InterpretationContext
import snma.lisp_interpreter.model.eval.LispUserFunction
import snma.lisp_interpreter.model.eval.Operation

val lambdaOperation = object : Operation {
    override fun perform(context: InterpretationContext, sExpression: SExpression): SExpression {
        check (sExpression is LispPair) { "Lambda requires a list of arguments" }
        check (sExpression.right is LispPair) { "Second argument of lambda should be an expression to evaluate" }
        check (sExpression.right.right is LispNull) { "Lambda should have only 2 parameters, but got more" }
        return LispUserFunction(sExpression.left, sExpression.right.left)
    }
}