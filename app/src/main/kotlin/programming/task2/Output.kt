package programming.task2

import com.github.ajalt.clikt.output.TermUi.echo
import java.io.File

class Output(
    private val directory: File,
    private val long: Boolean = false,
    private val human: Boolean = false,
    private val reverse: Boolean = false
) {
    private var fileList = directory.listFiles()
    init {
        val array = directory.listFiles() ?: arrayOf(directory)
        if (reverse) array.reversedArray()
        fileList = array
    }

    fun create(): List<String> {
        if (!directory.exists()) return listOf("File/folder is not found")
        val result = mutableListOf<String>()
        if (long) {
            for (file in fileList) {
                result.add(LongFormat(file.toString()).getLong(file, human) + " " + file.name)
            }
        } else
            for (file in fileList) {
                result.add((file.name))
        }
        return result.toList()
    }

    fun print(): String {
        val list = Output(directory, long, human, reverse).create()
        for (line in list) {
            echo(line)
        }
        return ""
    }

    fun fileOutput(fileName: String) {
        val writer = File(fileName).bufferedWriter()
        val output = Output(directory, long, human, reverse).create()
        for (line in output) {
            writer.write(line)
            writer.newLine()
        }
        writer.close()
    }

}