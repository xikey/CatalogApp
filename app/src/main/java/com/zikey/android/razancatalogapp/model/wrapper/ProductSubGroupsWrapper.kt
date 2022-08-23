package com.zikey.android.razancatalogapp.model.wrapper

import com.google.gson.annotations.SerializedName
import com.zikey.android.razancatalogapp.model.ProductSubGroup

data class ProductSubGroupsWrapper(

    @SerializedName("G") var list: ArrayList<ProductSubGroup>?

) : ServerAnswer()