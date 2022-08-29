package com.zikey.android.razancatalogapp.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zikey.android.razancatalogapp.model.wrapper.ProductSubGroupsWrapper
import com.zikey.android.razancatalogapp.model.wrapper.ProductsWrapper
import com.zikey.android.razancatalogapp.repo.server_repo.ProductServerRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel  @Inject constructor(
    private val repository: ProductServerRepo
): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val loading = MutableLiveData<Boolean>().apply { value = true }
    val progress: LiveData<Boolean> = loading

    val productsResponse = MutableLiveData<ProductsWrapper>()
    val productsError = MutableLiveData<Boolean>()

    fun getProducts(mainGroup: Long,subGroup: Long) {

        if (productsResponse.value != null && !productsResponse.value!!.products.isNullOrEmpty())
            return

        loading.value = true

        compositeDisposable.add(
            repository.getProducts(mainGroup,subGroup)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ProductsWrapper>() {
                    override fun onSuccess(value: ProductsWrapper) {
                        // Update the values with response in the success method.
                        loading.value = false
                        productsResponse.value = value
                        productsError.value = false
                    }

                    override fun onError(e: Throwable?) {
                        // Update the values in the response in the error method
                        loading.value = false
                        productsError.value = true
                        e!!.printStackTrace()
                    }
                })
        )

    }


}