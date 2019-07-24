package com.zkxl.mark.model.bean

/**
 * Create by Panda on 2019-07-02
 */
data class UpdateRequestBean(val dtoInfo: URB)

data class URB(
    val EnterpriseId: String,
    val EnterpriseCode: String,
    val GroupCode: String,
    val GroupId: String,
    val TagIds: ArrayList<String>,
    val UserId: String,
    val CreateOwn: String
)