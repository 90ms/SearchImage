package com.a90ms.searchimage.base

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil

class BaseViewPagingAdapter<ITEM : Any>(
    @LayoutRes private val layoutResourceId: Int,
    private val bindingItemId: Int,
    private val viewModel: Map<Int, ViewModel>,
    diffUtil: DiffUtil.ItemCallback<ITEM>
) : BasePagingDataAdapter<ITEM, BaseViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        BaseViewHolder(
            parent = parent,
            layoutResourceId = layoutResourceId,
            bindingItemId = bindingItemId,
            viewModel = viewModel
        )

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}
