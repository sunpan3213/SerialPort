package com.zkxl.mark.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zkxl.mark.R
import com.zkxl.mark.model.bean.InItemBean
import com.zkxl.mark.model.bean.InTagType
import kotlinx.android.synthetic.main.item_out.view.*

/**
 * Create by Panda on 2019/4/15
 */
class InTagTypeAdapter(resId:Int, data:ArrayList<InTagType>) : BaseQuickAdapter<InTagType, BaseViewHolder>(resId,data){

    override fun convert(helper: BaseViewHolder?, item: InTagType?) {
        helper?.setText(R.id.itemTagType_name_T,item?.tagType)
        if (item!!.isSelected){
            helper?.setImageResource(R.id.itemTagType_select_IMG,R.drawable.xz_btn_selected)
        }else{
            helper?.setImageResource(R.id.itemTagType_select_IMG,R.drawable.xz_btn_normal)
        }
        helper?.addOnClickListener(R.id.itemTagType_body_LL)
    }

}