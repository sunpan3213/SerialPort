package com.zkxl.mark.model.bean

/**
 * Create by Panda on 2019-06-29
 */
data class UnBindRequestBean(
    val dtoInfo: List<UBRB>
)

data class UBRB(
    val EnterpriseId: String,
    val GroupId: String,
    val TagId: String,
    val UserId: String,
    val CreateOwn: String
)