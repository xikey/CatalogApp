package com.zikey.android.razancatalogapp.ui.dashboard

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zikey.android.razancatalogapp.model.wrapper.AdvertisesWrapper
import com.zikey.android.razancatalogapp.model.wrapper.ProductMainGroupsWrapper
import com.zikey.android.razancatalogapp.repo.server_repo.ProductServerRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel  @Inject constructor(
    private val repository: ProductServerRepo
): ViewModel() {

    /**
     * A disposable container that can hold onto multiple other Disposables and
     * offers time complexity for add(Disposable), remove(Disposable) and delete(Disposable)
     * operations.
     */
    private val compositeDisposable = CompositeDisposable()

    val loading = MutableLiveData<Boolean>().apply { value = true }
    val productGroupDataResponse = MutableLiveData<ProductMainGroupsWrapper>()
    val productGroupError = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean> = loading

    fun getProductMainGroups(context: Context) {
        loading.value = true
        compositeDisposable.add(
            repository.getProducts_mainGroups()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ProductMainGroupsWrapper>() {
                    override fun onSuccess(value: ProductMainGroupsWrapper) {
                        // Update the values with response in the success method.
                        loading.value = false
                        productGroupDataResponse.value = value
                        productGroupError.value = false
                    }

                    override fun onError(e: Throwable?) {
                        // Update the values in the response in the error method
                        loading.value = false
                        productGroupError.value = true
                        e!!.printStackTrace()
                    }
                })
        )

    }

}