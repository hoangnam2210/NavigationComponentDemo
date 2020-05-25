package nws.example.navigationcomponentdemo.bases

import android.view.WindowManager
import androidx.fragment.app.DialogFragment

open class BaseDialogFragment : DialogFragment() {

    /**
     * Show Custom Loading Dialog
     */
    fun showLoading() {
        if (activity is BaseActivity) {
            (activity as BaseActivity).showLoading()
        }
    }

    /**
     * Hide Custom Loading Dialog
     */
    fun hideLoading() {
        if (activity is BaseActivity) {
            (activity as BaseActivity).hideLoading()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun disableDim() {
        val window = dialog?.window ?: return
        val windowParams = window.attributes
        windowParams.dimAmount = 0f
        windowParams.flags = windowParams.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
        window.attributes = windowParams
    }
}