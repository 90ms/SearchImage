package com.a90ms.common.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class RecyclerViewDividerDecoration: RecyclerView.ItemDecoration {

    private var verticalSpace: Int = 0
    private var horizontalSpace: Int = 0

    /**
     * for LinearLayoutManager
     * */
    constructor(space: Int) : this(space, space)

    /**
     * for StaggeredGridLayoutManager
     */
    constructor(verticalSpace: Int, horizontalSpace: Int) : super() {
        this.verticalSpace = verticalSpace
        this.horizontalSpace = horizontalSpace
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        when (val layoutManager = parent.layoutManager) {
            is StaggeredGridLayoutManager -> {
                val spanCount = layoutManager.spanCount
                val position = layoutManager.getPosition(view)
                val column = position % spanCount

                outRect.left = column * horizontalSpace / spanCount
                outRect.right = horizontalSpace - (column + 1) * horizontalSpace / spanCount

                if (position >= spanCount) {
                    outRect.top += verticalSpace
                }
            }
            is LinearLayoutManager -> {
                when (layoutManager.orientation) {
                    RecyclerView.HORIZONTAL -> {
                        outRect.left += calculateSpaceByLocation(
                            layoutManager,
                            view,
                            horizontalSpace
                        )
                    }
                    RecyclerView.VERTICAL -> {
                        outRect.bottom += calculateSpaceByLocation(
                            layoutManager,
                            view,
                            verticalSpace
                        )
                    }
                }
            }
        }
    }

    private fun calculateSpaceByLocation(
        layoutManager: LinearLayoutManager,
        view: View,
        space: Int
    ): Int {
        val isLast = layoutManager.getPosition(view) == layoutManager.itemCount - 1
        return if (isLast) 0 else space
    }
}