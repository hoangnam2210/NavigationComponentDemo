package nws.example.navigationcomponentdemo.extensions

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.animation.BounceInterpolator
import nws.example.navigationcomponentdemo.utils.CommonUtils

/**
 * Custom view click animation bounce with scale bounce
 */
fun View.setOnClickBounceAnimationListener(scaleBounce: Float? = null, clickFun: () -> Unit) {
    this.setOnClickListener {
        clickFun()
        it.scaleX = scaleBounce ?: 0.8f
        it.scaleY = scaleBounce ?: 0.8f
        it.animate().scaleX(1f).scaleY(1f).setDuration(500).interpolator = BounceInterpolator()
    }
}

/**
 * Custom background View
 *
 * Color can get from CommonUtils.getColor, Color.parseColor, ...
 *
 * @param colors: intArrayOf(color1, color2, ...)
 * @param orientationColor: Default is GradientDrawable.Orientation.LEFT_RIGHT
 * @param dpsCorner: Unit Dps
 * @param dpsBorderWidth: Unit Dps
 * @param colorBorder
 */
fun View.customBackground(
    colors: IntArray? = null,
    orientationColor: GradientDrawable.Orientation? = null,
    dpsCorner: Int? = null,
    dpsBorderWidth: Int? = null,
    colorBorder: Int? = null
) {
    var gradientDrawable = GradientDrawable()

    //set Color Background
    if (colors != null) {
        when {
            colors.size > 1 -> gradientDrawable =
                GradientDrawable(
                    orientationColor ?: GradientDrawable.Orientation.LEFT_RIGHT,
                    colors
                )
            colors.size == 1 -> gradientDrawable.setColor(colors[0])
            else -> {
                if (this.background is ColorDrawable)
                    gradientDrawable.setColor((this.background as ColorDrawable).color)
            }
        }
    } else {
        if (this.background is ColorDrawable)
            gradientDrawable.setColor((this.background as ColorDrawable).color)
    }

    //set Corner
    if (dpsCorner != null) gradientDrawable.cornerRadius =
        CommonUtils.dpsToPixel(dpsCorner).toFloat()

    //set Border
    if (dpsBorderWidth != null && colorBorder != null) gradientDrawable.setStroke(
        CommonUtils.dpsToPixel(dpsBorderWidth), colorBorder
    )

    //apply custom Background
    if (colors != null || dpsCorner != null || dpsBorderWidth != null) this.background =
        gradientDrawable
}