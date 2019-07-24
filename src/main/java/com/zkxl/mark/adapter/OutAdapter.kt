package com.zkxl.mark.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zkxl.mark.R
import com.zkxl.mark.model.bean.OutItemBean
import kotlinx.android.synthetic.main.item_out.view.*

/**
 * Create by Panda on 2019/4/15
 */
class OutAdapter(resId:Int,data:ArrayList<OutItemBean>) :BaseQuickAdapter<OutItemBean,BaseViewHolder>(resId,data){

    override fun convert(helper: BaseViewHolder?, item: OutItemBean?) {
        helper?.itemView?.itemOut_id_T?.text = item?.tagId
        if (item!!.red){
            helper?.itemView?.itemOut_id_T?.setTextColor(mContext.resources.getColor(R.color.red))
        }else{
            helper?.itemView?.itemOut_id_T?.setTextColor(mContext.resources.getColor(R.color.black))
        }
    }

}