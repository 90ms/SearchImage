package com.a90ms.searchimage.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.*
import com.a90ms.common.ext.openBrowser
import com.a90ms.common.ext.px
import com.a90ms.common.ext.textChangesToFlow
import com.a90ms.common.utils.RecyclerViewDividerDecoration
import com.a90ms.domain.data.dto.ItemDto
import com.a90ms.searchimage.BR
import com.a90ms.searchimage.R
import com.a90ms.searchimage.base.BaseActivity
import com.a90ms.searchimage.base.BaseViewPagingAdapter
import com.a90ms.searchimage.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var adapter: BaseViewPagingAdapter<ItemDto>
    private var decoration = RecyclerViewDividerDecoration(1.px)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupObserver()
        setupRecyclerView()
    }

    private fun setupBinding() {
        with(binding) {
            vm = viewModel

            lifecycleScope.launch {
                tieSearch.textChangesToFlow().debounce(1000).filter {
                    val text = tieSearch.text.toString()
                    text.isNotEmpty() && text != viewModel.nowQuery()
                }.onEach {
                    viewModel.fetchImageList(tieSearch.text.toString())
                }.launchIn(this)
            }

            fbTop.setOnClickListener {
                binding.rvImage.scrollToPosition(0)
            }
            fbLayoutManager.setOnClickListener {
                rvImage.removeItemDecoration(decoration)
                when (rvImage.layoutManager) {
                    is LinearLayoutManager -> {
                        rvImage.layoutManager =
                            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                        decoration = RecyclerViewDividerDecoration(1.px, 1.px)
                        fbLayoutManager.setImageResource(R.drawable.ic_layoutmanager_stagger)
                    }
                    is StaggeredGridLayoutManager -> {
                        rvImage.layoutManager = LinearLayoutManager(this@MainActivity)
                        decoration = RecyclerViewDividerDecoration(1.px)
                        fbLayoutManager.setImageResource(R.drawable.ic_layoutmanager_linear)
                    }
                }
                rvImage.addItemDecoration(decoration)
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
                is MainState.OnClickItem -> {
                    openBrowser(it.item.link)
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

            addItemDecoration(RecyclerViewDividerDecoration(1.px))

            addOnScrollListener(
                object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        binding.fbTop.isVisible = recyclerView.canScrollVertically(-1)
                    }
                }
            )

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
                        viewModel.updateEmptyInfo(it)
                    }
                )
            }
            adapter = this@MainActivity.adapter
        }
    }
}
