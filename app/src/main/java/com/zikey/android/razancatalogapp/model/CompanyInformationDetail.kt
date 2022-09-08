package com.zikey.android.razancatalogapp.model

import com.google.gson.annotations.SerializedName

data class CompanyInformationDetail (

    @SerializedName("n") var name: String?,
    @SerializedName("v") var value: String?

)