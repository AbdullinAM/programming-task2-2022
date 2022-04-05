package programming.task2

import java.io.File
import java.text.CharacterIterator
import java.text.StringCharacterIterator
import java.util.*

class LongFormat(private val filePath: String): File(filePath) {
    private val file = File(filePath)
    fun getPermission(human: Boolean): String {
        val resultSymbolic = StringBuilder()
        var resultNumeric = 0
        if (file.canRead()) {
            resultSymbolic.append('r')
            resultNumeric += 4
        }
        else
            resultSymbolic.append('-')
        if (file.canWrite()) {
            resultSymbolic.append('w')
            resultNumeric += 2
        }
        else
            resultSymbolic.append('-')
        if (file.canExecute()) {
            resultSymbolic.append('x')
            resultNumeric += 1
        }
        else
            resultSymbolic.append('-')
        if (human) return  resultSymbolic.toString()
        return "${resultNumeric}00"
    }

    //https://stackoverflow.com/questions/3758606/how-can-i-convert-byte-size-into-a-human-readable-format-in-java
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
        return "${bytes / 1000.0} ${ci.current()}B"
    }

    fun lastModifiedTime(): String {
        return Date(file.lastModified()).toString()
    }

    fun size(human: Boolean): String {
        if (file.isDirectory) return "folder"
        if (human) return humanReadableByteCountSI(file.length())
        return (file.length()).toString() + " B"
    }

    fun getLong(human: Boolean): String {
        val result = StringBuilder()
        if (human)
            result.append(getPermission(true))
        else result.append(getPermission(false))
        result.append(" ")
        result.append(lastModifiedTime())
        result.append(" ")
        if (human)
            result.append(size(true))
        else
            result.append(size(false))
        return result.toString()
    }
}