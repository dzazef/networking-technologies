package readers

import java.io.File

class FileReader(private val fileName: String) : Reader {
    override fun readList(): List<String> {
        return File(fileName).readLines()
    }

    override fun readString(): String {
        return File(fileName).readLines().joinToString(separator = "")
    }
}