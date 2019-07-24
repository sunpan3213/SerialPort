package com.zkxl.mark.view.activity

import android.content.Context
import android.net.wifi.WifiManager
import com.zkxl.mark.R
import com.zkxl.mark.base.BaseActivity
import kotlinx.android.synthetic.main.activity_wifi.*
import java.lang.StringBuilder

class WifiActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_wifi
    }

    override fun initData() {
        val manager = mContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val builder = StringBuilder().append("IP地址：").append(intToIp(manager.dhcpInfo.ipAddress))
            .append("\n网关：").append(intToIp(manager.dhcpInfo.gateway))
            .append("\n子网掩码：").append(intToIp(manager.dhcpInfo.netmask))
        wifi.text = builder.toString()
    }

    override fun initEvent() {
        wifi_back_Img.setOnClickListener { finish() }
    }

    private fun intToIp(paramInt: Int): String {
        return ((paramInt and 0xFF).toString() + "." + (0xFF and (paramInt shr 8)) + "." + (0xFF and (paramInt shr 16)) + "."
                + (0xFF and (paramInt shr 24)))
    }
}
