package snma.lisp_interpreter.model.eval.operation_registries_impl

import snma.lisp_interpreter.model.eval.OperationRegistryImpl
import snma.lisp_interpreter.model.parser.*
import java.math.BigDecimal

private val SumOperation = reduceOperation(LispNumber(BigDecimal.ZERO)) { a, b ->
    LispNumber(a.value + b.value)
}

private val MulOperation = reduceOperation(LispNumber(BigDecimal.ONE)) { a, b ->
    LispNumber(a.value * b.value)
}

val ArithmeticOperationRegistry = OperationRegistryImpl(mapOf(
    "+" to SumOperation,
    "*" to MulOperation,
))