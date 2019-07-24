package android_serialport_api

import android.util.Log
import android_serialport_api.entity.ReaderInfoEntity
import android_serialport_api.entity.SendTagEntity

/**
 * Create by Panda on 2019-06-21
 */
object SendDataUtils {

    //获取读头信息
    fun readerInfo() {
        val entity = ReaderInfoEntity()
        val bytes = entity.write()
        SerialPortHelper.sendData(bytes.toByteArray())
        Log.e("serialport", "send->" + HexDump.toHexString(bytes.toByteArray()))
    }

    //更新价签配置
    fun updateMark(hexString:String?){
        val entity = SendTagEntity()
        if (hexString==null){
            entity.rfaddr.add(0x00)
            entity.rfaddr.add(0x00)
            entity.rfaddr.add(0x00)
            entity.rfaddr.add(0x00)
            entity.ch.add(0x00)
            entity.ch.add(0x00)
            entity.rate.add(0x00)
            entity.rate.add(0x00)
        }else{
            val byteArray = HexDump.hexStringToByteArray(hexString)
            entity.heartbeat = byteArray[0]
            entity.rfaddr.add(byteArray[1])
            entity.rfaddr.add(byteArray[2])
            entity.rfaddr.add(byteArray[3])
            entity.rfaddr.add(byteArray[4])
            entity.ch.add(byteArray[5])
            entity.ch.add(byteArray[6])
            entity.rate.add(byteArray[7])
            entity.rate.add(byteArray[8])
            entity.enable = byteArray[9]
        }
        val bytes = entity.write()
        SerialPortHelper.sendData(bytes.toByteArray())
        Log.e("serialport", "send->" + HexDump.toHexString(bytes.toByteArray()))
    }

}