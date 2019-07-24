package com.zkxl.mark.base_utils

import android.view.KeyEvent
import java.lang.StringBuilder

/**
 * Create by Panda on 2019-06-28
 */
object ScanHelper {

    private var mCaps = false
    private val map = hashMapOf<Int, String>()
    val sb = StringBuilder()

    //检查shift键
    fun checkLetterStatus(event: KeyEvent?) {
        initMap()
        val keyCode = event?.getKeyCode()
        if (keyCode == KeyEvent.KEYCODE_SHIFT_RIGHT || keyCode == KeyEvent.KEYCODE_SHIFT_LEFT) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                //按着shift键，表示大写
                mCaps = true
            } else {
                //松开shift键，表示小写
                mCaps = false
            }
        }
    }

    //根据keycode得到对应的字母和数字
    fun keyCodeToNum(keycode: Int): Boolean {
        if (keycode >= KeyEvent.KEYCODE_A && keycode <= KeyEvent.KEYCODE_Z) {
            if (mCaps) {
                sb.append(map.get(keycode)?.toUpperCase())
            } else {
                sb.append(map.get(keycode))
            }
            return true
        } else if ((keycode >= KeyEvent.KEYCODE_0 && keycode <= KeyEvent.KEYCODE_9)) {
            sb.append(keycode - KeyEvent.KEYCODE_0)
            return true
        } else {
            //暂不处理特殊符号
            return false
        }
    }

    private fun initMap() {
        map.put(29, "a")
        map.put(30, "b")
        map.put(31, "c")
        map.put(32, "d")
        map.put(33, "e")
        map.put(34, "f")
        map.put(35, "g")
        map.put(36, "h")
        map.put(37, "i")
        map.put(38, "g")
        map.put(39, "k")
        map.put(40, "l")
        map.put(41, "m")
        map.put(42, "n")
        map.put(43, "0")
        map.put(44, "p")
        map.put(45, "q")
        map.put(46, "r")
        map.put(47, "s")
        map.put(48, "t")
        map.put(49, "u")
        map.put(50, "v")
        map.put(51, "w")
        map.put(52, "x")
        map.put(53, "y")
        map.put(54, "z")
    }


}