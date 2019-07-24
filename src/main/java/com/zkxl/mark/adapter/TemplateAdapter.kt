package com.zkxl.mark.adapter

import android.text.TextUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zkxl.mark.R
import com.zkxl.mark.model.bean.TemplateBean

/**
 * Create by Panda on 2019/4/15
 */
class TemplateAdapter(resId: Int, data: ArrayList<TemplateBean>) :
    BaseQuickAdapter<TemplateBean, BaseViewHolder>(resId, data) {

    override fun convert(helper: BaseViewHolder?, item: TemplateBean) {
        var types = ""
        for (str in item.TagTypes) {
            if (!TextUtils.isEmpty(str)) {
                types += ",$str"
            }
        }
        types = types.replaceFirst(",".toRegex(), "")
        helper?.setText(R.id.itemModel_type_T, types)
        helper?.setText(R.id.itemModel_name_T, item.Name)
        Glide.with(mContext).load(item.PicUrl).into(helper?.getView(R.id.itemModel_IMG)!!)
        if (item.isSelected) {
            helper?.setImageResource(R.id.itemModel_select_IMG, R.drawable.xz_btn_selected)
        } else {
            helper?.setImageResource(R.id.itemModel_select_IMG, R.drawable.xz_btn_normal)
        }
        helper?.addOnClickListener(R.id.itemModel_body_LL)
    }

}