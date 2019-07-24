package com.zkxl.mark.model.bean

/**
 * Create by Panda on 2019/4/4
 */
data class LoginRequestBean(val dtoInfo: LRB) {

}

data class LRB(val EnterpriseCode: String, val LoginName: String, val LoginPassword: String) {

}