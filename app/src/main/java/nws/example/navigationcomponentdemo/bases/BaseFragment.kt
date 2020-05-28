package nws.example.navigationcomponentdemo.bases

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    abstract fun initView()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeColorStatusBar()
        initView()
    }

    override fun onPause() {
        super.onPause()

        val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        val view = activity?.currentFocus ?: View(activity)
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun changeColorStatusBar(color: Int? = null) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).changeColorStatusBar(color)
        }
    }

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

    fun showSnackBar(text: CharSequence, duration: Int) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).showSnackBar(text, duration)
        }
    }

    /**
     * Load fragment to container
     *
     * @param containerId R.id.
     * @param fragment fragment
     */
    fun loadFragmentToContainer(containerId: Int, fragment: Fragment) {
        childFragmentManager.beginTransaction().apply {
            this.replace(containerId, fragment)
            this.commit()
        }
    }

    /**
     * Replace fragment
     *
     * @param containerId R.id.
     * @param fragment fragment
     */
    fun replaceFragment(containerId: Int, fragment: Fragment) {
        val backStateName = fragment.javaClass.name
        activity?.supportFragmentManager?.beginTransaction().apply {
            this?.replace(containerId, fragment)
            this?.addToBackStack(backStateName)
            this?.commit()
        }
    }

    /**
     * Add fragment
     *
     * @param containerId R.id.
     * @param fragment fragment
     */
    fun addFragment(containerId: Int, fragment: Fragment) {
        val backStateName = fragment.javaClass.name
        activity?.supportFragmentManager?.beginTransaction().apply {
            this?.add(containerId, fragment)
            this?.addToBackStack(backStateName)
            this?.commit()
        }
    }

}