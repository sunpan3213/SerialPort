package com.zkxl.mark.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.zkxl.mark.model.bean.*
import com.zkxl.mark.model.net.RetrofitUtils
import okhttp3.RequestBody

/**
 * Create by Panda on 2019/4/4
 */
class BindTagVM : ViewModel() {

    val liveData: MutableLiveData<Bean<List<String>>> = MutableLiveData()

    fun bindTag(body: BindRequestBean): MutableLiveData<Bean<List<String>>> {

        liveData.postValue(Bean.loading(null))

        RetrofitUtils.bindTag(
            body,
            { t ->
                when (t.Code) {
                    1 -> liveData.postValue(Bean.success(t.Value))
                    0,2,3->liveData.postValue(Bean.other(t.Message,t.Value))
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