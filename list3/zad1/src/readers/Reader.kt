package readers

interface Reader {
    fun readString(): String
    fun readList(): List<String>
}