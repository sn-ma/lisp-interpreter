package snma.lisp_interpreter.model.eval

import snma.lisp_interpreter.model.*

class Interpreter (
    private val operationRegistry: OperationRegistry,
) {
    fun eval(sExpression: SExpression, interpretationContext: InterpretationContext = InterpretationContext(this)): SExpression =
        when (sExpression) {
            is LispPair -> {
                val functionIdentifier = eval(sExpression.left)
                check(functionIdentifier is LispAtom)
                val operation = operationRegistry[functionIdentifier.name]
                if (operation != null) {
                    operation.perform(interpretationContext, sExpression.right)
                } else {
                    val function = interpretationContext.variables[functionIdentifier.name]
                    check (function != null) {
                        "Can't find either operation or function with name ${functionIdentifier.name}"
                    }
                    check (function is LispFunction) {
                        "${functionIdentifier.name} is not a function"
                    }
                    function.eval(evalList(sExpression.right, interpretationContext), interpretationContext)
                }
            }

            is LispAtom -> {
                interpretationContext.variables[sExpression.name]
                    ?: sExpression
            }

            else -> {
                sExpression
            }
        }

    private fun evalList(sExpression: SExpression, interpretationContext: InterpretationContext): SExpression {
        if (sExpression is LispNull) {
            return sExpression
        }
        check (sExpression is LispPair) { "List expected" }
        return LispPair(
            interpretationContext.eval(sExpression.left),
            evalList(sExpression.right, interpretationContext)
        )
    }
}