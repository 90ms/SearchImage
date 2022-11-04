package com.a90ms.searchimage

import androidx.lifecycle.viewModelScope
import com.a90ms.domain.base.onError
import com.a90ms.domain.base.onException
import com.a90ms.domain.base.onSuccess
import com.a90ms.domain.usecase.GetImageListUseCase
import com.a90ms.searchimage.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getImageListUseCase: GetImageListUseCase
) : BaseViewModel() {

    fun fetchImageList() {
        viewModelScope.launch {
            getImageListUseCase("나비").onSuccess {
                Timber.i("onSuccess : $it")
            }.onError { code, message ->
                Timber.e("onError : $code / $message")
            }.onException {
                Timber.e("onException: ${it.message}")
            }
        }
    }
}
