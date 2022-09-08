package com.zikey.android.razancatalogapp.repo.server_repo

import com.zikey.android.razancatalogapp.model.wrapper.CompanyInformationWrapper
import com.zikey.android.razancatalogapp.repo.apiClient.ServerApiClient
import com.zikey.android.razancatalogapp.repo.iRepo.ICompany
import com.zikey.android.razancatalogapp.repo.retroCalls.ICompanyApi
import io.reactivex.rxjava3.core.Single

class CompanyServerRepo :ICompany {

    override fun getInfo(): Single<CompanyInformationWrapper> {
        val api = ServerApiClient.getClientWithHeader()!!.create(ICompanyApi::class.java)
        return api.getInfo()
    }


}