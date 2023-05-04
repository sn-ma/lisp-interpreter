package snma.lisp_interpreter.model.eval

import snma.lisp_interpreter.model.parser.LispAtom
import snma.lisp_interpreter.model.parser.LispPair
import snma.lisp_interpreter.model.parser.SExpression

class Interpreter (
    private val operationRegistry: OperationRegistry,
) {
    fun eval(sExpression: SExpression, interpretationContext: InterpretationContext = InterpretationContext(this)): SExpression =
        if (sExpression is LispPair) {
            val functionIdentifier = eval(sExpression.left)
            check(functionIdentifier is LispAtom)
            val operation = operationRegistry[functionIdentifier.name]
                ?: error("Function $functionIdentifier.name not found")
            operation.perform(interpretationContext, sExpression.right)
        } else {
            sExpression
        }
}