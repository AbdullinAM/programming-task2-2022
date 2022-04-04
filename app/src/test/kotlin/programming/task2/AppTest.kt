package programming.task2

import java.io.File
import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals


class AppTest {
    private val directoryTest = Paths.get("src/test/resources").toAbsolutePath().toString()
    private fun assertFileContent(name: String, expectedContent: String) {
        val file = File(name)
        val content = file.readLines().joinToString("\n")
        assertEquals(expectedContent, content)
    }
    @Test
    fun getPermission() {
        assertEquals(
            "r-x",
            LongFormat("${directoryTest}/onlyRead.txt").getPermission(true)
        )
        assertEquals(
            "rwx",
            LongFormat(
                "${directoryTest}/kotlin.jpg").getPermission(true)
        )
        assertEquals(
            "500",
            LongFormat("${directoryTest}/onlyRead.txt").getPermission(false)
        )
        assertEquals(
            "700",
            LongFormat(
                "${directoryTest}/kotlin.jpg").getPermission(false)
        )
    }

    @Test
    fun humanReadableByteCountSI() {
        assertEquals("5,0 kB", LongFormat("").humanReadableByteCountSI(5000))
        assertEquals("1,3 MB", LongFormat("").humanReadableByteCountSI(1300000))
        assertEquals("2,2 GB", LongFormat("").humanReadableByteCountSI(2200000000))
    }

    @Test
    fun lastModifiedTime() {
        assertEquals(
            "Sun Apr 03 17:31:26 MSK 2022",
            LongFormat("${directoryTest}/emptyFolder").lastModifiedTime()
        )
        assertEquals(
            "Sun Sep 12 22:36:52 MSK 2021",
            LongFormat("${directoryTest}/substrings_in1.txt").lastModifiedTime()
        )
    }

    @Test
    fun size() {
        assertEquals("6,4 kB", LongFormat("${directoryTest}/kotlin.jpg").size(true))
        assertEquals("1336 B", LongFormat("${directoryTest}/substrings_in1.txt").size(false))
    }

    @Test
    fun output() {
        assertEquals(listOf("File/folder is not found"), Output(File("${directoryTest}/test")).create())
        assertEquals(listOf(
            "1.txt",
            "2.txt",
            "3.txt"
        ), Output(File("${directoryTest}/testFolder")).create())
        Output(File("${directoryTest}/testFolder")).fileOutput("${directoryTest}/output.txt")
        assertFileContent("${directoryTest}/output.txt",
        """1.txt
            |2.txt
            |3.txt
        """.trimMargin())
    }
}
