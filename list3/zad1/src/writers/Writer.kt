package writers

interface Writer {
    fun write(stringList : List<String>)
    fun write(string : String)
}