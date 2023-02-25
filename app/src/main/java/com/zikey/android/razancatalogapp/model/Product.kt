package com.zikey.android.razancatalogapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Product(

    @SerializedName("i") var id: Long?,
    @SerializedName("c") var code: Long?,
    @SerializedName("n") var name: String?,
    @SerializedName("b") var barcode: String?,
    @SerializedName("oc") var orderCount: Int?,
    @SerializedName("d") var descroption: String?,
    @SerializedName("d2") var descroption2: String?,
    @SerializedName("p") var price_WithoutDiscount: Long?,
    @SerializedName("cp") var price_WithDiscount: Long?,
    @SerializedName("iu") var imageUrl: String?,
    @SerializedName("iu2") var imageLargeUrls: List<String>?,
    @SerializedName("av") var availability: Double?,
    @SerializedName("pg") var discountPercentage: Double,
    @SerializedName("f1") var feature1: String?,
    @SerializedName("f2") var feature2: String?,
    @SerializedName("f3") var feature3: String?,
    @SerializedName("f4") var feature4: String?,
    @SerializedName("f5") var feature5: String?,
    @SerializedName("br") var subGroup: String?,
    @SerializedName("gr") var mainGroup: String,
    @SerializedName("qip") var boxCapacity: Double,
    @SerializedName("w") var weight: Double,
    @SerializedName("s") var size: Double,
    @SerializedName("mu") var mainUnit: String,
    @SerializedName("su") var subUnit: String,

    ) : Serializable
