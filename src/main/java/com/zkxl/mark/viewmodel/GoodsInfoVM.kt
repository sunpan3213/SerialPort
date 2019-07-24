package com.zkxl.mark.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.zkxl.mark.model.bean.*
import com.zkxl.mark.model.net.RetrofitUtils
import okhttp3.RequestBody

/**
 * Create by Panda on 2019/4/4
 */
class GoodsInfoVM : ViewModel() {

    val liveData: MutableLiveData<Bean<GoodsInfoBean>> = MutableLiveData()

    fun getGoodsInfo(body: GoodsInfoRequestBean): MutableLiveData<Bean<GoodsInfoBean>> {

        liveData.postValue(Bean.loading(null))

        RetrofitUtils.getGoodsInfo(
            body,
            { t ->
                when (t.Code) {
                    1 -> liveData.postValue(Bean.success(t.Value))
                    else -> liveData.postValue(Bean.error(t.Message, t.Value))
                }
            },
            { ex ->
                liveData.postValue(Bean.error(ex.message!!, null))
            }
        )
        return liveData
    }

}