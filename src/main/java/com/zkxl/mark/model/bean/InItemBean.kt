package com.zkxl.mark.model.bean

/**
 * Create by Panda on 2019/4/15
 */
data class InItemBean(val tagId: String, var red: Boolean) {

    constructor(tagId: String) : this(tagId, false)

}