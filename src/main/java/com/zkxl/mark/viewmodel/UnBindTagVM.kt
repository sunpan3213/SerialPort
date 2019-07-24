package com.zkxl.mark.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.zkxl.mark.model.bean.*
import com.zkxl.mark.model.net.RetrofitUtils
import okhttp3.RequestBody

/**
 * Create by Panda on 2019/4/4
 */
class UnBindTagVM : ViewModel() {

    val liveData: MutableLiveData<Bean<List<String>>> = MutableLiveData()

    fun unbindTag(body: UnBindRequestBean): MutableLiveData<Bean<List<String>>> {

        liveData.postValue(Bean.loading(null))

        RetrofitUtils.unbindTag(
            body,
            { t ->
                when (t.Code) {
                    1 -> liveData.postValue(Bean.success(t.Value))
                    0,2->liveData.postValue(Bean.other(t.Message,t.Value))
                    else -> liveData.postValue(Bean.error(t.Message, null))
                }
            },
            { ex ->
                liveData.postValue(Bean.error(ex.message!!, null))
            }
        )
        return liveData
    }

}