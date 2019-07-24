package com.zkxl.mark.model.bean

/**
 * Create by Panda on 2019/4/3
 */
data class LoginBean(
    val CreateDate: String,
    val CreateOwn: String,
    val Email: String,
    val Enterprise: Enterprise,
    val EnterpriseCode: String,
    val EnterpriseId: String,
    val Group: Group,
    val GroupCode: String,
    val GroupId: Int,
    val Language: String,
    val LoginDate: String,
    val LoginName: String,
    val LoginType: Int,
    val Mobile: String,
    val Password: Any,
    val Remark: String,
    val RoleId: Int,
    val Roles: List<Any>,
    val SearchKeyword: String,
    val Status: Int,
    val UpdateDate: String,
    val UpdateOwn: String,
    val UserId: Int,
    val UserName: String,
    val UserType: Int
)

data class Group(
    val Address: String,
    val Contact: String,
    val CreateDate: String,
    val CreateOwn: String,
    val EnterpriseCode: String,
    val EnterpriseId: String,
    val GroupCode: String,
    val GroupId: Int,
    val IsProxy: Int,
    val Level: Int,
    val Name: String,
    val ParentId: Int,
    val Remark: String,
    val Route: String,
    val Sequence: Int,
    val Status: Int,
    val UpdateDate: String,
    val UpdateOwn: String
)

data class Enterprise(
    val Address: String,
    val Banner: String,
    val Contact: String,
    val CreateDate: String,
    val CreateOwn: String,
    val Email: String,
    val EnterpriseCode: String,
    val EnterpriseId: String,
    val Groups: Any,
    val Latitude: String,
    val Logo: String,
    val Longitude: String,
    val Name: String,
    val Remark: String,
    val SearchKeyword: Any,
    val SecretKey: String,
    val Status: Int,
    val Type: Int,
    val UpdateDate: String,
    val UpdateOwn: String,
    val Users: Any
)