package snma.lisp_interpreter.command_line_runner

	import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
	import snma.lisp_interpreter.model.Model

@SpringBootApplication
class LispInterpreterApplication(
	private val model: Model,
) : CommandLineRunner {
	private val logger = LoggerFactory.getLogger(LispInterpreterApplication::class.java)

	override fun run(vararg args: String?) {
		logger.info("EXECUTING : command line runner")
		for (i in args.indices) {
			logger.info("args[{}]: {}", i, args[i])
		}
		logger.info(model.foo().toString())
	}
}

fun main(args: Array<String>) {
	runApplication<LispInterpreterApplication>(*args)
}
