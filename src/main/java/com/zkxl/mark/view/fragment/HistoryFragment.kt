package com.zkxl.mark.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.zkxl.mark.R
import com.zkxl.mark.adapter.HistoryAdapter
import com.zkxl.mark.base.BaseFragment
import com.zkxl.mark.base_utils.Staticc
import com.zkxl.mark.model.bean.Bean
import com.zkxl.mark.model.bean.DetailBean
import com.zkxl.mark.model.bean.HistoryBean
import com.zkxl.mark.view.activity.OrderActivity
import com.zkxl.mark.viewmodel.HistoryVM
import kotlinx.android.synthetic.main.fragment_history.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Create by Panda on 2019-07-04
 */
class HistoryFragment :BaseFragment(){

    lateinit var historyVM: HistoryVM
    lateinit var historyAdapter: HistoryAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_history
    }

    override fun initView() {
        val data = arrayListOf<HistoryBean>()
        rv.layoutManager = LinearLayoutManager(mContext)
        historyAdapter = HistoryAdapter(R.layout.item_history, data)
        rv.adapter = historyAdapter
    }

    override fun initData() {
        historyVM = ViewModelProviders.of(this).get(HistoryVM::class.java)
        val observer = Observer<Bean<List<HistoryBean>>> { res ->
            when (res?.Code) {
                Bean.SUCCESS -> {
                    finishLoading()
                    res.Value?.let { historyAdapter.addData(it) }
                }
                Bean.ERROR -> {
                    finishLoading()
                    toast(res.Message)
                }
                Bean.LOADING -> showLoading()
            }
        }
        historyVM.liveData.observe(this, observer)

    }

    override fun initEvent() {
        historyAdapter.setOnItemClickListener { adapter, view, position ->
            val bundle = Bundle()
            val list = ArrayList<DetailBean>()
            list.addAll(historyAdapter.data[position].RefreshResult)
            bundle.putParcelableArrayList("list", list)
            go<OrderActivity>(bundle)
        }
    }

    override fun show() {
        doNet()
    }

    private fun doNet() {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.DAY_OF_MONTH, -2)
        val startDate = calendar.time
        calendar.add(Calendar.DAY_OF_MONTH, 3)
        val endDate = calendar.time
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
        val map = hashMapOf<String, String>()
        map.put("EnterpriseId", Staticc.account.EnterpriseId)
        map.put("GroupId", Staticc.account.Group.GroupId.toString())
        map.put("UserId", Staticc.account.UserId.toString())
        map.put("StartDate", format.format(startDate))
        map.put("EndDate", format.format(endDate))
        map.put("PageIndex", "1")
        map.put("PageSize", "9999")
        historyVM.getHistoryList(map)
    }

}