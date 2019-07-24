package android_serialport_api

import android.util.Log
import android_serialport_api.entity.ReaderInfoRepEntity
import android_serialport_api.entity.SendTagRepEntity
import org.greenrobot.eventbus.EventBus
import java.io.FileInputStream
import java.io.IOException

/**
 * Create by Panda on 2019-06-21
 */
class ReceiveThread(val fis: FileInputStream, val decodeThread: DecodeThread?) : Thread() {

    val list = arrayListOf<ByteArray>()
    val indexList = arrayListOf<Int>()

    override fun run() {
        while (!isInterrupted) {
            val readData = ByteArray(1024)
            try {
                val size = fis.read(readData)
                if (size > 0) {
                    val data = ByteArray(size)
                    System.arraycopy(readData, 0, data, 0, size)
                    Log.e("serialport", "receive->" + HexDump.toHexString(data))

                    if (data[0] == 0xA5.toByte() && data[data.lastIndex] == 0xCA.toByte()) {//完整
//                    Log.e(TAG, "完整数据")
                        list.clear()//清除未组包的无效数据
                        indexList.clear()
                        synchronized(this) {
                            data.filterIndexed { index, byte ->
                                if (byte == 0XA5.toByte()) {
                                    indexList.add(index)
                                } else {
                                    false
                                }
                            }
                            for (i in indexList.indices) {
//                            Log.e("panda", "index->" + indexList[i])
                                val lengthBytes = ByteArray(2)
                                System.arraycopy(data, indexList[i] + 1, lengthBytes, 0, 2)
                                val length = HexDump.toHexString(lengthBytes).toShort(16) + 3 + 2//包总长度
                                if (data[length - 1] == 0xCA.toByte()) {//判断包尾
                                    val bytes = ByteArray(length)
                                    System.arraycopy(data, indexList[i], bytes, 0, bytes.size)
                                    decodeThread?.decodeLater(bytes)
                                }
                            }
                        }
                    }
                    if (data[0] == 0xA5.toByte() && data[data.lastIndex] != 0xCA.toByte()) {//包尾没有
//                    Log.e(TAG, "没包尾数据")
                        list.clear()//清除未组包的无效数据
                        indexList.clear()
                        list.add(data)
                    }
                    if (data[0] != 0xA5.toByte() && data[data.lastIndex] == 0xCA.toByte()) {//包头没有 包尾有
//                    Log.e(TAG, "没包头数据")
                        list.add(data)
                        val array = ByteArray(5 * 1024)
                        var offset = 0
                        while (list.size > 0) {
                            val bytes = list.removeAt(0)
                            System.arraycopy(bytes, 0, array, offset, bytes.size)
                            offset += bytes.size
                        }
                        val realData = ByteArray(offset)
                        System.arraycopy(array, 0, realData, 0, realData.size)
//                    Log.e(CommunicateHelper.javaClass.simpleName, "组包数据->" + HexDump.toHexString(realData))
//                    Log.e(CommunicateHelper.javaClass.simpleName, "组包数据长度->" + realData.size)
                        indexList.clear()
                        synchronized(this) {
                            realData.filterIndexed { index, byte ->
                                if (byte == 0XA5.toByte()) {
                                    indexList.add(index)
                                } else {
                                    false
                                }
                            }
                            for (i in indexList.indices) {
//                            Log.e("panda", "index->" + indexList[i])
                                val lengthBytes = ByteArray(2)
                                System.arraycopy(data, indexList[i] + 1, lengthBytes, 0, 2)
                                val length = HexDump.toHexString(lengthBytes).toShort(16) + 3 + 2//包总长度
                                if (data[length - 1] == 0xCA.toByte()) {//判断包尾
                                    val bytes = ByteArray(length)
                                    System.arraycopy(realData, indexList[i], bytes, 0, bytes.size)
                                    decodeThread?.decodeLater(bytes)
                                }
                            }
                        }
                    }
                    if (data[0] != 0xA5.toByte() && data[data.lastIndex] != 0xCA.toByte()) {//没包头包尾的中间数据
//                    Log.e(TAG, "没包头包尾的中间数据")
                        list.add(data)
                    }

                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}