package nws.example.navigationcomponentdemo.utils

import android.content.Context
import android.content.res.Resources
import android.os.Build
import nws.example.navigationcomponentdemo.bases.MainApplication
import kotlin.math.roundToInt

object CommonUtils {

    /**
     * Get color wrapper
     *
     * @param context
     * @param id
     * @return
     */
    @Suppress("DEPRECATION")
    fun getColor(context: Context?, id: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context?.getColor(id) ?: 0
        } else {
            context?.resources?.getColor(id) ?: 0
        }
    }

    /**
     * @param dps value of dps
     * @return value of pixel
     */
    fun dpsToPixel(dps: Int): Int {
        val density = Resources.getSystem().displayMetrics.density
        return (dps * density).roundToInt()
    }

    /**
     * @param px value of pixel
     * @return value of dps
     */
    fun pixelToDps(px: Int): Float {
        val densityDpi = Resources.getSystem().displayMetrics.densityDpi.toFloat()
        return px / (densityDpi / 160f)
    }

    /**
     * Function to clone any object
     * if error -> return new constructor
     */
    fun <T> cloneObject(obj: Any?, typeClass: Class<T>): T {
        return try {
            val stringObject = MainApplication.self().getGson().toJson(obj, typeClass)
            MainApplication.self().getGson().fromJson(stringObject, typeClass)
        } catch (e: Exception) {
            typeClass.newInstance()
        }
    }
}