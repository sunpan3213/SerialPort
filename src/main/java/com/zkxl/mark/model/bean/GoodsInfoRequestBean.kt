package com.zkxl.mark.model.bean

/**
 * Create by Panda on 2019-06-29
 */
data class GoodsInfoRequestBean(
    val dtoInfo: GIRB
)

data class GIRB(
    val EnterpriseId: String,
    val ItemNumber: String
)