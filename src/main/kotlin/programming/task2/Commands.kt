package programming.task2

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.clikt.parameters.types.path
import java.io.File
import java.nio.file.Path

class Commands: CliktCommand() {
    private val word: String by argument("Word or phrase", "Word or phrase to search")
    private val path: Path by argument("File", "Path to folder").path(mustExist = true)
    private val regex: Boolean by option("-r", help = "Write phrase to search").flag()
    private val invert: Boolean by option("-v", help = "Search string where is not your word").flag()
    private val ignore: Boolean by option("-i", help = "Ignore case").flag()
    override fun run() {
        val grep = Grep()
        grep.find(invert, ignore, regex, word, path.toString())
    }
}