package readers

import java.io.File

class FileReader(private val fileName: String) : Reader {
    override fun read(): String {
        return File(fileName).readLines().joinToString(separator = "")
    }
}