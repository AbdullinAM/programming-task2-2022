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

// Get unix-ish permissions in windows
fun permissionsFallback(file: File, numeric: Boolean? = false): String {
    val permissions = StringBuilder("----")
    var counter = 0
    if (file.isDirectory) {
        permissions[0] = 'd'
    }
    if (file.canRead()) {
        permissions[1] = 'r'
        counter += 4
    }
    if (file.canWrite()) {
        permissions[2] = 'w'
        counter += 2
    }
    if (file.canExecute()) {
        permissions[3] = 'x'
        counter += 1
    }
    if (numeric == true) {
        return "${counter}00"
    }
    return permissions.toString()
}


fun displayPermissions(file: File, numeric: Boolean? = false): String {
    try {
        val permissions: String = PosixFilePermissions.toString(Files.getPosixFilePermissions(file.toPath()))
        if (numeric == true) {
            val result = StringBuilder()
            var current = 0
            for (i in permissions.indices) {
                if (permissions[i] == 'r') {
                    current += 4
                }
                if (permissions[i] == 'w') {
                    current += 2
                }
                if (permissions[i] == 'x') {
                    current += 1
                }
                if ((i + 1) % 3 == 0) {
                    result.append(current)
                    current = 0
                }
            }
            return result.toString()
        }
        return if (file.isDirectory) {
            "d$permissions"
        } else {
            "-$permissions"
        }
    } catch (e: java.lang.UnsupportedOperationException) {
        return permissionsFallback(file, numeric)
    }
}

fun formatTime(timestamp: Long): String {
    val date = Date(timestamp)
    val dateFormatter = SimpleDateFormat("MMM dd HH:mm")
    return dateFormatter.format(date)
}

fun humanReadableByteCountSI(byte: Long): String {
    var bytes = byte
    if (-1000 < bytes && bytes < 1000) {
        return "$bytes B"
    }
    val ci: CharacterIterator = StringCharacterIterator("kMGTPE")
    while (bytes <= -999950 || bytes >= 999950) {
        bytes /= 1000
        ci.next()
    }
    return String.format("%.1f %cB", bytes / 1000.0, ci.current())
}

fun displaySize(file: File, isHuman: Boolean): String {
    if (isHuman) {
        return humanReadableByteCountSI(file.length())
    }
    return "${file.length()} B"
}


fun displayMeta(file: File, isLong: Boolean, isHuman: Boolean, isConsole: Boolean): String {
    val result = StringBuilder()
    if (isLong) {
        result.append("${displayPermissions(file, isHuman)} ")
        result.append("${formatTime(file.lastModified())} ")
    }
    // Print the name of directory red
    if (file.isDirectory && isConsole)
        result.append("\u001b[31m${file.name}\u001b[0m")
    else
        result.append(file.name)
    if (!file.isDirectory && isLong) {
        result.append(" ${displaySize(file, isHuman)}")
    }
    return result.toString()
}

fun generateOutput(path: String, isLong: Boolean, isHuman: Boolean, isReversed: Boolean, isConsole: Boolean): List<String> {
    var files = File(path).listFiles()?.sorted()
    if (files === null) return listOf("")
    if (isReversed)
        files = files.reversed()
    val strings = mutableListOf<String>()
    for (file in files) {
        strings.add(displayMeta(file, isLong, isHuman, isConsole))
    }
    return strings
}

fun produce(result: List<String>, outputFileName: String?) {
    if (!outputFileName.isNullOrEmpty()) {
        try {
            File(Paths.get(outputFileName).toAbsolutePath().toString()).writeText(result.joinToString("\n"))
        } catch (e: FileNotFoundException) {
            println("Error: ${e.localizedMessage}")
        }
    } else {
        for (string in result) {
            println(string)
        }
    }
}


class LS : CliktCommand() {
    private val path: Path by argument("Path", "Path to folder").path(canBeFile = false).default(Paths.get("").toAbsolutePath())
    private val output by option("-o", help = "Output file name")
    private val long: Boolean by option("-l", help = "Turn on long mode").flag()
    private val human: Boolean by option("-h", help = "Turn on human-readable mode").flag()
    private val reverse: Boolean by option("-r", help = "Reverse files list").flag()
    override fun run() {
        val absolutePath = path.toString()
        val strings = generateOutput(
            absolutePath,
            long,
            human,
            reverse,
            output.isNullOrEmpty()
        )
        produce(strings, output)
    }
}


fun main(args: Array<String>) = LS().main(args)