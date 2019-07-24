package com.zkxl.mark.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android_serialport_api.SerialPortHelper
import com.zkxl.mark.R
import com.zkxl.mark.base.BaseTextWatcherActivity
import com.zkxl.mark.base_utils.MD5
import com.zkxl.mark.base_utils.SPUtils
import com.zkxl.mark.base_utils.Staticc
import com.zkxl.mark.model.bean.Bean
import com.zkxl.mark.model.bean.LRB
import com.zkxl.mark.model.bean.LoginBean
import com.zkxl.mark.model.bean.LoginRequestBean
import com.zkxl.mark.viewmodel.LoginVM
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseTextWatcherActivity(), View.OnClickListener {

    lateinit var loginVM: LoginVM

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initData() {

        loginVM = ViewModelProviders.of(this).get(LoginVM::class.java)

        val observer = Observer<Bean<LoginBean>> { res ->
            when (res?.Code) {
                Bean.SUCCESS -> {
                    finishLoading()

                    SPUtils.putString(SPUtils.KEY_EnterpriseCode, login_id_E.text.toString())
                    SPUtils.putString(SPUtils.KEY_LoginName, login_username_E.text.toString())
                    SPUtils.putString(SPUtils.KEY_LoginPassword, MD5.encrypt(login_password_E.text.toString()))

                    Staticc.account = res.Value

                    go<MainActivity>()
                    finish()
                }
                Bean.ERROR -> {
                    finishLoading()
                    toast(res.Message)
                }
                Bean.LOADING -> showLoading()
            }
        }
        loginVM.liveData.observe(this, observer)
    }

    override fun initEvent() {
        login_root_LL.setOnClickListener(this)
        login_commit_RL.setOnClickListener(this)
        login_set_B.setOnClickListener(this)

        login_id_E.addTextChangedListener(this)
        login_username_E.addTextChangedListener(this)
        login_password_E.addTextChangedListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login_root_LL -> {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
            }
            R.id.login_commit_RL -> {
                login()
            }
            R.id.login_set_B -> {
                go<LoginSetActivity>()
            }
        }
    }

    private fun login() {
        val id = login_id_E.text.toString()
        val username = login_username_E.text.toString()
        val password = login_password_E.text.toString()

        val requestBean = LoginRequestBean(LRB(id, username, MD5.encrypt(password)))
        loginVM.login(requestBean)

    }

    override fun afterTextChanged(s: Editable?) {
        val id = login_id_E.text.toString()
        val username = login_username_E.text.toString()
        val password = login_password_E.text.toString()
        if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            login_commit_RL.setBackgroundResource(R.drawable.btn_enabled_0874af)
        } else {
            login_commit_RL.setBackgroundResource(R.drawable.btn_disabled)
        }
    }

    override fun onDestroy() {
        SerialPortHelper.powerDown()
        SerialPortHelper.stop()
        super.onDestroy()
    }
}
