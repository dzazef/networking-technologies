package crc

import CRC_SIZE
import ESCAPE_AFTER
import TARGET_FRAME_SIZE
import TERMINATE_SEQ
import getCRC
import removeFirst

class Coder {
    fun toFrame(m: String): Pair<String, String> {
        val message = m.substring(0, Math.min(m.length, TARGET_FRAME_SIZE-CRC_SIZE-(2*TERMINATE_SEQ.length)))
        val messageLeft = m.replaceFirst(message, "")
        var frame = message+getCRC(message)
        frame = frame.replace("011111", "0111110")
        frame = TERMINATE_SEQ+frame+TERMINATE_SEQ
        return Pair(frame, messageLeft)
    }

    fun codeMessage(m : String): List<String> {
        var message = m
        val resultList = mutableListOf<String>()
        while (message.isNotEmpty()) {
            val pair = toFrame(message)
            resultList.add(pair.first)
            message = pair.second
        }
        return resultList
    }
}

//    fun toFrame(m: String): Pair<String, String> {
//        var frame = TERMINATE_SEQ
//        var message = m
//        var codedString = ""
//
//        while (frame.length < (TARGET_FRAME_SIZE - TERMINATE_SEQ.length- CRC_SIZE) && message.isNotEmpty()) {
//            frame += message[0]
//            codedString += message[0]
//            message = message.removeFirst()
//
//            if (frame.length < (TARGET_FRAME_SIZE - TERMINATE_SEQ.length- CRC_SIZE)  && (frame.substring(frame.length-5, frame.length) == ESCAPE_AFTER)) {
//                frame += "0"
//            }
//        }
//        frame += getCRC(codedString)
//        frame += TERMINATE_SEQ
//        return Pair(frame, message)
//    }
