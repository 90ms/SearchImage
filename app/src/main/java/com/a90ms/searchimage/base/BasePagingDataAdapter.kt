package com.a90ms.searchimage.base

import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.a90ms.common.base.ERROR_MESSAGE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.launch

abstract class BasePagingDataAdapter<ITEM : Any, VH : RecyclerView.ViewHolder>(
    diffUtil: DiffUtil.ItemCallback<ITEM>
) : PagingDataAdapter<ITEM, VH>(diffUtil) {

    private var previousLoading: Boolean? = null

    fun setupSourceLoadStateListener(
        scope: CoroutineScope,
        isLoading: ((Boolean) -> Unit)? = null,
        isListEmpty: ((Boolean) -> Unit)? = null,
        scrollTop: (() -> Unit)? = null,
        isError: ((String) -> Unit)? = null
    ) {
        scrollTop?.let { setupScrollTop(scope, it) }

        scope.launch {
            loadStateFlow
                .dropWhile {
                    it.refresh is LoadState.NotLoading &&
                        it.append is LoadState.NotLoading &&
                        it.prepend is LoadState.NotLoading
                }
                .distinctUntilChanged()
                .collect {
                    val loading = it.refresh is LoadState.Loading
                    if (previousLoading != loading) {
                        isLoading?.invoke(loading)
                        previousLoading = loading
                    }

                    if (loading.not()) isListEmpty?.invoke(itemCount == 0)

                    val errorState = it.append as? LoadState.Error
                        ?: it.prepend as? LoadState.Error
                        ?: it.refresh as? LoadState.Error
                    errorState?.let { isError?.invoke(it.error.message ?: ERROR_MESSAGE) }
                }
        }
    }

    private fun setupScrollTop(
        scope: CoroutineScope,
        scrollTop: () -> Unit
    ) {
        scope.launch {
            loadStateFlow
                .dropWhile {
                    it.refresh is LoadState.NotLoading &&
                        it.append is LoadState.NotLoading &&
                        it.prepend is LoadState.NotLoading
                }
                .distinctUntilChangedBy { it.refresh }
                .collect {
                    if (it.refresh is LoadState.NotLoading) {
                        scrollTop()
                    }
                }
        }
    }
}
