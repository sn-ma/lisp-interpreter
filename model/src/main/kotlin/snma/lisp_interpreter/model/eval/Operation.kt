package snma.lisp_interpreter.model.eval

import snma.lisp_interpreter.model.SExpression

fun interface Operation {
    fun perform(context: InterpretationContext, sExpression: SExpression): SExpression
}