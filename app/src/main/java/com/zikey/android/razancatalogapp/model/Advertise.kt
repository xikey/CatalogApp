package com.zikey.android.razancatalogapp.model

import com.google.gson.annotations.SerializedName

data class Advertise(
    @SerializedName("i") var id: Long?,
    @SerializedName("d") var description: String?,
    @SerializedName("u") var uri: String?,
    @SerializedName("img") var imageUrl: String?,
    @SerializedName("exp") var expireDate: String?,
    @SerializedName("t") var type: AdvertiseType?,
)