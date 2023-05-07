package snma.lisp_interpreter.model.eval

import snma.lisp_interpreter.model.*

class LispUserFunction(
    argAtoms: SExpression,
    private val expression: SExpression,
) : LispFunction {
    private val atomNamesList = if (argAtoms is LispNull) emptyList() else {
        check(argAtoms is LispPair) { "Args names must be a list or an empty list" }
        argAtoms
            .toJvmList()
            .map {
                val atom = it as? LispAtom
                atom ?: error("Function parameter must be an atom")
                atom.name
            }
    }

    override fun eval(args: SExpression, context: InterpretationContext): SExpression {
        val computedArgs = if (args is LispNull) {
            emptyList()
        } else {
            check(args is LispPair) { "Function expects a list of arguments or an empty list" }
            args.toJvmList()
        }
        check(atomNamesList.size == computedArgs.size) {
            "Function expects ${atomNamesList.size} args, but ${computedArgs.size} are provided"
        }
        val mutatedContext = context.mutate { map ->
            atomNamesList.zip(computedArgs).forEach { (name, value) ->
                map[name] = value
            }
        }
        return mutatedContext.eval(expression)
    }
}

fun LispPair.toJvmList(): List<SExpression> {
    val list = mutableListOf(left)
    var ptr = right
    while (ptr !== LispNull) {
        check (ptr is LispPair) { "Unexpected item in list: $ptr" }
        list.add(ptr.left)
        ptr = ptr.right
    }
    return list
}
