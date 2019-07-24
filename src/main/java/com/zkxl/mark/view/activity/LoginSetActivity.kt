package com.zkxl.mark.view.activity

import android.content.Context
import android.text.TextUtils
import android.view.inputmethod.InputMethodManager
import com.zkxl.mark.R
import com.zkxl.mark.base.BaseActivity
import com.zkxl.mark.base_utils.SPUtils
import com.zkxl.mark.model.net.RetrofitUtils
import kotlinx.android.synthetic.main.activity_login_set.*
import me.jessyan.retrofiturlmanager.RetrofitUrlManager

class LoginSetActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_login_set
    }

    override fun initView() {
        loginSet_ip_E.setText(SPUtils.getString(SPUtils.KEY_IP))
    }

    override fun initData() {

    }

    override fun initEvent() {
        loginSet_root_LL.setOnClickListener { v ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
        }
        loginSet_back_Img.setOnClickListener { finish() }
        loginSet_commit_B.setOnClickListener{
            val ip = loginSet_ip_E.text.toString()
            if (!TextUtils.isEmpty(ip)){
                RetrofitUrlManager.getInstance().setGlobalDomain(ip)
                SPUtils.putString(SPUtils.KEY_IP,ip)
                RetrofitUtils.BASEURL = ip
            }
            toast(getString(R.string.login_set_success))
            finish()
        }
    }

}
