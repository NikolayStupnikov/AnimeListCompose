package ru.nikolay.stupnikov.animelistcompose.ui.base

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ru.nikolay.stupnikov.animelistcompose.data.api.exception.NoConnectivityException
import java.lang.ref.WeakReference

abstract class BaseViewModel<N> : ViewModel() {

    private lateinit var navigator: WeakReference<N>

    var mIsLoading = mutableStateOf(false)

    fun setNavigator(navigator: N) {
        this.navigator = WeakReference(navigator)
    }

    open fun getNavigator(): N? {
        return navigator.get()
    }

    fun showErrorMessage(it: Throwable): String {
        return if (it is NoConnectivityException) it.localizedMessage
        else if (!it.message.isNullOrEmpty()) it.message!!
        else "Server error"
    }
}