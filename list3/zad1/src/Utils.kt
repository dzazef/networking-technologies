import crc.CRC16
import java.util.zip.CRC32
import java.util.zip.Checksum

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

fun getCRC16(message: String): String {
    val crc16Binary = Integer.toBinaryString(CRC16().also { it.update(message.toByteArray()) }.value.toInt())
    return "${"0".repeat(16-crc16Binary.length)}$crc16Binary"
}
fun getCRC32(message: String): String {
    val crc32Binary = Integer.toBinaryString(CRC32().also { it.update(message.toByteArray()) }.value.toInt())
    return "${"0".repeat(32-crc32Binary.length)}$crc32Binary"
}