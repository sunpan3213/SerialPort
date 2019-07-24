package com.zkxl.mark.model.bean

/**
 * Create by Panda on 2019/4/8
 */
data class StationBean(
    val ApId: String,
    val ApIp: String,
    val ApName: String,
    val ApType: Int,
    val CreateDate: String,
    val CreateOwn: String,
    val Description: String,
    val EnterpriseCode: String,
    val EnterpriseId: String,
    val Group: Group,
    val GroupCode: String,
    val GroupId: Int,
    val Id: Int,
    val IsDefault: Int,
    val Mac: String,
    val RelationId: Int,
    val Status: Int,
    val UpdateDate: String,
    val UpdateOwn: String,

    var isSelected:Boolean
)


