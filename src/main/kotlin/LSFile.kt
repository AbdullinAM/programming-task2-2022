import java.io.File
import java.nio.file.Files
import java.nio.file.attribute.PosixFilePermissions

class LSFile(private val pathToFile: String) : File(pathToFile) {
    fun displaySize(isHuman: Boolean): String =
        if (isHuman) humanReadableByteCountSI(this.length())
        else "${this.length()} B"

    fun displayMeta(isLong: Boolean, isHuman: Boolean, isConsole: Boolean): String {
        val result = StringBuilder()
        if (isLong) {
            result.append("${displayPermissions(isHuman)} ")
            result.append("${formatTime(this.lastModified())} ")
        }
        // Print the name of directory red if we're printing into console
        if (this.isDirectory && isConsole)
            result.append("\u001b[31m${this.name}\u001b[0m")
        else
            result.append(this.name)
        if (!this.isDirectory && isLong) {
            result.append(" ${displaySize(isHuman)}")
        }
        return result.toString()
    }

    // Get unix-ish permissions in windows
    fun windowsPermissions(numeric: Boolean = false): String {
        val permissions = StringBuilder("----")
        var counter = 0
        if (this.isDirectory) {
            permissions[0] = 'd'
        }
        if (this.canRead()) {
            permissions[1] = 'r'
            counter += 4
        }
        if (this.canWrite()) {
            permissions[2] = 'w'
            counter += 2
        }
        if (this.canExecute()) {
            permissions[3] = 'x'
            counter += 1
        }
        return if (numeric) "${counter}00"
        else permissions.toString()
    }

    fun displayPermissions(numeric: Boolean = false): String {
        try {
            val permissions: String = PosixFilePermissions.toString(Files.getPosixFilePermissions(this.toPath()))
            // Convert posix permissions to number-style
            if (numeric) {
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
            return if (this.isDirectory) {
                "d$permissions"
            } else {
                "-$permissions"
            }
        } catch (e: java.lang.UnsupportedOperationException) {
            return windowsPermissions(numeric)
        }
    }
}