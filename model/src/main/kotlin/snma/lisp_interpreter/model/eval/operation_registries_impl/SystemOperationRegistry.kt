package snma.lisp_interpreter.model.eval.operation_registries_impl

import snma.lisp_interpreter.model.eval.InterpretationContext
import snma.lisp_interpreter.model.eval.Operation
import snma.lisp_interpreter.model.eval.OperationRegistryImpl
import snma.lisp_interpreter.model.parser.LispAtom
import snma.lisp_interpreter.model.parser.LispNull
import snma.lisp_interpreter.model.parser.LispPair
import snma.lisp_interpreter.model.parser.SExpression

private val LetOperation = object : Operation {
    override fun perform(context: InterpretationContext, sExpression: SExpression): SExpression {
        check(sExpression is LispPair) { "Argument of let must be a list" }
        check(sExpression.right is LispPair) { "Argument of let must be a list with two elements, but got 1" }
        check(sExpression.right.right === LispNull) { "Argument of let must be a list with two elements, but got more" }
        val modifiedContext = modifyContext(context, sExpression.left)
        val expressionToEvaluate = sExpression.right.left
        return modifiedContext.interpreter.eval(expressionToEvaluate, modifiedContext)
    }

    private fun modifyContext(
        context: InterpretationContext,
        sExpression: SExpression,
    ) : InterpretationContext  = when (sExpression) {
        LispNull -> context
        is LispPair -> {
            val someVariableAndValue = sExpression.left
            check(someVariableAndValue is LispPair) { "Variable and value pair is expected, but got $someVariableAndValue" }
            val variable = someVariableAndValue.left
            check(variable is LispAtom) { "Variable must be some valid variable name, but it is $variable" }
            val tail = someVariableAndValue.right
            check (tail is LispPair) { "Value for variable should be listed after it, instead get $tail" }
            check (tail.right === LispNull) { "Got more than one value for a ${variable.name}" }
            val value = context.interpreter.eval(tail.left)
            val newContext = context.mutate {
                it[variable.name] = value
            }
            modifyContext(newContext, sExpression.right)
        }
        else -> error("Context must contain a list of value and variable pairs, instead got $sExpression")
    }
}

val SystemOperationRegistry = OperationRegistryImpl(mapOf(
    "let" to LetOperation,
))