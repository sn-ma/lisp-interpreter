package snma.lisp_interpreter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LispInterpreterApplication

fun main(args: Array<String>) {
	runApplication<LispInterpreterApplication>(*args)
}
