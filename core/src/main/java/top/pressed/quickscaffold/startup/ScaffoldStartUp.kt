package top.pressed.quickscaffold.startup

import android.app.Application
import android.content.Context
import com.rousetime.android_startup.AndroidStartup
import top.pressed.quickscaffold.util.ApplicationUtils
import top.pressed.quickscaffold.util.UiUtils

class ScaffoldStartUp: AndroidStartup<Unit>() {

    override fun callCreateOnMainThread(): Boolean = true

    override fun waitOnMainThread(): Boolean = true

    override fun create(context: Context): Unit? {
        (context as? Application)?.let {
            ApplicationUtils.init(it)
        }
        UiUtils.density = context.resources.displayMetrics.density
        UiUtils.screenHeightPx = context.resources.displayMetrics.widthPixels
        UiUtils.screenWidthPx = context.resources.displayMetrics.heightPixels
        return null
    }
}