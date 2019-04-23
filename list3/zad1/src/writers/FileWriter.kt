package writers

import java.io.File

class FileWriter(private val fileName : String) : Writer {
    override fun write(string: String) {
        File(fileName).printWriter().use { out -> out.println(string)}
    }

    override fun write(stringList: List<String>) {
        File(fileName).printWriter().use { out -> stringList.forEach { out.println(it) } }
    }
}