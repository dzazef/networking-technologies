import crc.Coder
import crc.Decoder
import readers.FileReader
import writers.FileWriter

fun checkParameters(args: Array<String>) {
    if (args.size != 2) {
        System.err.println("No name for input file")
        System.exit(1)

    }
}

fun testAndPrintToSystemOut(args: Array<String>) {
    val message = FileReader(args[0]).readString()
    println("MESSAGE")
    println(message)
    val framedMessage = Coder().codeMessage(message)
    println("FRAMED_MESSAGE")
    framedMessage.forEach { println(it) }
    println("DEFRAMED_MESSAGE")
    println(Decoder().decodeMessage(framedMessage))
}

fun code(args: Array<String>) {
    checkParameters(args)
    val message = FileReader(args[0]).readString()
    val framedMessage = Coder().codeMessage(message)
    FileWriter(args[1]).write(framedMessage)
}

fun decode(args: Array<String>) {
    checkParameters(args)
    val frameList = FileReader(args[1]).readList()
    val message = Decoder().decodeMessage(frameList)
    FileWriter(args[0]).write(message)
}

fun main(args : Array<String>) {
    code(args)
}
