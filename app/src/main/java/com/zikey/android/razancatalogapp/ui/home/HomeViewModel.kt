package com.zikey.android.razancatalogapp.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zikey.android.razancatalogapp.model.Advertise
import com.zikey.android.razancatalogapp.model.Product
import com.zikey.android.razancatalogapp.model.wrapper.AdvertisesWrapper
import com.zikey.android.razancatalogapp.model.wrapper.ProductsWrapper
import com.zikey.android.razancatalogapp.repo.server_repo.ProductServerRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ProductServerRepo
) : ViewModel() {

    /**
     * A disposable container that can hold onto multiple other Disposables and
     * offers time complexity for add(Disposable), remove(Disposable) and delete(Disposable)
     * operations.
     */
    private val compositeDisposable = CompositeDisposable()

    val loading = MutableLiveData<Boolean>().apply { value = true }
    val advertiseDataResponse = MutableLiveData<AdvertisesWrapper>()
    val advertiseError = MutableLiveData<Boolean>()

    val productDataResponse = MutableLiveData<ProductsWrapper>()
    val productError = MutableLiveData<Boolean>()


    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    val text: LiveData<String> = _text

    val progress: LiveData<Boolean> = loading

    fun getAdvertises() {

        if (advertiseDataResponse.value != null && !advertiseDataResponse.value!!.datas.isNullOrEmpty())
            return
        loading.value = true

        compositeDisposable.add(
            repository.getAdvertises()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<AdvertisesWrapper>() {
                    override fun onSuccess(value: AdvertisesWrapper) {
                        // Update the values with response in the success method.
                        loading.value = false
                        advertiseDataResponse.value = value
                        advertiseError.value = false
                    }

                    override fun onError(e: Throwable?) {
                        // Update the values in the response in the error method
                        loading.value = false
                        advertiseError.value = true
                        e!!.printStackTrace()
                    }
                })
        )


    }

    fun getProducts() {

        if (productDataResponse.value != null && !productDataResponse.value!!.products.isNullOrEmpty())
            return
        loading.value = true

        compositeDisposable.add(
            repository.getSpecialProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ProductsWrapper>() {
                    override fun onSuccess(value: ProductsWrapper) {
                        // Update the values with response in the success method.
                        loading.value = false
                        productDataResponse.value = value
                        productError.value = false
                    }

                    override fun onError(e: Throwable?) {
                        // Update the values in the response in the error method
                        loading.value = false
                        productError.value = true
                        e!!.printStackTrace()
                    }
                })
        )


    }

}