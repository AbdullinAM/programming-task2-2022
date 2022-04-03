package programming.task2

import com.github.ajalt.clikt.core.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class Tests {

    @Test
    fun findCaseInsensitive() {
        val grep = Grep()
        val expected = listOf("ВОЛКИ [2]",
            "Дядя Семён ехал из города домой. С ним была собака Жучка, Вдруг из леса выскочили волки.",
            "Деревня была близко. Показались огни в окнах. Волки отстали.")
        val actual = grep.find(
            v = false,
            i = true,
            r = false,
            regex = "ВОЛКИ",
            filename = "D:/programming-task2-2022/src/test/resources/input.txt"
        )
        assertEquals(expected, actual)
    }

    @Test
    fun findPattern() {
        val grep = Grep()
        val expected = listOf("ОРЁЛ И КОШКА [1]",
            "ВОЛКИ [2]",
            "МУРАВЬИ [3]", "СИМВОЛЬНЫЕ ЗНАКИ И REGEXP[4]", "Также можно искать цифры, как определенные: [12569], так и диапазон: [0-9], [3-7].")
        val actual = grep.find(
            v = false,
            i = true,
            r = true,
            regex = "[1-7]+",
            filename = "D:/programming-task2-2022/src/test/resources/input.txt"
        )
        assertEquals(expected, actual)
    }

    @Test
    fun findInvert() {
        val grep = Grep()
        val expected = listOf("В траве сидел кузнечик,",
            "В траве сидел кузнечик,",
            "Совсем как огуречик,", "Зелененький он был.", "Совсем как огуречик.", "Зелененький он был.")
        val actual = grep.find(
            v = true,
            i = true,
            r = false,
            regex = "представьте",
            filename = "D:/programming-task2-2022/src/test/resources/input1.txt"
        )
        assertEquals(expected, actual)
    }

    @Test
    fun findInvert1() {
        val grep = Grep()
        val expected = listOf("Зелененький он был.",
            "Совсем как огуречик.",
            "Зелененький он был.")
        val actual = grep.find(
            v = true,
            i = false,
            r = true,
            regex = "[а-я]+\\,",
            filename = "D:/programming-task2-2022/src/test/resources/input1.txt"
        )
        assertEquals(expected, actual)
    }

    @Test
    fun testErrors() {
        assertFailsWith<NoSuchOption> { main(arrayOf("-k", "word", "D:/programming-task2-2022/src/test/resources/input.txt")) }
        assertFailsWith<BadParameterValue> { main(arrayOf("-r", "-i", "word", "D:/programming-task2-2022/src/test/resources/NoInput.txt")) }
        assertFailsWith<MissingArgument> { main(arrayOf("-r", "-i", "word")) }
        assertFailsWith<MissingArgument> { main(arrayOf("-r", "-i", "D:/programming-task2-2022/src/test/resources/input.txt")) }
        assertFailsWith<PrintHelpMessage> { main(arrayOf("--help")) }
        assertFailsWith<PrintHelpMessage> { main(arrayOf("-h")) }
        assertFailsWith<UsageError> { main(arrayOf("word", "D:/programming-task2-2022/src/test/resources/input.txt", "D:/programming-task2-2022/src/test/resources/input.txt")) }
        assertFailsWith<BadParameterValue> { main(arrayOf("word", "D:/programming-task2-2022/src/TESACSA/resources/input.txt")) }
    }
}
