import java.io.File
import java.nio.file.Path

class LS (
    private val path: Path,
    private val isLong: Boolean = false,
    private val isHuman: Boolean = false,
    private val isReversed: Boolean = false
) {
    public val absolutePath = path.toString()
    public val files: Array<File>

    init {
        val filesArray = File(absolutePath).listFiles() ?: arrayOf(File(absolutePath))
        files = if (isReversed) filesArray.reversedArray()
        else filesArray
    }

    fun generateOutput(isConsole: Boolean): List<String> {
        val strings = mutableListOf<String>()
        for (file in files) {
            val lsFile = LSFile(file.absolutePath)
            strings.add(lsFile.displayMeta(isLong, isHuman, isConsole))
        }
        return strings
    }
}