package com.zkxl.mark.model.bean

import android.icu.text.IDNA

/**
 * Create by Panda on 2019-06-29
 */
data class GoodsInfoBean(
    val Barcode: String,
    val CreateDate: String,
    val CreateOwn: String,
    val DisplayName: String,
    val EnterpriseCode: String,
    val EnterpriseId: String,
    val GoodsAttributes: List<GoodsAttribute>,
    val GoodsDetail: List<Any>,
    val GoodsId: Int,
    val Grade: String,
    val GroupCode: String,
    val ItemNumber: String,
    val Name: String,
    val Origin: String,
    val Pic: String,
    val PriceMember: String,
    val SearchKeyword: String,
    val Spec: String,
    val Status: Int,
    val TemplateCode: String,
    val Unit: String,
    val UpdateDate: String,
    val UpdateOwn: String
)

data class GoodsAttribute(
    val CreateDate: String,
    val CreateOwn: String,
    val GoodsAttributeId: Int,
    val GoodsId: Int,
    val GoodsKey: String,
    val GoodsValue: String,
    val ItemNumber: String
)