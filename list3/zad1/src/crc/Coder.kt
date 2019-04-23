package crc

import CRC_SIZE
import TARGET_FRAME_SIZE
import TERMINATE_SEQ
import getCRC16
import getCRC32

class Coder {
    fun toFrame(m: String): Pair<String, String> {
        val message = m.substring(0, Math.min(m.length, TARGET_FRAME_SIZE-CRC_SIZE-(2*TERMINATE_SEQ.length)))
        val messageLeft = m.replaceFirst(message, "")
        var frame = message+getCRC32(message)
        frame = frame.replace("011111", "0111110")
        frame = TERMINATE_SEQ+frame+TERMINATE_SEQ
        return Pair(frame, messageLeft)
    }

    fun codeMessage(m : String): MutableList<String> {
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