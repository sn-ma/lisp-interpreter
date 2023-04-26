package snma.lisp_interpreter.command_line_runner

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LispInterpreterApplication(
) : CommandLineRunner {
	private val logger = LoggerFactory.getLogger(LispInterpreterApplication::class.java)

	override fun run(vararg args: String?) {
		logger.info("EXECUTING : command line runner")
		for (i in args.indices) {
			logger.info("args[{}]: {}", i, args[i])
		}
	}
}

fun main(args: Array<String>) {
	runApplication<LispInterpreterApplication>(*args)
}
