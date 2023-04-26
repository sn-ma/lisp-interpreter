package snma.lisp_interpreter.command_line_runner

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import snma.lisp_interpreter.model.Model

@Configuration
class CliConfiguration {
    @Bean
    fun model() = Model()
}