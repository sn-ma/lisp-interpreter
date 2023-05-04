package snma.lisp_interpreter.model.eval

interface OperationRegistry {
    operator fun get(name: String): Operation?
}

class OperationRegistryImpl(
    private val operationMap: Map<String, Operation>
): OperationRegistry {
    override operator fun get(name: String) = operationMap[name]
}

class OperationRegistryBundle(
    private val registries: Set<OperationRegistry>
): OperationRegistry {
    override fun get(name: String) = registries.firstNotNullOfOrNull { it[name] }
}
