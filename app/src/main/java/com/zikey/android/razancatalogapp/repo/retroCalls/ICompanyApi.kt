package com.zikey.android.razancatalogapp.repo.retroCalls


import com.zikey.android.razancatalogapp.model.ProductSubGroup
import com.zikey.android.razancatalogapp.model.wrapper.*
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface ICompanyApi {

    @GET("organization/AppInformation")
    fun getInfo(): Single<CompanyInformationWrapper>

}