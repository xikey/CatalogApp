package com.zikey.android.razancatalogapp.repo.iRepo

import com.zikey.android.razancatalogapp.model.wrapper.CompanyInformationWrapper
import io.reactivex.rxjava3.core.Single

interface ICompany {

    fun getInfo(): Single<CompanyInformationWrapper>

}