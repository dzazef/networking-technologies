import crc.Coder
import crc.Decoder
import readers.FileReader

fun checkParameters(args: Array<String>) {
    if (args.size != 2) {
        System.err.println("No name for input file")
        System.exit(1)

    }
}

fun wrongCRCExample() {

}

fun main(args : Array<String>) {
//    println("0111110".replace("011111", "0111110").replace("0111110", "011111"))
    checkParameters(args)
    val message = FileReader(args[0]).read()
    println("MESSAGE")
    println(message)
    val framedMessage = Coder().codeMessage(message)
    println("FRAMED_MESSAGE")
    framedMessage.forEach { println(it) }
    println("DEFRAMED_MESSAGE")
    println(Decoder().decodeMessage(framedMessage))
}