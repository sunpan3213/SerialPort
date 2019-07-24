package com.zkxl.mark.model.bean

/**
 * Create by Panda on 2019/4/15
 */
data class InRequestBean(val dtoInfo: IRB){

}

data class IRB(val EnterpriseId:String,val EnterpriseCode:String,val GroupId:String,val GroupCode:String,val TagTypeId:String,val TagClass:String,val UserId:String,val CreateOwn:String,val TagIds:List<String>){}