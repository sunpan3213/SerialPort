package com.zkxl.mark.view.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Point
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.widget.TextView
import android_serialport_api.SerialPortHelper
import com.zkxl.mark.R
import com.zkxl.mark.base.BaseActivity
import com.zkxl.mark.base_utils.LanUtil
import com.zkxl.mark.base_utils.SPUtils
import com.zkxl.mark.base_utils.Staticc
import com.zkxl.mark.view.fragment.HistoryFragment
import com.zkxl.mark.view.fragment.InOutFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.navigation_header.*
import kotlinx.android.synthetic.main.sub_item.*

class MainActivity : BaseActivity() {

    lateinit var historyFragment: HistoryFragment
    lateinit var inoutFragment: InOutFragment

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        initNavigation()
        initFragments()
        setDefaultFragment()
    }

    override fun initData() {

    }

    override fun initEvent() {
        menu_home.setOnClickListener {
            switchFragment(0)

        }
        menu_history.setOnClickListener {
            switchFragment(1)

        }
        menu_my_setip.setOnClickListener {
            switchFragment(2)

        }
        menu_my_modify.setOnClickListener {
            switchFragment(3)

        }
        menu_my_lan.setOnClickListener{
            selectLan()
        }
        menu_my_channel.setOnClickListener{

        }
        menu_my_ip.setOnClickListener{
            switchFragment(4)
        }
        ll_exit.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage(getString(R.string.exit_title))
                .setNegativeButton(getString(R.string.out_unbind_no), null)
                .setPositiveButton(getString(R.string.out_unbind_yes)) { _, _ ->
                    SPUtils.putString(SPUtils.KEY_EnterpriseCode, "")
                    SPUtils.putString(SPUtils.KEY_LoginName, "")
                    SPUtils.putString(SPUtils.KEY_LoginPassword, "")
                    Staticc.account = null
                    go<LoginActivity>()
                    finish()
                }
                .show()
        }
    }

    private fun initNavigation() {
        val point = Point()
        windowManager.defaultDisplay.getSize(point)
        val params = navigation.layoutParams
        params.width = point.x / 2
        navigation.layoutParams = params

        tv_name.text = Staticc.account.UserName
        tv_company_name.text = Staticc.account.Group.Name
        tv_version.text = "V".plus(packageManager.getPackageInfo(packageName, 0).versionName)
    }

    private fun initFragments() {
        historyFragment = HistoryFragment()
        inoutFragment = InOutFragment()
    }

    private fun setDefaultFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fl, historyFragment)
            .add(R.id.fl, inoutFragment)
            .show(inoutFragment)
            .hide(historyFragment)
            .commit()
    }

    private fun switchFragment(menuItem: Int) {
        dl.closeDrawer(navigation)
        when (menuItem) {
            0 -> {
                hideAllFragment()
                supportFragmentManager.beginTransaction()
                    .show(inoutFragment)
                    .commit()
            }
            1 -> {
                hideAllFragment()
                supportFragmentManager.beginTransaction()
                    .show(historyFragment)
                    .commit()
            }
            2 -> {
                go<LoginSetActivity>()
            }
            3 -> {
                go<RepwdActivity>()
            }
            4 -> {
                go<WifiActivity>()
            }
        }
    }

    private fun selectLan() {
        dl.closeDrawer(navigation)

        val view = layoutInflater.inflate(R.layout.alert_select, null)
        val tv_cn = view.findViewById<TextView>(R.id.tv_cn)
        val tv_en = view.findViewById<TextView>(R.id.tv_en)
        val dialog = AlertDialog.Builder(this).setView(view).create()
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
        tv_cn.setOnClickListener {
            dialog.dismiss()
            LanUtil.set(this,1)
            go<LaunchActivity>()
            finish()
        }
        tv_en.setOnClickListener {
            dialog.dismiss()
            LanUtil.set(this,2)
            go<LaunchActivity>()
            finish()
        }
    }

    private fun hideAllFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        for (fragment in supportFragmentManager.fragments) {
            transaction.hide(fragment)
        }
        transaction.commit()
    }


    @SuppressLint("RestrictedApi")
    fun disableShiftMode(view: BottomNavigationView) {
        val count = view.getChildCount()
        if (count > 0) {
            val menuView = view.getChildAt(0) as BottomNavigationMenuView
            menuView.setLabelVisibilityMode(1);
            menuView.updateMenuView();
        }
    }

    private var mTime = 0L

    override fun onBackPressed() {
        if (System.currentTimeMillis() - mTime >2000){
            mTime = System.currentTimeMillis()
            toast("再按一次回退键退出APP!")
        }else{
            finish()
            SerialPortHelper.powerDown()
            SerialPortHelper.stop()
            super.onBackPressed()
        }
    }
}
