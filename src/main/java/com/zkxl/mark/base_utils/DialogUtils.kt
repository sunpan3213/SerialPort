package com.zkxl.mark.base_utils

import android.app.Dialog
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.zkxl.mark.R
import com.zkxl.mark.adapter.InTagTypeAdapter
import com.zkxl.mark.adapter.StationAdapter
import com.zkxl.mark.adapter.TemplateAdapter
import com.zkxl.mark.model.bean.InTagType
import com.zkxl.mark.model.bean.StationBean
import com.zkxl.mark.model.bean.TemplateBean

/**
 * Create by Panda on 2019/4/19
 */
object DialogUtils {

    fun showCommonDialog(context: Context, message: String, btnText: String, sure: () -> Unit, diss: () -> Unit) {
        val dialog = Dialog(context, R.style.ActionSheetDialogStyle)
        dialog.setContentView(R.layout.dialog_common)
        val window = dialog.window
        val params = window?.attributes
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        params?.width = windowManager.defaultDisplay.width / 2    //屏幕宽度的一半,可自行修改
        params?.height = WindowManager.LayoutParams.WRAP_CONTENT
        params?.gravity = Gravity.CENTER    //显示位置
        window?.attributes = params

        val textView = window?.findViewById<TextView>(R.id.message)
        val commit = window?.findViewById<Button>(R.id.yes)
        val cancel = window?.findViewById<Button>(R.id.no)
        textView?.text = message
        commit?.text = btnText

        commit?.setOnClickListener {
            dialog.dismiss()
            sure.invoke()
        }

        cancel?.setOnClickListener {
            dialog.dismiss()
            diss.invoke()
        }

        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    fun stationDialog(context: Context, data: ArrayList<StationBean>, onClick: (StationAdapter, View, Int) -> Unit) {
        val dialog = Dialog(context, R.style.ActionSheetDialogStyle)
        dialog.setContentView(R.layout.dialog_stations)
        val window = dialog.window
        val params = window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.WRAP_CONTENT
        params?.gravity = Gravity.CENTER    //显示位置
        window?.attributes = params
        window?.decorView?.setPadding(0, 0, 0, 0)

        window?.findViewById<ImageView>(R.id.dialogStations_close_IMG)?.setOnClickListener {
            dialog.dismiss()
        }
        val recyclerView = window?.findViewById<RecyclerView>(R.id.rv)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        val stationAdapter = StationAdapter(R.layout.item_station, data)
        recyclerView?.adapter = stationAdapter
        stationAdapter.setOnItemChildClickListener { adapter, view, position ->
            onClick.invoke(adapter as StationAdapter, view, position)
            dialog.dismiss()
        }

        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    fun templateDialog(context: Context, data: ArrayList<TemplateBean>, onClick: (TemplateAdapter, View, Int) -> Unit) {
        val dialog = Dialog(context, R.style.ActionSheetDialogStyle)
        dialog.setContentView(R.layout.dialog_models)
        val window = dialog.window
        val params = window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.WRAP_CONTENT
        params?.gravity = Gravity.CENTER    //显示位置
        window?.attributes = params
        window?.decorView?.setPadding(0, 0, 0, 0)

        window?.findViewById<ImageView>(R.id.dialogModels_close_IMG)?.setOnClickListener {
            dialog.dismiss()
        }
        val recyclerView = window?.findViewById<RecyclerView>(R.id.rv)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        val templateAdapter = TemplateAdapter(R.layout.item_model, data)
        recyclerView?.adapter = templateAdapter
        templateAdapter.setOnItemChildClickListener { adapter, view, position ->
            onClick.invoke(adapter as TemplateAdapter, view, position)
            dialog.dismiss()
        }

        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    fun tagTypeDialog(context: Context, data: ArrayList<InTagType>, onClick: (InTagTypeAdapter, View, Int) -> Unit) {
        val dialog = Dialog(context, R.style.ActionSheetDialogStyle)
        dialog.setContentView(R.layout.dialog_tagtypes)
        val window = dialog.window
        val params = window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.WRAP_CONTENT
        params?.gravity = Gravity.CENTER    //显示位置
        window?.attributes = params
        window?.decorView?.setPadding(0, 0, 0, 0)

        window?.findViewById<ImageView>(R.id.dialogTagTypes_close_IMG)?.setOnClickListener {
            dialog.dismiss()
        }
        val recyclerView = window?.findViewById<RecyclerView>(R.id.rv)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        val inTagTypeAdapter = InTagTypeAdapter(R.layout.item_tag_type, data)
        recyclerView?.adapter = inTagTypeAdapter
        inTagTypeAdapter.setOnItemChildClickListener { adapter, view, position ->
            onClick.invoke(adapter as InTagTypeAdapter, view, position)
            dialog.dismiss()
        }

        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
}