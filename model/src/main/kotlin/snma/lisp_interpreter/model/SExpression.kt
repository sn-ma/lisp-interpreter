package snma.lisp_interpreter.model

import snma.lisp_interpreter.model.eval.InterpretationContext
import java.math.BigDecimal

sealed interface SExpression

object LispNull : SExpression

data class LispAtom (
    val name: String,
) : SExpression

data class LispString (
    val value: String,
) : SExpression

data class LispNumber (
    val value: BigDecimal
) : SExpression

data class LispPair (
    val left: SExpression,
    val right: SExpression,
): SExpression

interface LispFunction : SExpression {
    fun eval(
        args: SExpression,
        context: InterpretationContext
    ): SExpression
}
