import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Paths
import java.text.CharacterIterator
import java.text.SimpleDateFormat
import java.text.StringCharacterIterator
import java.util.*

fun formatTime(timestamp: Long): String {
    val date = Date(timestamp)
    val dateFormatter = SimpleDateFormat("MMM dd HH:mm")
    return dateFormatter.format(date)
}
// Credit: https://stackoverflow.com/questions/3758606/how-can-i-convert-byte-size-into-a-human-readable-format-in-java
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

fun produce(result: List<String>, outputFile: File?) {
    if (outputFile !== null) {
        outputFile.writeText(result.joinToString("\n"))
    } else {
        for (string in result) {
            println(string)
        }
    }
}
