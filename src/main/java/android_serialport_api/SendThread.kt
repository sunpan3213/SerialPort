package android_serialport_api

import java.io.FileOutputStream
import java.util.*

/**
 * Create by Panda on 2019-06-21
 */
class SendThread(val fos:FileOutputStream) :Thread(){

    private val mTaskQueue = LinkedList<ByteArray>() // 待解析任务队列

    override fun run() {
        while (!isInterrupted){
            val bytes = poll() ?: continue
            try {
                fos.write(bytes)
                fos.flush()
            }catch (e:Exception){

            }
        }
    }

    fun addData(bytes:ByteArray){
        synchronized(mTaskQueue) {
            mTaskQueue.add(bytes)
        }
    }

    private fun poll(): ByteArray? {
        synchronized(mTaskQueue) {
            if (mTaskQueue.size > 0) {
                val bytes = mTaskQueue.removeAt(0)
                return bytes
            }
        }
        return null
    }
}