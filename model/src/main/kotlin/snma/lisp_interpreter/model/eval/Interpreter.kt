package snma.lisp_interpreter.model.eval

import snma.lisp_interpreter.model.*

class Interpreter (
    private val operationRegistry: OperationRegistry,
) {
    fun eval(sExpression: SExpression, interpretationContext: InterpretationContext): SExpression =
        when (sExpression) {
            is LispPair -> {
                val functionOrOperationIdentifier = sExpression.left
                check (functionOrOperationIdentifier is LispAtom) {
                    "Function or operation name expected, but got $functionOrOperationIdentifier"
                }
                if (functionOrOperationIdentifier.name in interpretationContext.variables) {
                    val function = interpretationContext.variables[functionOrOperationIdentifier.name]
                    check (function is LispFunction) {
                        "Function name expected, but got $function"
                    }
                    function.eval(evalList(sExpression.right, interpretationContext), interpretationContext)
                } else {
                    val operation = operationRegistry[functionOrOperationIdentifier.name]
                        ?: error {
                            "Can't find either operation or function with name ${functionOrOperationIdentifier.name}"
                        }
                    operation.perform(interpretationContext, sExpression.right)
                }
            }

            is LispAtom -> {
                interpretationContext.variables[sExpression.name]
                    ?: error("Atom ${sExpression.name} not found in the context")
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