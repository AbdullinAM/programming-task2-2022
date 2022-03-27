import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.PosixFilePermissions
import java.text.CharacterIterator
import java.text.SimpleDateFormat
import java.text.StringCharacterIterator
import java.util.*

class Command : CliktCommand() {
    private val path: Path by argument("Path", "Path to folder").path(canBeFile = true, mustExist = true).default(Paths.get("").toAbsolutePath())
    private val output by option("-o", help = "Output file name")
    private val long: Boolean by option("-l", help = "Turn on long mode").flag()
    private val human: Boolean by option("-h", help = "Turn on human-readable mode").flag()
    private val reverse: Boolean by option("-r", help = "Reverse files list").flag()
    override fun run() {
        val ls = LS(path, long, human, reverse)
        val strings = ls.generateOutput(output.isNullOrEmpty())
        produce(strings, output)
    }
}


fun mainWithoutLibraryExceptionHandler(args: Array<String>) = Command().parse(args)

fun main(args: Array<String>) = Command().main(args)