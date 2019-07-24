package com.zkxl.mark.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.view.inputmethod.InputMethodManager
import com.zkxl.mark.R
import com.zkxl.mark.base.BaseTextWatcherActivity
import com.zkxl.mark.base_utils.MD5
import com.zkxl.mark.base_utils.Staticc
import com.zkxl.mark.model.bean.Bean
import com.zkxl.mark.model.bean.MRB
import com.zkxl.mark.model.bean.ModifyPwdRequestBean
import com.zkxl.mark.viewmodel.ModifyPwdVM
import kotlinx.android.synthetic.main.activity_repwd.*

class RepwdActivity : BaseTextWatcherActivity() {

    lateinit var pwdVM: ModifyPwdVM

    override fun getLayoutId(): Int {
        return R.layout.activity_repwd
    }

    override fun initData() {
        pwdVM = ViewModelProviders.of(this).get(ModifyPwdVM::class.java)
        val observer = Observer<Bean<String>> { res ->
            when (res?.Code) {
                Bean.SUCCESS -> {
                    finishLoading()
                    toast(getString(R.string.repwd_success))
                    finish()
                }
                Bean.ERROR -> {
                    finishLoading()
                    toast(res.Message)
                }
                Bean.LOADING -> {
                    showLoading()
                }
            }
        }
        pwdVM.liveData.observe(this, observer)
    }

    override fun initEvent() {
        repwd_pwd_E.addTextChangedListener(this)
        repwd_newpwd_E.addTextChangedListener(this)
        repwd_confirm_E.addTextChangedListener(this)

        repwd_back_Img.setOnClickListener { finish() }
        repwd_root_LL.setOnClickListener { v ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
        }
        repwd_commit_RL.setOnClickListener { commit() }
    }

    override fun afterTextChanged(s: Editable?) {
        val s1 = repwd_pwd_E.text.toString()
        val s2 = repwd_newpwd_E.text.toString()
        val s3 = repwd_confirm_E.text.toString()
        if (!TextUtils.isEmpty(s1) && !TextUtils.isEmpty(s2) && !TextUtils.isEmpty(s3)) {
            repwd_commit_RL.setBackgroundResource(R.drawable.btn_enabled_0874af)
            repwd_commit_RL.isEnabled = true
        } else {
            repwd_commit_RL.setBackgroundResource(R.drawable.btn_disabled)
            repwd_commit_RL.isEnabled = false
        }

    }

    private fun commit() {

        val requestBean = ModifyPwdRequestBean(
            MRB(
                Staticc.account.UserId.toString(),
                MD5.encrypt(repwd_pwd_E.text.toString()),
                MD5.encrypt(repwd_newpwd_E.text.toString())
            )
        )
        pwdVM.modify(requestBean)
    }
}
