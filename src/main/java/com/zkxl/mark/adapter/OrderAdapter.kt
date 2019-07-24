package com.zkxl.mark.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zkxl.mark.R
import com.zkxl.mark.model.bean.DetailBean
import kotlinx.android.synthetic.main.item_order.view.*

/**
 * Create by Panda on 2019/4/15
 */
class OrderAdapter(resId: Int, data: ArrayList<DetailBean>?) : BaseQuickAdapter<DetailBean, BaseViewHolder>(resId, data) {

    override fun convert(helper: BaseViewHolder?, item: DetailBean?) {
        helper?.itemView?.itemOrder_station_T?.text = item?.ApId
        helper?.itemView?.itemOrder_tagid_T?.text = item?.TagId
        helper?.itemView?.itemOrder_items_T?.text = item?.GoodsName
        when (item?.Status) {
            "0" -> helper?.itemView?.itemOrder_status_T?.text =
                mContext.getResources().getString(R.string.history_transmitting)
            "1" -> helper?.itemView?.itemOrder_status_T?.text =
                mContext.getResources().getString(R.string.history_successful)
            "2" -> helper?.itemView?.itemOrder_status_T?.text =
                mContext.getResources().getString(R.string.history_failed)
            else -> helper?.itemView?.itemOrder_status_T?.text = ""
        }
        when (item?.Status) {
            "2" -> {
                helper?.itemView?.itemOrder_station_T?.setTextColor(mContext.getResources().getColor(R.color.red))
                helper?.itemView?.itemOrder_tagid_T?.setTextColor(mContext.getResources().getColor(R.color.red))
                helper?.itemView?.itemOrder_items_T?.setTextColor(mContext.getResources().getColor(R.color.red))
                helper?.itemView?.itemOrder_status_T?.setTextColor(mContext.getResources().getColor(R.color.red))
            }
            else -> {
                helper?.itemView?.itemOrder_station_T?.setTextColor(mContext.getResources().getColor(R.color.black))
                helper?.itemView?.itemOrder_tagid_T?.setTextColor(mContext.getResources().getColor(R.color.black))
                helper?.itemView?.itemOrder_items_T?.setTextColor(mContext.getResources().getColor(R.color.black))
                helper?.itemView?.itemOrder_status_T?.setTextColor(mContext.getResources().getColor(R.color.black))
            }
        }

    }

}