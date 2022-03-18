import org.junit.Test
import java.io.File
import java.nio.file.Files
import java.nio.file.Files.createDirectory
import java.nio.file.Path
import java.nio.file.Paths
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Tests {
    @Test
    fun humanReadableByteCountSI() {
        assertEquals("1.0 kB", humanReadableByteCountSI(1000))
        assertEquals("999 B", humanReadableByteCountSI(999))
        assertEquals("987.7 MB", humanReadableByteCountSI(987654321))
    }
    fun prepare() {
        val testPath = Files.createDirectory(Paths.get("./build/tempTest"))

        Files.createFile(Paths.get("${testPath.toAbsolutePath()}/1.txt"))
        for (i in 1..100)
            File("./build/tempTest/1.txt").appendText("TEST EXAMPLE FILE")

        Files.createDirectory(Paths.get("${testPath.toAbsolutePath()}/test"))
    }

    fun checkSize() {
        assertEquals("1.7 kB", displaySize(File("./build/tempTest/1.txt"), true))
        assertEquals("1700 B", displaySize(File("./build/tempTest/1.txt"), false))
    }

    fun permissionsFallback() {
        assertEquals("-rw-", permissionsFallback(File("./build/tempTest/1.txt"), false))
        assertEquals("600", permissionsFallback(File("./build/tempTest/1.txt"), true))
    }

    fun displayPermissions(){
        var permission = displayPermissions(File("./build/tempTest/1.txt"))
        assertTrue("-rw-" in permission)
    }

    fun remove() {
        Files.walk(Paths.get("./build/tempTest"))
            .sorted(Comparator.reverseOrder())
            .map(Path::toFile)
            .forEach(File::delete);
    }


    @Test
    fun main() {
        prepare()
        checkSize()
        permissionsFallback()
        displayPermissions()
        remove()
    }
}