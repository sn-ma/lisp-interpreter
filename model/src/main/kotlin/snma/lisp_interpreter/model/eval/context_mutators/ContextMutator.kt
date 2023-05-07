package snma.lisp_interpreter.model.eval.context_mutators

import snma.lisp_interpreter.model.SExpression

fun interface ContextMutator : (MutableMap<String, SExpression>) -> Unit