package snma.lisp_interpreter.model.parser

import java.math.BigDecimal

interface SExpression

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