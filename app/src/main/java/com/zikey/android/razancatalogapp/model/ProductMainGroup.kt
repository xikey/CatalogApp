package com.zikey.android.razancatalogapp.model

import com.google.gson.annotations.SerializedName

data class ProductMainGroup(

    @SerializedName("i") val id: Long?,
    @SerializedName("n") var name: String?,
    @SerializedName("s") var subGroups: List<ProductSubGroup>?,
    @SerializedName("iu") val imageURL1: String?,
    @SerializedName("iu2") val imageURL2: String?,
    @SerializedName("sgc") val subGroupsCount: Long?,
    @SerializedName("pc") val productsCount: Long?,
    var isSelected: Boolean,

)