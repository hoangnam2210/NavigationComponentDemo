package nws.example.navigationcomponentdemo.bases

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import nws.example.navigationcomponentdemo.R
import nws.example.navigationcomponentdemo.views.customs.CustomLoadingDialog

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    private val customLoadingDialog = CustomLoadingDialog()

    /**
     * Show Custom Loading Dialog
     */
    fun showLoading() {
        if (!customLoadingDialog.isShow) {
            customLoadingDialog.show(supportFragmentManager, null)
            customLoadingDialog.isShow = true
        }
    }

    /**
     * Hide Custom Loading Dialog
     */
    fun hideLoading() {
        try {
            customLoadingDialog.isShow = false
            customLoadingDialog.dismiss()
        } catch (e: Exception) {
        }
    }

    fun showSnackBar(text: CharSequence, duration: Int) {
        Snackbar.make(findViewById(android.R.id.content), text, duration).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeColorStatusBar()
    }

    /**
     * Change color status bar
     */
    fun changeColorStatusBar(color: Int? = null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

            val colorValue = color ?: ContextCompat.getColor(this, R.color.colorPrimaryDark)
            window.statusBarColor = colorValue
        }
    }

    /**
     * Load fragment to container
     *
     * @param containerId R.id.
     * @param fragment fragment
     */
    fun loadFragmentToContainer(containerId: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            this.replace(containerId, fragment)
            this.commit()
        }
    }

    fun removeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            this.remove(fragment)
            this.commit()
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
        supportFragmentManager.beginTransaction().apply {
            this.add(containerId, fragment)
            this.addToBackStack(backStateName)
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
        supportFragmentManager.beginTransaction().apply {
            this.replace(containerId, fragment)
            this.addToBackStack(backStateName)
            this.commit()
        }
    }

    /**
     * Pop fragment
     */
    fun popFragment(): Boolean {
        val countStack = supportFragmentManager.backStackEntryCount
        return if (countStack > 1) {
            supportFragmentManager.popBackStack()
            true
        } else {
            false
        }
    }

    override fun onBackPressed() {
        if (!popFragment()) {
            super.onBackPressed()
        }
    }

}