package snma.lisp_interpreter.model.eval.operation_registries_impl

import snma.lisp_interpreter.model.eval.InterpretationContext
import snma.lisp_interpreter.model.eval.Operation
import snma.lisp_interpreter.model.parser.LispAtom
import snma.lisp_interpreter.model.parser.LispNull
import snma.lisp_interpreter.model.parser.LispPair
import snma.lisp_interpreter.model.parser.SExpression
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

inline fun <reified T: SExpression> reduceOperation(
    default: T,
    noinline reduce: (T, T) -> T,
) = ReduceOperation(
    default = default,
    reduce = reduce,
    returnType = T::class,
)

class ReduceOperation<T: SExpression>(
    private val default: T,
    private val reduce: (T, T) -> T,
    private val returnType: KClass<T>,
) : Operation {
    @Suppress("UNCHECKED_CAST")
    override fun perform(context: InterpretationContext, sExpression: SExpression): T = when {
        sExpression === LispNull -> default
        sExpression::class.isSubclassOf(returnType) -> sExpression as T
        sExpression is LispAtom -> {
            val value = context.variables[sExpression.name]
            check (value != null) { "Variable ${sExpression.name} not found in the context" }
            check (value::class.isSubclassOf(returnType)) { "Variable ${sExpression.name} is $value, but must be a $returnType"}
            value as T
        }
        sExpression is LispPair -> {
            val left = context.interpreter.eval(sExpression.left, context)
            check (returnType.isInstance(left)) { "$returnType expected, got $left" }
            val right = perform(context, sExpression.right)
            reduce(left as T, right)
        }
        else -> error("Expression of type ${sExpression::class} isn't supported")
    }
}