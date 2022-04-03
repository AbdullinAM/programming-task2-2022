package programming.task2

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.file



class Tool: CliktCommand() {
    private val directory by argument(help = "Directory or file").file()
    private val output by option("-o", help = "Name of output file")
    private val long: Boolean by option("-l", help = "Enables long format").flag()
    private val human: Boolean by option("-h", help = "Enables human-readable format").flag()
    private val reverse: Boolean by option("-r", help = "Reverse the order of files").flag()
    override fun run() {
        if (!output.isNullOrEmpty()) {
            Output(directory, long, human, reverse).fileOutput(output!!)
        } else
            echo(Output(directory, long, human, reverse).print())
    }
}

fun main(args: Array<String>) = Tool().main(args)
