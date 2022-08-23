package com.zikey.android.razancatalogapp.repo.server_repo

import android.content.Context
import com.zikey.android.razancatalogapp.model.wrapper.AdvertisesWrapper
import com.zikey.android.razancatalogapp.model.wrapper.ProductMainGroupsWrapper
import com.zikey.android.razancatalogapp.model.wrapper.ProductSubGroupsWrapper
import com.zikey.android.razancatalogapp.model.wrapper.ProductsWrapper
import com.zikey.android.razancatalogapp.repo.apiClient.ServerApiClient
import com.zikey.android.razancatalogapp.repo.iRepo.IProduct
import com.zikey.android.razancatalogapp.repo.retroCalls.IProductApi
import io.reactivex.rxjava3.core.Single

class ProductServerRepo :IProduct{

    override fun getProducts(
        context: Context?,
        mainGroup: Long,
        subGroup: Long,

        ): Single<ProductsWrapper> {
        val api = ServerApiClient.getClientWithHeader(context)!!.create(IProductApi::class.java)
        return api.getProducts(mainGroup, subGroup)
    }

    override fun getSpecialProducts(
        context: Context?,

        ): Single<ProductsWrapper> {
        val api = ServerApiClient.getClientWithHeader(context)!!.create(IProductApi::class.java)
        return api.specialProducts()
    }


    override fun getProducts_mainGroups(
        context: Context?
        ): Single<ProductMainGroupsWrapper> {
        val api = ServerApiClient.getClientWithHeader(context)!!.create(IProductApi::class.java)
        return api.getProducts_mainGroups()
    }

    override fun getProducts_subGroups(
        context: Context?,
        mainGroup: Long,
    ): Single<ProductSubGroupsWrapper> {
        val api = ServerApiClient.getClientWithHeader(context)!!.create(IProductApi::class.java)
        return api.getProducts_subGroups(mainGroup)
    }


    override fun getAdvertises(
        context: Context?,

        ): Single<AdvertisesWrapper> {
        val api = ServerApiClient.getClientWithHeader(context)!!.create(IProductApi::class.java)
        return api.advertises()
    }


}