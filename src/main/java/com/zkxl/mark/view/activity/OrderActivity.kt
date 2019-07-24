package com.zkxl.mark.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.zkxl.mark.R
import com.zkxl.mark.adapter.OrderAdapter
import com.zkxl.mark.base.BaseActivity
import com.zkxl.mark.model.bean.DetailBean
import kotlinx.android.synthetic.main.activity_order.*

class OrderActivity : BaseActivity() {

    var list: ArrayList<DetailBean>? = null
    lateinit var orderAdapter: OrderAdapter

    override fun getLayoutId(): Int {
        return R.layout.activity_order
    }

    override fun initView() {
        val bundle = intent.extras
        list = bundle?.getParcelableArrayList<DetailBean>("list")

        initRV()
    }

    override fun initData() {

    }

    override fun initEvent() {
        order_back_Img.setOnClickListener { finish() }
    }

    private fun initRV() {
        rv.layoutManager = LinearLayoutManager(this)
        orderAdapter = OrderAdapter(R.layout.item_order, list)
        rv.adapter = orderAdapter
    }

}
