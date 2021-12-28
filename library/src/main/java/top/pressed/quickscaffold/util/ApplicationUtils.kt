package top.pressed.quickscaffold.util

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import java.lang.NullPointerException
import java.lang.reflect.InvocationTargetException

object ApplicationUtils {
    private var app: Application? = null

    fun init(application: Application) {
        this.app = application
    }

    @SuppressLint("PrivateApi")
    fun getApp(): Application {
        try {
            return app.takeIf { it != null }
                ?: Class.forName("android.app.ActivityThread").run {
                    val currentActivityThread = getMethod("currentActivityThread").invoke(null)
                    val application =
                        getMethod("getApplication").invoke(currentActivityThread) as Application
                    init(application)
                    return application
                }

        } catch (e: NoSuchMethodException) {
            Log.e("getApp:","$e")
        } catch (e: IllegalAccessException) {
            Log.e("getApp:","$e")
        } catch (e: InvocationTargetException) {
            Log.e("getApp:","$e")
        } catch (e: ClassNotFoundException) {
            Log.e("getApp:","$e")
        }
        throw NullPointerException("App should init ")
    }
}