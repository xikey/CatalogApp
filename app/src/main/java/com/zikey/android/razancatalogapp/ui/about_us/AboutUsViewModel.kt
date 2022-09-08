package com.zikey.android.razancatalogapp.ui.about_us

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zikey.android.razancatalogapp.model.wrapper.CompanyInformationWrapper
import com.zikey.android.razancatalogapp.model.wrapper.ProductMainGroupsWrapper
import com.zikey.android.razancatalogapp.repo.server_repo.CompanyServerRepo
import com.zikey.android.razancatalogapp.repo.server_repo.ProductServerRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
@HiltViewModel
class AboutUsViewModel @Inject constructor(
    private val repository: CompanyServerRepo
) : ViewModel() {

    /**
     * A disposable container that can hold onto multiple other Disposables and
     * offers time complexity for add(Disposable), remove(Disposable) and delete(Disposable)
     * operations.
     */
    private val compositeDisposable = CompositeDisposable()

    val loading = MutableLiveData<Boolean>().apply { value = true }
    val dataResponse = MutableLiveData<CompanyInformationWrapper>()
    val dataError = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean> = loading

    fun getInfo() {

        if (dataResponse.value != null && dataResponse.value!!.data != null)
            return

        loading.value = true
        compositeDisposable.add(
            repository.getInfo()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<CompanyInformationWrapper>() {
                    override fun onSuccess(value: CompanyInformationWrapper) {
                        // Update the values with response in the success method.
                        loading.value = false
                        dataResponse.value = value
                        dataError.value = false
                    }

                    override fun onError(e: Throwable?) {
                        // Update the values in the response in the error method
                        loading.value = false
                        dataError.value = true
                        e!!.printStackTrace()
                    }
                })
        )

    }


}