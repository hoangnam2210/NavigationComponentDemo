package nws.example.navigationcomponentdemo.views.screens.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController

import nws.example.navigationcomponentdemo.R
import nws.example.navigationcomponentdemo.bases.BaseFragment
import nws.example.navigationcomponentdemo.bases.MainApplication

class SplashFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onResume() {
        super.onResume()
        if (MainApplication.self().isInitDone) {
            navigateToNextScreen()
        }
    }

    override fun initView() {
        context?.let {
            changeColorStatusBar(ContextCompat.getColor(it, R.color.colorSplash))
        }

        if (!MainApplication.self().isInitDone) {
            Handler().postDelayed({
                MainApplication.self().isInitDone = true
                navigateToNextScreen()
            }, 2000)
        }
    }

    private fun navigateToNextScreen() {
        if (isAdded) {
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
        }
    }
}
