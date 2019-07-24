package com.zkxl.mark.model.net

import com.zkxl.mark.model.bean.*
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * Create by Panda on 2019/1/15
 */
interface Apis {

    @POST("/UserService/Login")
    fun login(@Body requestBody: LoginRequestBean): Observable<Bean<LoginBean>>

    @POST("/APService/GetAPList")
    fun getApList(@Body requestBody: StationRequestBean): Observable<Bean<List<StationBean>>>

    @POST("/TemplateService/GetEnterpriseTemplateList")
    fun getTemplateList(@Body requestBody: TemplateRequestBean): Observable<Bean<List<TemplateBean>>>

    @POST("/UserService/ChangePassword")
    fun modifyPwd(@Body requestBody: ModifyPwdRequestBean): Observable<Bean<String>>

    @POST("/TagService/TagOut")
    fun outTag(@Body requestBody: OutRequestBean): Observable<Bean<List<String>>>

    @GET("/TagService/GetTagTypeList")
    fun getTagList(): Observable<Bean<List<String>>>

    @POST("/TagService/TagIn")
    fun inTag(@Body requestBody: InRequestBean): Observable<Bean<List<String>>>

    @GET("/TagService/GetTagRefreshLog")
    fun getUpdateHistoryList(@QueryMap map:HashMap<String,String>): Observable<Bean<List<HistoryBean>>>

    @POST("/GoodsService/GetGoodsInfo")
    fun getGoodsInfo(@Body goodsInfoRequestBean: GoodsInfoRequestBean):Observable<Bean<GoodsInfoBean>>

    @POST("/TagService/TagBind")
    fun bindTag(@Body bindRequestBean: BindRequestBean):Observable<Bean<List<String>>>

    @POST("/TagService/TagRefresh")
    fun updateTag(@Body updateRequestBean: UpdateRequestBean):Observable<Bean<String>>

    @POST("/TagService/TagUnbind")
    fun unbindTag(@Body unBindRequestBean: UnBindRequestBean):Observable<Bean<List<String>>>

}