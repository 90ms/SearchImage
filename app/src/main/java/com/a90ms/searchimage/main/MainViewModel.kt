package com.a90ms.searchimage.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.a90ms.domain.base.successOr
import com.a90ms.domain.data.dto.ItemDto
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

    private val _emptyInfo = MutableLiveData(Pair(true, "검색어를 통해 조회 해보세요."))
    val emptyInfo: LiveData<Pair<Boolean, String>> get() = _emptyInfo

    private val nowQuery = MutableLiveData("")

    fun fetchImageList(query: String) {
        viewModelScope.launch {
            nowQuery.value = query
            getImageListUseCase(query).successOr(null).let {
                it?.map { pagingData ->
                    pagingData
                }?.cachedIn(viewModelScope)?.collect { vo ->
                    _state.value = MainState.OnUpdateList(vo)
                }
            }
        }
    }

    fun updateEmptyInfo(visible: Boolean) {
        _emptyInfo.value = Pair(visible, "검색 결과가 없습니다.")
    }

    fun onClickItem(item: ItemDto) {
        _state.value = MainState.OnClickItem(item)
    }

    fun nowQuery() = nowQuery.value ?: ""
}
