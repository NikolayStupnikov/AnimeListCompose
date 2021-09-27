package ru.nikolay.stupnikov.animelistcompose.ui.base

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.nikolay.stupnikov.animelistcompose.data.api.exception.NoConnectivityException
import java.lang.ref.WeakReference

abstract class BaseViewModel<N> : ViewModel() {

    private var compositeDisposable: CompositeDisposable? = null

    private lateinit var navigator: WeakReference<N>

    var mIsLoading = mutableStateOf(false)

    fun setNavigator(navigator: N) {
        this.navigator = WeakReference(navigator)
    }

    open fun getNavigator(): N? {
        return navigator.get()
    }

    private fun getCompositeDisposable(): CompositeDisposable {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
        }
        return compositeDisposable!!
    }

    protected open fun compositeRemove(disposableVariable: Disposable?) {
        if (disposableVariable != null) {
            getCompositeDisposable().remove(disposableVariable)
        }
    }

    protected open fun compositeAdd(disposable: Disposable?) {
        if (disposable != null) {
            getCompositeDisposable().add(disposable)
        }
    }

    private fun compositeDisposableUnsubscribe() {
        if (compositeDisposable != null && !compositeDisposable!!.isDisposed) {
            compositeDisposable!!.dispose()
            compositeDisposable = null
        }
    }

    override fun onCleared() {
        compositeDisposableUnsubscribe()
        super.onCleared()
    }

    fun showErrorMessage(it: Throwable): String {
        return if (it is NoConnectivityException) it.localizedMessage
        else if (!it.message.isNullOrEmpty()) it.message!!
        else "Server error"
    }
}