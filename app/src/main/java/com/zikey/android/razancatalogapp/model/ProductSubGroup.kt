package com.zikey.android.razancatalogapp.model

import com.google.gson.annotations.SerializedName

data class ProductSubGroup(
    @SerializedName("i") val id: Long?,
    @SerializedName("n") var name: String?,
    @SerializedName("p") var products: List<Product>?,
    @SerializedName("iu") val imageURL1: String?,
    @SerializedName("iu2") val imageURL2: String?,
    var isSelected: Boolean,

    )
