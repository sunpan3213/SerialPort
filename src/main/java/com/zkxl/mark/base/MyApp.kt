package com.zkxl.mark.base

import android.app.Application
import com.zkxl.mark.model.net.RetrofitUtils

/**
 * Create by Panda on 2019/3/14
 */
class MyApp :Application(){

    companion object {
        lateinit var app:Application
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        RetrofitUtils.init()
    }
}