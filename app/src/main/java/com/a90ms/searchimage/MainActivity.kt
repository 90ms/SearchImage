package com.a90ms.searchimage

import android.os.Bundle
import androidx.activity.viewModels
import com.a90ms.searchimage.base.BaseActivity
import com.a90ms.searchimage.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
    }

    private fun setupBinding() {
        with(binding) {
            vm = viewModel

            tv.setOnClickListener {
                viewModel.fetchImageList()
            }
        }
    }
}
