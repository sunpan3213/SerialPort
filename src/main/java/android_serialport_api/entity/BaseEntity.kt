package android_serialport_api.entity

import android_serialport_api.HexDump
import android_serialport_api.XorUtil

/**
 * Create by Panda on 2019-06-24
 */
abstract class BaseEntity {
    val sop: Byte = 0xA5.toByte()//固定
    var len = arrayListOf<Byte>()//inf的长度->2byte
    var payload = arrayListOf<Byte>()//指令内容
    var xor = 0x00.toByte()//所有字节异或校验
    val end = 0xCA.toByte()//固定

    open fun write(): ArrayList<Byte> {
        val bytes = arrayListOf<Byte>()
        bytes.add(sop)
        len.addAll(HexDump.toByteArray(payload.size.toShort()).toList())
        bytes.addAll(len)
        bytes.addAll(payload)
        xor = XorUtil.xor(getBytes2Xor().toByteArray())
        bytes.add(xor)
        bytes.add(end)
        return bytes
    }

    protected fun getBytes2Xor(): ArrayList<Byte> {
        val toXor = arrayListOf<Byte>()
        toXor.add(sop)
        toXor.addAll(len)
        toXor.addAll(payload)
        toXor.add(xor)
        toXor.add(end)
        return toXor
    }
}