package com.zkxl.mark.view.fragment

import android.content.Intent
import com.zkxl.mark.R
import com.zkxl.mark.base.BaseFragment
import com.zkxl.mark.view.activity.InActivity
import com.zkxl.mark.view.activity.OutActivity
import kotlinx.android.synthetic.main.fragment_inout.*

class InOutFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_inout
    }

    override fun initData() {

    }

    override fun initEvent() {
        inOut_out_LL.setOnClickListener { startActivity(Intent(mContext, OutActivity::class.java)) }
        inOut_in_LL.setOnClickListener { startActivity(Intent(mContext, InActivity::class.java)) }
    }
}
