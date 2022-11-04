package com.a90ms.searchimage.main

import androidx.paging.PagingData
import com.a90ms.domain.data.dto.ItemDto

sealed class MainState {
    data class OnUpdateList(val pagingData: PagingData<ItemDto>) : MainState()
}
