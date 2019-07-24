package com.zkxl.mark.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.view.Gravity
import android.widget.Toast
import android_serialport_api.SendDataUtils
import android_serialport_api.entity.ReaderInfoRepEntity
import android_serialport_api.entity.SendTagRepEntity
import com.zkxl.mark.R
import com.zkxl.mark.base.BaseActivity
import com.zkxl.mark.base_utils.Staticc
import com.zkxl.mark.model.bean.*
import com.zkxl.mark.viewmodel.OutTagVM
import com.zkxl.mark.viewmodel.UnBindTagVM
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_out.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.TimeUnit

class OutActivity : BaseActivity() {

    lateinit var unbindTagVM: UnBindTagVM
    lateinit var outTagVM: OutTagVM
    val tagIds = arrayListOf<String>()

    override fun getLayoutId(): Int {
        return R.layout.activity_out
    }

    override fun initView() {
        EventBus.getDefault().register(this)
    }

    override fun initData() {
        //获取读头信息
        getReaderInfo()

        initUnBindTagVM()
        initOutTagVM()
    }

    override fun initEvent() {
        super.initEvent()

        select_back_Img.setOnClickListener {
            finish()
        }

        bt.setOnClickListener {
            SendDataUtils.updateMark(null)
        }
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    private fun getReaderInfo() {
        SendDataUtils.readerInfo()
    }

    private fun initUnBindTagVM() {
        unbindTagVM = ViewModelProviders.of(this).get(UnBindTagVM::class.java)
        val observer = Observer<Bean<List<String>>> { res ->
            when (res?.Code) {
                Bean.SUCCESS -> {
                    finishLoading()
                    toast(getString(R.string.unbind_success))
                    //出库
                    Observable.timer(500, TimeUnit.MILLISECONDS).subscribe {
                        outTagVM.out(OutRequestBean(ORB(Staticc.account.EnterpriseId,
                                    Staticc.account.UserId.toString(), Staticc.account.UserName, tagIds)
                            )
                        )
                    }

                }
                Bean.OTHER -> {
                    finishLoading()
                    if ("FindNull".equals(res.Message)) {
                        toast(getString(R.string.out_no_in))
                    } else {
                        toast(getString(R.string.unbind_fail))
                    }

                }
                Bean.ERROR -> {
                    finishLoading()
                    toast(res.Message)
                }
                Bean.LOADING -> {
                    showLoading()
                }
            }
        }
        unbindTagVM.liveData.observe(this, observer)
    }

    private fun initOutTagVM() {
        outTagVM = ViewModelProviders.of(this).get(OutTagVM::class.java)
        val observer = Observer<Bean<List<String>>> { res ->
            when (res?.Code) {
                Bean.SUCCESS -> {
                    finishLoading()
                    toast(getString(R.string.out_success))
                    tagIds.clear()
                }
                Bean.OTHER -> {
                    finishLoading()
                    if ("FindNull".equals(res.Message)) {
                        toast(getString(R.string.out_no_in))
                    } else {
                        toast(getString(R.string.out_fail))
                    }
                }
                Bean.ERROR -> {
                    finishLoading()
                    toast(getString(R.string.out_fail))
                }
                Bean.LOADING -> {
                    showLoading()
                }
            }
        }
        outTagVM.liveData.observe(this, observer)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun receive(readerInfoRepEntity: ReaderInfoRepEntity) {
        if (readerInfoRepEntity.isBoot) {
            tv_state.text = "BOOT模式---版本${readerInfoRepEntity.version}"
        } else {
            tv_state.text = "APP模式---版本${readerInfoRepEntity.version}"
            val toast = Toast.makeText(this, "请读取价签出库!", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun receive(sendTagRepEntity: SendTagRepEntity) {
        val markId = sendTagRepEntity.uid
        tagIds.add(markId)
        val list = arrayListOf<UBRB>()
        list.add(
            UBRB(
                Staticc.account.EnterpriseId,
                Staticc.account.Group.GroupId.toString(),
                markId,
                Staticc.account.UserId.toString(),
                Staticc.account.UserName
            )
        )
        //解绑
        val unBindRequestBean = UnBindRequestBean(list)
        unbindTagVM.unbindTag(unBindRequestBean)
    }

}
