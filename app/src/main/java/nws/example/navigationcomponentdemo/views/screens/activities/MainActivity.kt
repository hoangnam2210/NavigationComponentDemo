package nws.example.navigationcomponentdemo.views.screens.activities

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import nws.example.navigationcomponentdemo.R
import nws.example.navigationcomponentdemo.bases.BaseActivity
import timber.log.Timber

class MainActivity : BaseActivity() {

    private val navController by lazy {
        Navigation.findNavController(this, R.id.nav_host_container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        changeColorStatusBar(ContextCompat.getColor(this, R.color.colorSplash))
        handleDeepLink(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //send onActivityResult to child fragments
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_container)
        navHostFragment?.let {
            val childFragments = it.childFragmentManager.fragments
            childFragments.forEach { fragment ->
                fragment.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    private fun handleDeepLink(intent: Intent?) {
        intent?.let {
            FirebaseDynamicLinks.getInstance()
                .getDynamicLink(it)
                .addOnSuccessListener(this) { pendingDynamicLinkData ->
                    pendingDynamicLinkData?.let {
                        val args = Bundle()
                        args.putString("name", "name of dynamic link")
                        args.putString("username", "username of dynamic link")
                        args.putString("password", "password of dynamic link")
                        navController.navigate(R.id.editProfileFragment, args)

                    }
                    Timber.d("NamNH - getDynamicLink:onSuccess - pendingDynamicLinkData?.link: ${pendingDynamicLinkData?.link}")
                }
                .addOnFailureListener(this) { e ->
                    Timber.e(e, "NamNH - handleDeepLink - addOnFailureListener")
                }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Timber.d("NamNH - onNewIntent - intent: ${intent?.data}")
        handleDeepLink(intent)
    }
}
