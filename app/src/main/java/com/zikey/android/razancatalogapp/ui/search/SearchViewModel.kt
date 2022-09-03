package com.zikey.android.razancatalogapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zikey.android.razancatalogapp.model.wrapper.ProductsWrapper
import com.zikey.android.razancatalogapp.repo.server_repo.ProductServerRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: ProductServerRepo
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val loading = MutableLiveData<Boolean>().apply { value = false }
    val progress: LiveData<Boolean> = loading

    val productsResponse = MutableLiveData<ProductsWrapper>()
    val productsError = MutableLiveData<Boolean>()

    fun getProducts(keySearch: String) {


      runBlocking {
          launch(Main) {
              loading.value=true
          }
      }

        compositeDisposable.add(
            repository.searchProducts(keySearch)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ProductsWrapper>() {
                    override fun onSuccess(value: ProductsWrapper) {

                        loading.value = false
                        productsResponse.value = value
                        productsError.value = false
                    }

                    override fun onError(e: Throwable?) {

                        loading.value = false
                        productsError.value = true
                        e!!.printStackTrace()
                    }
                })
        )

    }


}