package com.zikey.android.razancatalogapp.model.wrapper

import com.google.gson.annotations.SerializedName
import com.zikey.android.razancatalogapp.model.CompanyInformation
import com.zikey.android.razancatalogapp.model.ProductMainGroup
import com.zikey.android.razancatalogapp.model.ProductSubGroup

data class CompanyInformationWrapper(

    @SerializedName("i") var data: CompanyInformation

) : ServerAnswer()