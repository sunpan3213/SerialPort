package com.zkxl.mark.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.zkxl.mark.model.bean.Bean
import com.zkxl.mark.model.bean.LoginBean
import com.zkxl.mark.model.bean.LoginRequestBean
import com.zkxl.mark.model.bean.ModifyPwdRequestBean
import com.zkxl.mark.model.net.RetrofitUtils
import okhttp3.RequestBody

/**
 * Create by Panda on 2019/4/4
 */
class ModifyPwdVM : ViewModel() {

    val liveData: MutableLiveData<Bean<String>> = MutableLiveData()

    fun modify(body: ModifyPwdRequestBean): MutableLiveData<Bean<String>> {

        liveData.postValue(Bean.loading(null))

        RetrofitUtils.modifyPwd(
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