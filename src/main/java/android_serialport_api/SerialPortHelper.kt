package android_serialport_api

import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

/**
 * Create by Panda on 2019-06-21
 */

object SerialPortHelper {

    private val UART_SWITCH_PATH = "/sys/devices/soc.0/78b0000.serial/uart_switch"
    private var receiveThread: ReceiveThread? = null
    private var decodeThread: DecodeThread? = null
    private var sendThread: SendThread? = null
    private var serialPort: SerialPort? = null

    fun open(string: String, baudrate: Int) {
        serialPort = SerialPort(File(string), baudrate, 0)
        serialPort?.let { start(it.inputStream, it.outputStream) }

    }

    private fun start(inputStream: FileInputStream, outputStream: FileOutputStream) {
        decodeThread = DecodeThread()
        decodeThread?.start()
        receiveThread = ReceiveThread(inputStream,decodeThread)
        receiveThread?.start()
        sendThread = SendThread(outputStream)
        sendThread?.start()
    }

    fun sendData(byteArray: ByteArray){
        sendThread?.addData(byteArray)
    }

    fun powerUp() {
        switchSerialPin("uart3")
    }

    fun powerDown() {
        switchSerialPin("disable")
        switchSerialPin("uart2")
    }

    private fun switchSerialPin(whichPin: String) {
        val f = File(UART_SWITCH_PATH)
        try {
            val out = FileOutputStream(f)
            out.write(whichPin.toByteArray())
            out.close()
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("serialport", "File write failed: \$e")
        }

    }

    fun stop() {
        receiveThread?.interrupt()
        receiveThread = null
        sendThread?.interrupt()
        sendThread = null
        serialPort?.allClose()
        decodeThread?.interrupt()
        decodeThread = null
    }
}