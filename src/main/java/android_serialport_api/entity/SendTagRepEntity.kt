package android_serialport_api.entity

import android_serialport_api.HexDump

/**
 * Create by Panda on 2019-06-24
 */
class SendTagRepEntity : BaseEntity() {

    var uid = ""
    var type = ""

    fun read(data: ByteArray) {
        val bytes = ByteArray(4)
        System.arraycopy(data, 0, bytes, 0, 4)
        uid = HexDump.toHexString(bytes).replaceFirst("00", "HL", false)
        when (data[4]) {
            0x00.toByte() -> type = "M210"
            0x01.toByte() -> type = "M290"
            0x02.toByte() -> type = "M400"
        }
    }

}