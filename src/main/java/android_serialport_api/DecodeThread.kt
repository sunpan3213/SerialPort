package android_serialport_api

import android_serialport_api.entity.ReaderInfoRepEntity
import android_serialport_api.entity.SendTagRepEntity
import org.greenrobot.eventbus.EventBus
import java.util.*

/**
 * Create by Panda on 2019-07-02
 */
class DecodeThread :Thread(){
    private val mTaskQueue = LinkedList<ByteArray>() // 待解析任务队列
    private val ticket = Object()

    override fun run() {
        while (!isInterrupted) {
            val data = poll() ?: continue
            try {
                val sop = data[0]
                val lengthBytes = ByteArray(2)
                System.arraycopy(data, 1, lengthBytes, 0, 2)
                val len: Short = HexDump.toHexString(lengthBytes).toShort(16)
                val cmd = data[3]
                val bytes = ByteArray(len.toInt())
                System.arraycopy(data, 4, bytes, 0, bytes.size)
                when (cmd) {
                    0x50.toByte() -> {
                        val entity = ReaderInfoRepEntity()
                        entity.read(bytes)
                        EventBus.getDefault().post(entity)
                    }
                    0x55.toByte() -> {
                        val entity = SendTagRepEntity()
                        entity.read(bytes)
                        EventBus.getDefault().post(entity)
                    }
                }
            }catch (e:Exception){

            }
        }
    }

    fun decodeLater(byteArray: ByteArray) {
//        Log.e("panda","解析数据->"+HexDump.toHexString(byteArray))
        synchronized(mTaskQueue) {
            mTaskQueue.add(byteArray)
        }
        synchronized(ticket) {
            ticket.notify()
        }
    }

    private fun poll(): ByteArray? {
        synchronized(mTaskQueue) {
            if (mTaskQueue.size > 0) {
                val bytes = mTaskQueue.removeAt(0)

                return bytes
            }
        }
        synchronized(ticket) {
            try {
                ticket.wait()
            } catch (ex: InterruptedException) {
            }

        }
        return null
    }
}