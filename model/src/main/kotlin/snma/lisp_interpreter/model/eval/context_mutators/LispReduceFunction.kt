package snma.lisp_interpreter.model.eval.context_mutators

import snma.lisp_interpreter.model.LispFunction
import snma.lisp_interpreter.model.LispNull
import snma.lisp_interpreter.model.LispPair
import snma.lisp_interpreter.model.SExpression
import snma.lisp_interpreter.model.eval.InterpretationContext
import kotlin.reflect.KClass

inline fun <reified T: SExpression> lispReduceFunction(
    default: T,
    noinline reduce: (T, T) -> T,
): LispFunction = LispReduceFunction(default, reduce, T::class)

class LispReduceFunction<T : SExpression> (
    private val default: T,
    private val reduce: (T, T) -> T,
    private val type: KClass<T>,
): LispFunction {
    override fun eval(args: SExpression, context: InterpretationContext): T {
        var result = default
        var ptr = args
        while (ptr is LispPair) {
            @Suppress("UNCHECKED_CAST")
            result = reduce(result, (ptr.left as? T) ?: error("Wrong type of ${ptr.left}: $type expected"))
            ptr = ptr.right
        }
        check (ptr === LispNull) {
            "Argument list is $ptr, not a list"
        }
        return result
    }
}