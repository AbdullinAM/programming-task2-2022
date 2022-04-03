package programming.task2

import java.io.File
import java.io.IOException

class Grep {

    fun find(v: Boolean, i: Boolean, r: Boolean, regex: String, filename: String): List<String> {
        val result = mutableListOf<String>()
        try {
            val firstLines = File(filename).readLines()
            val word = if (r) regex else "\\Q$regex\\E"
            val p = if (i) Regex(word, RegexOption.IGNORE_CASE) else Regex(word)
            for (strings in firstLines) if (strings.contains(p) != v && strings.isNotEmpty()) result.add(strings)
        } catch (e: IOException) {
            System.err.println(e.message)
        }
        for (str in result) println(str)
        return result
    }

}