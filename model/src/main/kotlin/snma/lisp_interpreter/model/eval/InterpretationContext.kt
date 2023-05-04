package snma.lisp_interpreter.model.eval

import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.persistentHashMapOf
import snma.lisp_interpreter.model.parser.SExpression

class InterpretationContext (
    val interpreter: Interpreter,
    val variables: PersistentMap<String, SExpression> = persistentHashMapOf(),
) {
    fun mutate(mutator: (MutableMap<String, SExpression>) -> Unit) = InterpretationContext(
        interpreter = interpreter,
        variables = variables.mutate(mutator)
    )
}