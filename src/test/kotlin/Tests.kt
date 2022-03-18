import org.junit.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals

class Tests {
    @Test
    fun humanReadableByteCountSI() {
        assertEquals("1.0 kB", humanReadableByteCountSI(1000))
        assertEquals("999 B", humanReadableByteCountSI(999))
        assertEquals("987.7 MB", humanReadableByteCountSI(987654321))
    }
}