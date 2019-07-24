package com.zkxl.mark.model.bean

/**
 * Create by Panda on 2019-06-29
 */
data class BindRequestBean(
    val dtoInfo: List<BRB>
)

data class BRB(
    val EnterpriseId: String,
    val EnterpriseCode: String,
    var TagId: String,
    val GoodsId: String?,
    val ItemNumber: String?,
    var TemplateId: String?,
    var TemplateCode: String?,
    val UserId: String,
    val CreateOwn: String,
    var ApId: String?,
    var Id: String?
)