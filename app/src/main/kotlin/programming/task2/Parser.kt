package programming.task2

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.file
import java.io.File

class Parser: CliktCommand() {
    private val pack: Boolean by option("-z", help = "file packaging").flag()
    private val unpack: Boolean by option("-u", help = "file unpacking").flag()
    private val output: File by argument("-out", help = "output name").file()
    private val path: File by argument("file", help = "Path to the folder").file(mustExist = true)
    override fun run() {
        val packRLE = PackRLE()
        if (pack) packRLE.compression(path.toString())
        if (unpack) packRLE.expansion(path.toString())
        if (output)
    }
}