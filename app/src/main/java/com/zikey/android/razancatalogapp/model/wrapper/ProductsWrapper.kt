package com.zikey.android.razancatalogapp.model.wrapper

import com.google.gson.annotations.SerializedName
import com.zikey.android.razancatalogapp.model.Product

data class ProductsWrapper(

    @SerializedName("p") var products: List<Product>?

) : ServerAnswer()