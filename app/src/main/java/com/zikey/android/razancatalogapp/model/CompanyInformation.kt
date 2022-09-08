package com.zikey.android.razancatalogapp.model

import com.google.gson.annotations.SerializedName

data class CompanyInformation(

    @SerializedName("ph") var phoneNumber: String?,
    @SerializedName("lt") var latitude: String?,
    @SerializedName("lg") var longitude: String?,
    @SerializedName("i")  var imageUrl: String?,
    @SerializedName("d")  var details: List<CompanyInformationDetail>?,

)
