package com.zikey.android.razancatalogapp.repo.iRepo

import android.content.Context
import com.zikey.android.razancatalogapp.model.wrapper.AdvertisesWrapper
import com.zikey.android.razancatalogapp.model.wrapper.ProductMainGroupsWrapper
import com.zikey.android.razancatalogapp.model.wrapper.ProductSubGroupsWrapper
import com.zikey.android.razancatalogapp.model.wrapper.ProductsWrapper
import com.zikey.android.razancatalogapp.repo.apiClient.ServerApiClient
import com.zikey.android.razancatalogapp.repo.retroCalls.IProductApi
import com.zikey.android.razancatalogapp.repo.tools.IRepo
import com.zikey.android.razancatalogapp.repo.tools.IRepoCallBack
import io.reactivex.rxjava3.core.Single

interface IProduct : IRepo<ProductsWrapper> {

    fun getProducts(
        mainGroup: Long,
        subGroup: Long,

        ): Single<ProductsWrapper>

    fun getSpecialProducts(
        ): Single<ProductsWrapper>


    fun getProducts_mainGroups(
    ): Single<ProductMainGroupsWrapper>


    fun getProducts_subGroups(
        mainGroup: Long,
    ): Single<ProductSubGroupsWrapper>



    fun getAdvertises(
        ): Single<AdvertisesWrapper>

    fun searchProducts(
        keySearch: String,
    ): Single<ProductsWrapper>





}