package android_serialport_api.entity

import android_serialport_api.HexDump

/**
 * Create by Panda on 2019-06-24
 */
class ReaderInfoRepEntity : BaseEntity() {

    var isBoot = false
    var version = 0

    fun read(data: ByteArray) {
        isBoot = data[0].toInt() == 0x00
        val bytes = ByteArray(2)
        System.arraycopy(data, 1, bytes, 0, 2)
        version = HexDump.toHexString(bytes).toShort(16).toInt()
    }

}