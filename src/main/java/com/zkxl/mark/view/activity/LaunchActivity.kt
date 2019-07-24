package com.zkxl.mark.view.activity

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.text.TextUtils
import android_serialport_api.SendDataUtils
import android_serialport_api.SerialPortHelper
import com.zkxl.mark.R
import com.zkxl.mark.base.BaseActivity
import com.zkxl.mark.base_utils.LanUtil
import com.zkxl.mark.base_utils.SPUtils
import com.zkxl.mark.base_utils.Staticc
import com.zkxl.mark.model.bean.Bean
import com.zkxl.mark.model.bean.LRB
import com.zkxl.mark.model.bean.LoginBean
import com.zkxl.mark.model.bean.LoginRequestBean
import com.zkxl.mark.viewmodel.LoginVM
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import java.util.concurrent.TimeUnit

class LaunchActivity : BaseActivity() {

    var disposable: Disposable? = null

    lateinit var loginVM: LoginVM
    lateinit var observer: Observer<Bean<LoginBean>>

    override fun getLayoutId(): Int {
        return R.layout.activity_launch
    }

    override fun initView() {
        LanUtil.init(this)
    }

    override fun initData() {

        loginVM = ViewModelProviders.of(this).get(LoginVM::class.java)

        observer = Observer { res ->
            when (res?.Code) {
                Bean.SUCCESS -> {
                    finishLoading()
                    Staticc.account = res.Value
                    go<MainActivity>()
                    finish()
                }
                Bean.ERROR -> {
                    finishLoading()
                    retry(getString(R.string.result_autologin_problem), getString(R.string.result_login2))
                }
                Bean.LOADING -> showLoading()
            }
        }
        loginVM.liveData.observe(this, observer)

        SerialPortHelper.powerUp()
        SerialPortHelper.open("/dev/ttyHSL0",115200)
        checkCache()
    }

    private fun checkCache() {
        val ip = SPUtils.getString(SPUtils.KEY_IP)
        if (!TextUtils.isEmpty(ip)) {
            RetrofitUrlManager.getInstance().setGlobalDomain(ip)
        }

        val enterpriseCode = SPUtils.getString(SPUtils.KEY_EnterpriseCode)
        val loginName = SPUtils.getString(SPUtils.KEY_LoginName)
        val loginPasswordMD5 = SPUtils.getString(SPUtils.KEY_LoginPassword)
        if (TextUtils.isEmpty(enterpriseCode) || TextUtils.isEmpty(loginName) || TextUtils.isEmpty(loginPasswordMD5)) {
            disposable =
                Observable.timer(1500, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe {
                    go<LoginActivity>()
                    finish()
                }
        } else {
            val requestBean = LoginRequestBean(LRB(enterpriseCode, loginName, loginPasswordMD5))
            loginVM.login(requestBean)
        }
    }

    fun retry(title: String, msg: String) {
        val dialog = AlertDialog.Builder(this)
            .setPositiveButton("重试") { dialog, _ ->
                dialog.dismiss()
                val enterpriseCode = SPUtils.getString(SPUtils.KEY_EnterpriseCode)
                val loginName = SPUtils.getString(SPUtils.KEY_LoginName)
                val loginPasswordMD5 = SPUtils.getString(SPUtils.KEY_LoginPassword)
                loginVM.login(LoginRequestBean(LRB(enterpriseCode, loginName, loginPasswordMD5)))
            }.setNegativeButton("手动登录") { dialog, _ ->
                dialog.dismiss()
                SPUtils.putString(SPUtils.KEY_EnterpriseCode, "")
                SPUtils.putString(SPUtils.KEY_LoginName, "")
                SPUtils.putString(SPUtils.KEY_LoginPassword, "")
                go<LoginActivity>()
                finish()
            }
            .create()
        dialog.setCancelable(false)
        dialog.setCancelable(false)
        dialog.setTitle(title)
        dialog.setMessage(msg)
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}
