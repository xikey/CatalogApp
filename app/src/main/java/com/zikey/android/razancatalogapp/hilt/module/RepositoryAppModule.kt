package com.zikey.android.razancatalogapp.hilt.module

import com.zikey.android.razancatalogapp.repo.server_repo.CompanyServerRepo
import com.zikey.android.razancatalogapp.repo.server_repo.ProductServerRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryAppModule {

    @Provides
    @ViewModelScoped
    fun bindProductServerRepos(): ProductServerRepo =
        ProductServerRepo()

    @Provides
    @ViewModelScoped
    fun bindCompanyRepos(): CompanyServerRepo =
        CompanyServerRepo()

}