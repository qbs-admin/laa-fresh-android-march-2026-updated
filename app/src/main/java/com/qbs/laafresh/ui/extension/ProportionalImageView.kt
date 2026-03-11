package com.qbs.laafresh.ui.extension

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView


class ProportionalImageView : AppCompatImageView {
    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!,
        attrs,
        defStyle
    ) {
    }

    protected override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val d: Drawable = getDrawable()
        if (d != null) {
            val w = MeasureSpec.getSize(widthMeasureSpec)
            val h = w * d.intrinsicHeight / d.intrinsicWidth
            setMeasuredDimension(w, h)
        } else super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}