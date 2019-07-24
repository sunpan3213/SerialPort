package com.zkxl.mark.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.view.KeyEvent
import android.widget.Toast
import android_serialport_api.SendDataUtils
import android_serialport_api.entity.ReaderInfoRepEntity
import android_serialport_api.entity.SendTagRepEntity
import com.zkxl.mark.R
import com.zkxl.mark.base.BaseActivity
import com.zkxl.mark.base_utils.DialogUtils
import com.zkxl.mark.base_utils.ScanHelper
import com.zkxl.mark.base_utils.Staticc
import com.zkxl.mark.model.bean.*
import com.zkxl.mark.viewmodel.*
import kotlinx.android.synthetic.main.activity_in.*
import kotlinx.android.synthetic.main.activity_in.in_tagType_RL
import kotlinx.android.synthetic.main.activity_in.in_tagType_T
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class InActivity : BaseActivity() {

    var markId = ""//当前读取的价签id
    val markIds = arrayListOf<String>()//入库价签id集合

    lateinit var stationVM: StationVM
    lateinit var templateVM: TemplateVM
    lateinit var inTagVM: InTagVM
    lateinit var goodsInfoVM: GoodsInfoVM
    lateinit var bindTagVM: BindTagVM
    lateinit var updateTagVM: UpdateTagVM

    var stationList = arrayListOf<StationBean>()
    var AllmodelList = arrayListOf<TemplateBean>()
    var modelList = arrayListOf<TemplateBean>()
    val typeList = arrayListOf<InTagType>()

    var station: StationBean? = null
    var model: TemplateBean? = null
    var inTagType: InTagType? = null

    var markString: String? = null

    var bindList = arrayListOf<BRB>()

    override fun getLayoutId(): Int {
        return R.layout.activity_in
    }

    override fun initData() {
        initStationVM()
        initTemplateVM()
        initTagTypeVM()
        initGoodsInfoVM()
        initBindTagVM()
        initUpdateTagVM()

        //获取读头信息
        getReaderInfo()

        val requestBean = StationRequestBean(SRB(Staticc.account.EnterpriseId, Staticc.account.GroupId.toString()))
        stationVM.getAPList(requestBean)
        templateVM.getTemplateList(TemplateRequestBean(TRB(Staticc.account.EnterpriseId)))
        inTagVM.getTagList()
    }

    override fun initEvent() {
        select_back_Img.setOnClickListener {
            finish()
        }
        bt_read.setOnClickListener {
            //todo
            if (markString == null) {
                toast("请先选择基站")
            } else if (markString!!.matches(Regex("^[a-fA-F0-9]+$"))){
                SendDataUtils.updateMark(markString)
            }else {
                toast("请确认基站信息无误")
            }
        }
        bt.setOnClickListener {
            if (tv_state.text.contains("BOOT")) {
                toast("请确认读头状态")
            }
            if (bind_station_T.text.equals("基站") || bind_model_T.text.equals("模板") || in_tagType_T.text.equals("型号")) {
                toast("请先选择类型")
            } else {
                bind()
            }
        }
        bind_station_RL.setOnClickListener {
            DialogUtils.stationDialog(this, stationList) { adapter, _, position ->
                station = adapter.getItem(position)
                bind_station_T.text = station?.ApId
                val name = station?.ApName
                if (name != null && name.length >= 20) {
                    markString = name.substring(name.length - 20, name.length).toUpperCase()
                }
                for (value in stationList) {
                    value.isSelected = position == stationList.indexOf(value)
                }
            }
        }
        bind_model_RL.setOnClickListener {
            DialogUtils.templateDialog(this, modelList) { adapter, _, position ->
                model = adapter.getItem(position)
                bind_model_T.text = model?.Name
                for (value in modelList) {
                    value.isSelected = position == modelList.indexOf(value)
                }
            }
        }
        in_tagType_RL.setOnClickListener {
            DialogUtils.tagTypeDialog(this, typeList) { adapter, _, position ->
                inTagType = adapter.getItem(position)
                in_tagType_T.text = inTagType?.tagType
                for (value in typeList) {
                    value.isSelected = position == typeList.indexOf(value)
                }
            }
        }
    }

    private fun getReaderInfo() {
        SendDataUtils.readerInfo()
    }

    private fun initStationVM() {
        stationVM = ViewModelProviders.of(this).get(StationVM::class.java)
        val observer = Observer<Bean<List<StationBean>>> { res ->
            when (res?.Code) {
                Bean.SUCCESS -> {
                    finishLoading()
                    if (res.Value != null) {
                        for (value in res.Value) {
                            stationList.add(value)
                        }
                        if (stationList.size == 0) {
                            toast(getResources().getString(R.string.server_error))
                        } else if (stationList.size == 1) {
                            station = stationList.get(0)
                            station?.isSelected = true
                            bind_station_T.text = station?.ApId
                        }
                    }

                }
                Bean.ERROR -> {
                    finishLoading()
                    toast(getResources().getString(R.string.request_fail))
                }
                Bean.LOADING -> showLoading()
            }
        }
        stationVM.liveData.observe(this, observer)
    }

    private fun initTemplateVM() {
        templateVM = ViewModelProviders.of(this).get(TemplateVM::class.java)
        val observer = Observer<Bean<List<TemplateBean>>> { res ->
            when (res?.Code) {
                Bean.SUCCESS -> {
                    finishLoading()
                    if (res.Value != null) {
                        for (value in res.Value) {
                            AllmodelList.add(value)
                        }
                        if (AllmodelList.size == 0) {
                            toast(getResources().getString(R.string.server_error))
                        } else if (AllmodelList.size == 1) {
                            model = AllmodelList.get(0)
                            model?.isSelected = true
                            bind_model_T.text = model?.Name
                        }
                    }
                }
                Bean.ERROR -> {
                    finishLoading()
                    toast(getResources().getString(R.string.request_fail))
                }
                Bean.LOADING -> showLoading()
            }
        }
        templateVM.liveData.observe(this, observer)
    }

    private fun initTagTypeVM() {
        inTagVM = ViewModelProviders.of(this).get(InTagVM::class.java)
        val observer = Observer<Bean<List<String>>> { res ->
            when (res?.Code) {
                Bean.SUCCESS -> {
                    finishLoading()
                    if (res.Value != null) {
                        for (str in res.Value) {
                            typeList.add(InTagType(str, false))
                        }
                        if (typeList.size == 1) {
                            inTagType = typeList[0]
                            inTagType!!.isSelected = true
                            in_tagType_T.text = inTagType!!.tagType
                        }
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
        inTagVM.liveData.observe(this, observer)

        //入库
        val inTagObserve = Observer<Bean<List<String>>> { res ->
            when (res?.Code) {
                Bean.SUCCESS -> {
                    finishLoading()
                    toast(getString(R.string.in_success))
                }
                Bean.OTHER -> {
                    finishLoading()
                    if ("Fail".equals(res.Message)) {
                        toast(getString(R.string.in_fail))
                    } else {
                        toast(getString(R.string.in_exist))
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
        inTagVM.inData.observe(this, inTagObserve)
    }

    private fun initGoodsInfoVM() {
        goodsInfoVM = ViewModelProviders.of(this).get(GoodsInfoVM::class.java)
        val observer = Observer<Bean<GoodsInfoBean>> { res ->
            when (res?.Code) {
                Bean.SUCCESS -> {
                    finishLoading()
                    val goodsInfoBean = res.Value
                    bindList.add(
                        BRB(
                            Staticc.account.EnterpriseId,
                            Staticc.account.Enterprise.EnterpriseCode,
                            markId,
                            goodsInfoBean?.GoodsId.toString(),
                            goodsInfoBean?.ItemNumber,
                            null,
                            null,
                            Staticc.account.UserId.toString(),
                            Staticc.account.UserName,
                            null,
                            null
                        )
                    )
                    tv.append("标签编码: $markId\n")
                    tv.append("商品名称: ${goodsInfoBean?.Name}\n")
                    tv.append("条码/货号：${goodsInfoBean?.Barcode}\n")
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
        goodsInfoVM.liveData.observe(this, observer)
    }

    private fun initBindTagVM() {
        bindTagVM = ViewModelProviders.of(this).get(BindTagVM::class.java)
        val observer = Observer<Bean<List<String>>> { res ->
            when (res?.Code) {
                Bean.SUCCESS -> {
                    finishLoading()
                    bindList.clear()
                    showUpdateAlert()
                }
                Bean.OTHER -> {
                    finishLoading()
                    when (res.Message) {
                        "FindNull" -> toast(getString(R.string.bind_no_in))
                        "Fail" -> toast(getString(R.string.bind_fail))
                        else -> toast(getString(R.string.bind_exist))
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
        bindTagVM.liveData.observe(this, observer)
    }

    private fun initUpdateTagVM() {
        updateTagVM = ViewModelProviders.of(this).get(UpdateTagVM::class.java)
        val observer = Observer<Bean<String>> { res ->
            when (res?.Code) {
                Bean.SUCCESS -> {
                    finishLoading()
                    toast(getString(R.string.bind_update_success))
                    markIds.clear()
                    tv.text = ""
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
        updateTagVM.liveData.observe(this, observer)
    }

    private fun showUpdateAlert() {
        AlertDialog.Builder(mContext/*, R.style.MyAlertDialogStyle*/)
            .setMessage(getString(R.string.bind_update))
            .setNegativeButton(getString(R.string.bind_update_no)) { dialog, which ->
                toast(getString(R.string.bind_success))
                markIds.clear()
                tv.text = ""
            }
            .setPositiveButton(getString(R.string.bind_update_yes)) { dialog, which ->
                updateMarks(markIds)
            }
            .show()
    }

    private fun updateMarks(ids: ArrayList<String>) {
        val updateRequestBean = UpdateRequestBean(
            URB(
                Staticc.account.EnterpriseId,
                Staticc.account.Enterprise.EnterpriseCode,
                Staticc.account.Group.GroupCode,
                Staticc.account.Group.GroupId.toString(),
                ids,
                Staticc.account.UserId.toString(),
                Staticc.account.UserName
            )
        )
        updateTagVM.updateTag(updateRequestBean)
    }

    override fun initView() {
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    //处理扫描枪的输出
    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {//返回键退出界面
            finish()
            return true
        }
        ScanHelper.checkLetterStatus(event)
        if (KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction()) {
            ScanHelper.keyCodeToNum(event.keyCode)
            //获取商品信息
            getGoodsInfo(ScanHelper.sb.toString())
            ScanHelper.sb.clear()
            return true
        }
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            return ScanHelper.keyCodeToNum(event.keyCode)
        }
        return super.dispatchKeyEvent(event)
    }

    fun getGoodsInfo(code: String) {
        goodsInfoVM.getGoodsInfo(GoodsInfoRequestBean(GIRB(Staticc.account.EnterpriseId, code)))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun receive(readerInfoRepEntity: ReaderInfoRepEntity) {
        if (readerInfoRepEntity.isBoot) {
            tv_state.text = "BOOT模式---版本${readerInfoRepEntity.version}"
        } else {
            tv_state.text = "APP模式---版本${readerInfoRepEntity.version}"
            val toast = Toast.makeText(this, "请读取价签入库和扫描商品条码绑定!", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun receive(sendTagRepEntity: SendTagRepEntity) {
        for (item in typeList) {
            if (item.tagType.contains(sendTagRepEntity.type)) {
                item.isSelected = true
                in_tagType_T.text = item.tagType
                inTagType = item
            }
        }
        modelList.clear()
        for (model in AllmodelList) {
            if (model.FileName.contains(sendTagRepEntity.type)) {
                modelList.add(model)
            }
        }
        markId = sendTagRepEntity.uid
        if (!markIds.contains(markId)) markIds.add(markId)
        val inRequestBean = InRequestBean(
            IRB(
                Staticc.account.EnterpriseId,
                Staticc.account.Enterprise.EnterpriseCode,
                Staticc.account.Group.GroupId.toString(),
                Staticc.account.Group.GroupCode,
                inTagType!!.tagType,
                Staticc.TagClass,
                Staticc.account.UserId.toString(),
                Staticc.account.UserName,
                markIds
            )
        )
        //价签id入库
        inTagVM.inTag(inRequestBean)
    }

    fun bind() {
        for (item in bindList) {
            item.ApId = station?.ApId
            item.Id = station?.Id.toString()
            item.TemplateId = model?.TemplateId.toString()
            item.TemplateCode = model?.TemplateCode
        }
        val bindRequestBean = BindRequestBean(bindList)
        bindTagVM.bindTag(bindRequestBean)
    }

}
