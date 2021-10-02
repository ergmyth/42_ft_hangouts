package ru.school21.eleonard.helpers

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class VerticalMarginItemDecoration(context: Context, @DimenRes verticalMarginInDp: Int) :
        RecyclerView.ItemDecoration() {

    private val verticalMarginInPx: Int =
            context.resources.getDimension(verticalMarginInDp).toInt()

    override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.bottom = verticalMarginInPx
        outRect.top = verticalMarginInPx
    }

}