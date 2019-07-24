package com.zkxl.mark.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zkxl.mark.model.bean.HistoryBean
import kotlinx.android.synthetic.main.item_history.view.*

/**
 * Create by Panda on 2019/4/15
 */
class HistoryAdapter(resId:Int, data:ArrayList<HistoryBean>) :BaseQuickAdapter<HistoryBean,BaseViewHolder>(resId,data){

    override fun convert(helper: BaseViewHolder?, item: HistoryBean?) {
        helper?.itemView?.itemHistory_RefreshNo_T?.text = item?.RefreshNo
        helper?.itemView?.itemHistory_CreateOwn_T?.text = item?.CreateOwn
    }

}