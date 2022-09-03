package com.zikey.android.razancatalogapp.repo.retroCalls


import com.zikey.android.razancatalogapp.model.ProductSubGroup
import com.zikey.android.razancatalogapp.model.wrapper.AdvertisesWrapper
import com.zikey.android.razancatalogapp.model.wrapper.ProductMainGroupsWrapper
import com.zikey.android.razancatalogapp.model.wrapper.ProductSubGroupsWrapper
import com.zikey.android.razancatalogapp.model.wrapper.ProductsWrapper
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface IProductApi {

    @GET("product/products")
    fun getProducts(
        @Query("maingroup") mainGroup: Long,
        @Query("subgroup") subGroup: Long,
    ): Single<ProductsWrapper>

    @GET("product/maingroups")
    fun getProducts_mainGroups(
    ): Single<ProductMainGroupsWrapper>

  @GET("product/SpecialProducts")
    fun specialProducts(
    ): Single<ProductsWrapper>

    @GET("product/subgroups")
    fun getProducts_subGroups(
        @Query("code") mainGroup: Long,
    ): Single<ProductSubGroupsWrapper>

    @GET("advertise/HeaderAdvertise")
    fun advertises(
    ): Single<AdvertisesWrapper>

    @GET("product/productsearch?")
    fun searchProducts(
        @Query("key") keySearch: String,
    ): Single<ProductsWrapper>


}