package com.zikey.android.razancatalogapp.model.wrapper

import com.google.gson.annotations.SerializedName
import com.zikey.android.razancatalogapp.model.Advertise

data class AdvertisesWrapper(

    @SerializedName("a") var datas: List<Advertise>?

) : ServerAnswer()