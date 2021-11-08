package top.pressed.quickscaffold.util

import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.AttrRes

object UiUtils {
    var density: Float = 0f
    var screenWidthPx: Int = 0
    var screenHeightPx: Int = 0

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dip2px(dpValue: Int): Int {
        val scale: Float = density
        return (dpValue * density + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dip(pxValue: Int): Int {
        return (pxValue / density + 0.5f).toInt()
    }

    /**
     * 根据 R.attr 返回对应 TypedValue
     * @param context 需要为Activity/Fragment Context不能是ApplicationContext
     */
    fun resolveAttr(context: Context, @AttrRes attr: Int): TypedValue {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(attr, typedValue, true)
        return typedValue;
    }

    /**
     * 显示Toast
     */
    fun showToast(
        content: String,
        gravity: Int = Gravity.BOTTOM,
        context: Context = ApplicationUtils.getApp()
    ) {
        val toast = Toast.makeText(
            context,
            content,
            if (content.length > 6) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        )
        toast.setGravity(
            gravity, 0, when (gravity) {
                Gravity.BOTTOM -> -200
                Gravity.TOP -> 200
                else -> 0
            }
        )
        toast.show()
    }
}