package com.zikey.android.razancatalogapp.ui.product_sub_group

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zikey.android.razancatalogapp.model.wrapper.ProductMainGroupsWrapper
import com.zikey.android.razancatalogapp.model.wrapper.ProductSubGroupsWrapper
import com.zikey.android.razancatalogapp.repo.server_repo.ProductServerRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ProductSubGroupViewModel  @Inject constructor(
    private val repository: ProductServerRepo
): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val loading = MutableLiveData<Boolean>().apply { value = true }
    val progress: LiveData<Boolean> = loading

    val productSubDataResponse = MutableLiveData<ProductSubGroupsWrapper>()
    val productSubError = MutableLiveData<Boolean>()

    fun getProductSubGroups(mainGroup: Long) {

        if (productSubDataResponse.value != null && !productSubDataResponse.value!!.list.isNullOrEmpty())
            return

        loading.value = true

        compositeDisposable.add(
            repository.getProducts_subGroups(mainGroup)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ProductSubGroupsWrapper>() {
                    override fun onSuccess(value: ProductSubGroupsWrapper) {
                        // Update the values with response in the success method.
                        loading.value = false
                        productSubDataResponse.value = value
                        productSubError.value = false
                    }

                    override fun onError(e: Throwable?) {
                        // Update the values in the response in the error method
                        loading.value = false
                        productSubError.value = true
                        e!!.printStackTrace()
                    }
                })
        )

    }

}