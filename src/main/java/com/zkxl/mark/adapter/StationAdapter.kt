package com.zkxl.mark.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zkxl.mark.R
import com.zkxl.mark.model.bean.StationBean

/**
 * Create by Panda on 2019/4/15
 */
class StationAdapter(resId: Int, data: ArrayList<StationBean>) :
    BaseQuickAdapter<StationBean, BaseViewHolder>(resId, data) {

    override fun convert(helper: BaseViewHolder?, item: StationBean) {
        helper?.setText(R.id.itemStation_name_T, item.ApId)
        if (item.isSelected) {
            helper?.setImageResource(R.id.itemStation_select_IMG, R.drawable.xz_btn_selected)
        } else {
            helper?.setImageResource(R.id.itemStation_select_IMG, R.drawable.xz_btn_normal)
        }
        if (item.Status == 1) {
            helper?.setImageResource(R.id.itemStation_status_IMG, R.drawable.online)
        } else {
            helper?.setImageResource(R.id.itemStation_status_IMG, R.drawable.offline)
        }
        helper?.addOnClickListener(R.id.itemStation_body_LL)
    }

}