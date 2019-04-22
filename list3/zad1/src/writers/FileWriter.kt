package writers

import java.io.File

class FileWriter(private val fileName : String) : Writer {
    override fun write(stringList: List<String>) {
        stringList.forEach { File(fileName).writeText(it) }
    }
}