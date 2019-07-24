package com.zkxl.mark.model.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * Create by Panda on 2019/4/16
 */
data class HistoryBean(val RefreshId:String,val RefreshNo:String,val Status:String,val CreateDate:String,val CreateOwn:String,val RefreshResult:List<DetailBean>){}

data class DetailBean(val TagId:String,val ApId:String,val GoodsName:String,val TagPower:String,val TagVoltage:String,val TagSignal:String,val Status:String,val CreateDate:String,val CreateOwn:String,val UpdateDate:String):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(TagId)
        parcel.writeString(ApId)
        parcel.writeString(GoodsName)
        parcel.writeString(TagPower)
        parcel.writeString(TagVoltage)
        parcel.writeString(TagSignal)
        parcel.writeString(Status)
        parcel.writeString(CreateDate)
        parcel.writeString(CreateOwn)
        parcel.writeString(UpdateDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DetailBean> {
        override fun createFromParcel(parcel: Parcel): DetailBean {
            return DetailBean(parcel)
        }

        override fun newArray(size: Int): Array<DetailBean?> {
            return arrayOfNulls(size)
        }
    }
}