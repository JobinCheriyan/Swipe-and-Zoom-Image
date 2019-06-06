package com.example.extendedviewpager

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.viewpager.widget.ViewPager

class ExtendedViewPager : ViewPager {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}


    override fun canScroll(v: View, checkV: Boolean, dx: Int, x: Int, y: Int): Boolean {
        return if (v is TouchImageZoom) {
            //
            // canScrollHorizontally is not supported for Api < 14. To get around this issue,
            // ViewPager is extended and canScrollHorizontallyFroyo, a wrapper around
            // canScrollHorizontally supporting Api >= 8, is called.
            //
            (v as TouchImageZoom).canScrollHorizontallyFroyo(-dx)

        } else {
            super.canScroll(v, checkV, dx, x, y)
        }
    }

}
