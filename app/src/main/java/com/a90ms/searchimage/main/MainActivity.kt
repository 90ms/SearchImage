package com.a90ms.searchimage.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.SimpleItemAnimator
import com.a90ms.domain.data.dto.ItemDto
import com.a90ms.searchimage.BR
import com.a90ms.searchimage.R
import com.a90ms.searchimage.base.BaseActivity
import com.a90ms.searchimage.base.BaseViewPagingAdapter
import com.a90ms.searchimage.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var adapter: BaseViewPagingAdapter<ItemDto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupObserver()
        setupRecyclerView()
    }

    private fun setupBinding() {
        with(binding) {
            vm = viewModel

            tv.setOnClickListener {
                viewModel.fetchImageList()
            }
        }
    }

    private fun setupObserver() {
        viewModel.state.observe(this) {
            when (it) {
                is MainState.OnUpdateList -> {
                    lifecycleScope.launch {
                        adapter.submitData(it.pagingData)
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvImage.run {
            val diffUtil = object : DiffUtil.ItemCallback<ItemDto>() {
                override fun areItemsTheSame(
                    oldItem: ItemDto,
                    newItem: ItemDto
                ) = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: ItemDto,
                    newItem: ItemDto
                ) = oldItem == newItem
            }

            val animator = itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }

            this@MainActivity.adapter = BaseViewPagingAdapter(
                layoutResourceId = R.layout.item_image,
                bindingItemId = BR.item,
                viewModel = mapOf(BR.vm to viewModel),
                diffUtil = diffUtil
            ).apply {
                setupSourceLoadStateListener(
                    scope = lifecycleScope,
                    isLoading = {
                        // TODO 로딩 처리
                    },
                    scrollTop = {
                        scrollToPosition(0)
                    },
                    isListEmpty = {
                        // TODO 빈 화면 처리
                    }
                )
            }
            adapter = this@MainActivity.adapter
        }
    }
}
