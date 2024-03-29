package com.zkxl.mark.base_utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.zkxl.mark.base.MyApp

/**
 * Create by Panda on 2019/4/4
 */
object SPUtils {

    private val NAME = "DataShared"// 表名

    // 登录用户数据
    val KEY_IP = "KEY_IP"
    val KEY_EnterpriseCode = "KEY_EnterpriseCode"
    val KEY_LoginName = "KEY_LoginName"
    val KEY_LoginPassword = "KEY_LoginPassword"

    fun putString(key: String, value: String) {
        MyApp.app.applicationContext.getSharedPreferences(NAME, MODE_PRIVATE).edit().putString(key, value).apply()
    }

    fun getString(key: String): String {
        return MyApp.app.applicationContext.getSharedPreferences(NAME, MODE_PRIVATE).getString(key, "")!!
    }

    fun putInt(key: String, value: Int) {
        MyApp.app.applicationContext.getSharedPreferences(NAME, MODE_PRIVATE).edit().putInt(key, value).apply()
    }

    fun getInt(key: String): Int {
        return MyApp.app.applicationContext.getSharedPreferences(NAME, MODE_PRIVATE).getInt(key, -1)
    }

    fun putBoolean(key: String, value: Boolean) {
        MyApp.app.applicationContext.getSharedPreferences(NAME, MODE_PRIVATE).edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return MyApp.app.applicationContext.getSharedPreferences(NAME, MODE_PRIVATE).getBoolean(key, false)
    }
}