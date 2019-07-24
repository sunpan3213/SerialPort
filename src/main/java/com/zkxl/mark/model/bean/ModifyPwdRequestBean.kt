package com.zkxl.mark.model.bean

/**
 * Create by Panda on 2019/4/4
 */
data class ModifyPwdRequestBean(val dtoInfo: MRB) {

}

data class MRB(val UserId: String, val OldPassword: String, val NewPassword: String) {

}