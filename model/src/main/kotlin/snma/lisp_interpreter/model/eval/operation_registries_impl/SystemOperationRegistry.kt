package snma.lisp_interpreter.model.eval.operation_registries_impl

import snma.lisp_interpreter.model.eval.OperationRegistryImpl

val SystemOperationRegistry = OperationRegistryImpl(mapOf(
    "let" to letOperation,
    "lambda" to lambdaOperation,
))