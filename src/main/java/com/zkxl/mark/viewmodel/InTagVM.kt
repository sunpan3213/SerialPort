package com.zkxl.mark.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.zkxl.mark.model.bean.*
import com.zkxl.mark.model.net.RetrofitUtils
import okhttp3.RequestBody

/**
 * Create by Panda on 2019/4/4
 */
class InTagVM : ViewModel() {

    val liveData: MutableLiveData<Bean<List<String>>> = MutableLiveData()
    val inData: MutableLiveData<Bean<List<String>>> = MutableLiveData()

    fun getTagList(): MutableLiveData<Bean<List<String>>> {

        liveData.postValue(Bean.loading(null))

        RetrofitUtils.getTagList(
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

    fun inTag(body: InRequestBean): MutableLiveData<Bean<List<String>>> {

        inData.postValue(Bean.loading(null))

        RetrofitUtils.inTag(
            body,
            { t ->
                when (t.Code) {
                    1 -> inData.postValue(Bean.success(t.Value))
                    2, 3 -> inData.postValue(Bean.other(t.Message, t.Value))
                    else -> inData.postValue(Bean.error(t.Message, t.Value))
                }
            },
            { ex ->
                inData.postValue(Bean.error(ex.message!!, null))
            }
        )
        return inData
    }

}