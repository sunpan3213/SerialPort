package com.zkxl.mark.model.net

import com.google.gson.GsonBuilder
import com.zkxl.mark.BuildConfig
import com.zkxl.mark.base_utils.LogUtils
import com.zkxl.mark.model.bean.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Create by Panda on 2019/1/15
 */
object RetrofitUtils {

    lateinit var apis: Apis

    var DEBUG: String = "http://117.79.157.154:6160"
    var RELEASE: String = "https://powergoapi.linkgates.com:8180"

    var BASEURL: String = if (BuildConfig.DEBUG) {
        DEBUG
    } else {
        RELEASE
    }

    fun init(): Apis {

        val builder = RetrofitUrlManager.getInstance().with(OkHttpClient.Builder())
//        val builder = OkHttpClient.Builder()
        val loggingInterceptor =
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { msg -> LogUtils.e(msg) })//不重写,部分手机平板需要打开日志
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        builder.addInterceptor(loggingInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)

        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl(BASEURL)
            .client(builder.build())
            .build()
        apis = retrofit.create(Apis::class.java)
        return apis
    }

    fun doLogin(body:LoginRequestBean, observer: (bean: Bean<LoginBean>) -> Unit, error: (t: Throwable) -> Unit): Disposable {
        val disposable = apis.login(body).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer, error)
        return disposable
    }

    fun getAPList(body:StationRequestBean, observer: (bean: Bean<List<StationBean>>) -> Unit, error: (t: Throwable) -> Unit): Disposable {
        val disposable = apis.getApList(body).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer, error)
        return disposable
    }

    fun getTemplateList(body:TemplateRequestBean, observer: (bean: Bean<List<TemplateBean>>) -> Unit, error: (t: Throwable) -> Unit): Disposable {
        val disposable = apis.getTemplateList(body).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer, error)
        return disposable
    }

    fun modifyPwd(body:ModifyPwdRequestBean, observer: (bean: Bean<String>) -> Unit, error: (t: Throwable) -> Unit): Disposable {
        val disposable = apis.modifyPwd(body).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer, error)
        return disposable
    }

    fun outTag(body:OutRequestBean, observer: (bean: Bean<List<String>>) -> Unit, error: (t: Throwable) -> Unit): Disposable {
        val disposable = apis.outTag(body).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer, error)
        return disposable
    }

    fun getTagList(observer: (bean: Bean<List<String>>) -> Unit, error: (t: Throwable) -> Unit): Disposable {
        val disposable = apis.getTagList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer, error)
        return disposable
    }

    fun inTag(body:InRequestBean, observer: (bean: Bean<List<String>>) -> Unit, error: (t: Throwable) -> Unit): Disposable {
        val disposable = apis.inTag(body).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer, error)
        return disposable
    }

    fun getHistoryList(map:HashMap<String,String>,observer: (bean: Bean<List<HistoryBean>>) -> Unit, error: (t: Throwable) -> Unit): Disposable {
        val disposable = apis.getUpdateHistoryList(map).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer, error)
        return disposable
    }

    fun getGoodsInfo(body:GoodsInfoRequestBean, observer: (bean: Bean<GoodsInfoBean>) -> Unit, error: (t: Throwable) -> Unit): Disposable {
        val disposable = apis.getGoodsInfo(body).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer,error)
        return disposable
    }

    fun bindTag(body:BindRequestBean, observer: (bean: Bean<List<String>>) -> Unit, error: (t: Throwable) -> Unit): Disposable {
        val disposable = apis.bindTag(body).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer, error)
        return disposable
    }

    fun updateTag(body:UpdateRequestBean, observer: (bean: Bean<String>) -> Unit, error: (t: Throwable) -> Unit): Disposable {
        val disposable = apis.updateTag(body).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer, error)
        return disposable
    }

    fun unbindTag(body:UnBindRequestBean, observer: (bean: Bean<List<String>>) -> Unit, error: (t: Throwable) -> Unit): Disposable {
        val disposable = apis.unbindTag(body).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer, error)
        return disposable
    }
}

