package com.zkxl.mark.model.bean

/**
 * Create by Panda on 2019/4/15
 */
data class OutRequestBean(val dtoInfo: ORB){

}

data class ORB(val EnterpriseId:String,val UserId:String,val CreateOwn:String,val TagIds:List<String>){}