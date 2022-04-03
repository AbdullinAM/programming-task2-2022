package programming.task2

import java.io.File
import java.text.CharacterIterator
import java.text.StringCharacterIterator
import java.util.*


class LongFormat(private val file: String): File(file) {
    fun getPermission(file: File, human: Boolean): String {
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
    private fun humanReadableByteCountSI(byte: Long): String {
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

    private fun lastModifiedTime(file: File): String {
        return Date(file.lastModified()).toString()
    }

    private fun size(file: File, human: Boolean): String {
        if (file.isDirectory) return "folder"
        if (human) return humanReadableByteCountSI(file.length())
        return (file.length()).toString()
    }

    fun getLong(file: File, human: Boolean): String {
        val result = StringBuilder()
        if (human)
            result.append(getPermission(file, true))
        else result.append(getPermission(file, false))
        result.append(" ")
        result.append(lastModifiedTime(file))
        result.append(" ")
        if (human)
            result.append(size(file,true))
        else {
            result.append(size(file, false))
            result.append(" bytes")
        }
        return result.toString()
    }
}