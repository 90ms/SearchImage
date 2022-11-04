package com.a90ms.searchimage.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.a90ms.domain.base.successOr
import com.a90ms.domain.usecase.GetImageListUseCase
import com.a90ms.searchimage.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getImageListUseCase: GetImageListUseCase
) : BaseViewModel() {

    private val _state = MutableLiveData<MainState>()
    val state: LiveData<MainState> get() = _state

    fun fetchImageList() {
        viewModelScope.launch {
            getImageListUseCase("비이지").successOr(null).let {
                it?.map { pagingData ->
                    pagingData
                }?.cachedIn(viewModelScope)?.collect { vo ->
                    _state.value = MainState.OnUpdateList(vo)
                }
            }
        }
    }
}
