import com.github.ajalt.clikt.core.BadParameterValue
import com.github.ajalt.clikt.core.IncorrectOptionValueCount
import com.github.ajalt.clikt.core.NoSuchOption
import com.github.ajalt.clikt.core.PrintHelpMessage
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.test.assertContains
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith


class Tests {
    @Test
    fun humanReadableByteCountSI() {
        assertEquals("1.0 kB", humanReadableByteCountSI(1000))
        assertEquals("999 B", humanReadableByteCountSI(999))
        assertEquals("987.7 MB", humanReadableByteCountSI(987654321))
    }
    private fun prepare() {
        val testPath = Files.createDirectory(Paths.get("./build/tempTest"))

        Files.createFile(Paths.get("${testPath.toAbsolutePath()}/1.txt"))
        for (i in 1..100)
            File("./build/tempTest/1.txt").appendText("TEST EXAMPLE FILE")
        Files.createFile(Paths.get("${testPath.toAbsolutePath()}/empty.txt"))

        Files.createDirectory(Paths.get("${testPath.toAbsolutePath()}/test"))
    }

    private fun checkSize() {
        assertEquals("1.7 kB", LSFile("./build/tempTest/1.txt").displaySize(true))
        assertEquals("1700 B", LSFile("./build/tempTest/1.txt").displaySize(false))
    }

    private fun permissionsFallback() {
        assertEquals("-rw-", LSFile("./build/tempTest/1.txt").windowsPermissions(false))
        assertEquals("600", LSFile("./build/tempTest/1.txt").windowsPermissions(true))
    }

    private fun displayPermissions() {
        val permission = LSFile("./build/tempTest/1.txt").displayPermissions()
        assertContains(permission, "-rw")
    }

    private fun checkEmptyFolder() {
        val folder = LS(Paths.get("./build/tempTest/test"))
        assertContentEquals(emptyArray(), folder.files)
        assertContentEquals(emptyList(), folder.generateOutput(false))
    }

    private fun checkFolder() {
        val folder = LS(Paths.get("./build/tempTest"))
        assertEquals("empty.txt, test, 1.txt", folder.generateOutput(false).joinToString())
    }

    private fun checkFile() {
        val file = LS(Paths.get("./build/tempTest/1.txt"))
        assertEquals(listOf("1.txt"), file.generateOutput(false))
        val fileL = LS(Paths.get("./build/tempTest/1.txt"), isLong = true)
        val output = fileL.generateOutput(false).joinToString()
        assertContains(output,"-rw")
        assertContains(output, "1700 B")
        assertContains(output,"1.txt")
    }

    private fun checkEmptyFile() {
        val fileL = LS(Paths.get("./build/tempTest/empty.txt"), isLong = true)
        val output = fileL.generateOutput(false).joinToString()
        assertContains(output, "0 B")
    }

    private fun remove() {
        Files.walk(Paths.get("./build/tempTest"))
            .sorted(Comparator.reverseOrder())
            .map(Path::toFile)
            .forEach(File::delete)
    }


    @Test
    fun main() {
        prepare() // Create files for testing
        checkSize() // Check exact size of file
        permissionsFallback() // Check permissions (use only builtin kotlin functions)
        displayPermissions() // Check string permissions format
        checkEmptyFolder()
        checkFile()
        checkFolder()
        checkEmptyFile()
        remove() // Cleanup
    }
    @Test
    fun checkArgs() {
        assertFailsWith<NoSuchOption>{mainWithoutLibraryExceptionHandler(arrayOf("--p"))} // Incorrect option
        assertFailsWith<BadParameterValue>{mainWithoutLibraryExceptionHandler(arrayOf("-l", "oooo"))} // Incorrect path + correct option
        assertFailsWith<PrintHelpMessage>{mainWithoutLibraryExceptionHandler(arrayOf("--help"))} // Exception to print help message
        assertFailsWith<IncorrectOptionValueCount>{mainWithoutLibraryExceptionHandler(arrayOf("-o"))} // No path to output file
        assertFailsWith<BadParameterValue> {mainWithoutLibraryExceptionHandler(arrayOf("./testt"))} // No directory test
    }
    @Test
    // Incorrect output file path
    fun checkOutputFile() {
        val stream = ByteArrayOutputStream()
        val ps = PrintStream(stream)
        System.setOut(ps)
        mainWithoutLibraryExceptionHandler(arrayOf("-o ./testt"))
        val output = String(stream.toByteArray())
        assertContains(output, "No such file or directory")
    }
}