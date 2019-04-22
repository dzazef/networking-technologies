import crc.CRC16

//UTILS
fun String.removeFirst(): String {
//        System.err.println("removeFirst")
    return this.substring(1, length)
}
fun String.substringIndex(sub : String) : Int? {
//        System.err.println("substringIndex")
    for (i in 0..(length - sub.length)) {
        if (substring(i, i+sub.length) == sub)
            return i
    }
    return null
}
fun String.substringIndex(sub : String, beginIdx: Int) : Int? {
//        System.err.println("substringIndex")
    for (i in beginIdx..(length - sub.length)) {
        if (substring(i, i+sub.length) == sub)
            return i
    }
    return null
}

fun getCRC(message: String): String {
    val crc16Binary = Integer.toBinaryString(CRC16().also { it.update(message.toByteArray()) }.value.toInt())
    return "${"0".repeat(CRC_SIZE-crc16Binary.length)}$crc16Binary"
}