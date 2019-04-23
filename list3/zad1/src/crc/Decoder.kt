package crc

import CRC_SIZE
import TERMINATE_SEQ
import exceptions.IncorrectCRCException
import exceptions.TerminateSequenceNotFoundException
import getCRC16
import getCRC32
import substringIndex

class Decoder {
    fun fromFrame(m: String): String {
        val beginIdx = m.substringIndex(TERMINATE_SEQ) ?: throw TerminateSequenceNotFoundException()
        val endIdx = m.substringIndex(TERMINATE_SEQ, beginIdx+1) ?: throw TerminateSequenceNotFoundException()
        var message = m.substring(beginIdx+ TERMINATE_SEQ.length, endIdx)
        message = message.replace("0111110", "011111")
        val crc = message.substring(message.length-CRC_SIZE, message.length)
        message = message.removeSuffix(crc)
        if (getCRC32(message) != crc) throw IncorrectCRCException()
        return message
    }

    fun decodeMessage(m: List<String>): String {
        var result = ""
        m.forEach { result += fromFrame(it) }
        return result
    }

}
//    fun fromFrame(m: String): String {
//        if ((endIdx - (beginIdx+ TERMINATE_SEQ.length)) < CRC_SIZE) throw CRC16NotFoundException()
//        if ((endIdx - CRC_SIZE) - (beginIdx+ TERMINATE_SEQ.length) <= 0) throw MessageNotFoundException()
//        val crc = m.substring(endIdx - CRC_SIZE, endIdx)
//        var message = m.substring(beginIdx+ TERMINATE_SEQ.length, endIdx - CRC_SIZE)
//        var escapeIndex : Int? = message.substringIndex(ESCAPE_AFTER + ESCAPE_SYMBOL)
//        while (escapeIndex != null) {
//            message = message.removeRange(escapeIndex + ESCAPE_AFTER.length, escapeIndex + ESCAPE_AFTER.length+ ESCAPE_SYMBOL.length)
//            escapeIndex = message.substring(escapeIndex + ESCAPE_AFTER.length).substringIndex(ESCAPE_AFTER + ESCAPE_SYMBOL)
//        }
//        if (getCRC16(message) != crc) throw IncorrectCRCException()
//        return message
//    }
