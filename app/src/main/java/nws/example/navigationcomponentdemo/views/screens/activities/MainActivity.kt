package nws.example.navigationcomponentdemo.views.screens.activities

import android.content.Intent
import android.os.Bundle
import nws.example.navigationcomponentdemo.R
import nws.example.navigationcomponentdemo.bases.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
}
