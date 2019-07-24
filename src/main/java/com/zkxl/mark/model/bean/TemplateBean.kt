package com.zkxl.mark.model.bean

import android.renderscript.Sampler

/**
 * Create by Panda on 2019/4/8
 */
data class TemplateBean(
    val Content: String,
    val CreateDate: String,
    val CreateOwn: String,
    val EnterpriseCode: String,
    val EnterpriseId: String,
    val FileName: String,
    val FileUrl: String,
    val IsDefault: Int,
    val IsPublic: Int,
    val IsSystem: Int,
    val Name: String,
    val PicUrl: String,
    val TagTypes: List<String>,
    val TemplateCode: String,
    val TemplateId: Int,
    val Type: Int,
    val UpdateDate: String,
    val UpdateOwn: String,

    var isSelected:Boolean
)