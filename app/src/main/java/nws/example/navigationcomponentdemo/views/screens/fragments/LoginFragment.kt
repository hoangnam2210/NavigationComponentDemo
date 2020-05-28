package nws.example.navigationcomponentdemo.views.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login.*

import nws.example.navigationcomponentdemo.R
import nws.example.navigationcomponentdemo.bases.BaseFragment
import nws.example.navigationcomponentdemo.extensions.customBackground
import nws.example.navigationcomponentdemo.extensions.setOnClickBounceAnimationListener
import nws.example.navigationcomponentdemo.helpers.SharedPreferencesHelper

class LoginFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun initView() {
        context?.let {
            btn_login?.customBackground(
                dpsCorner = 28,
                colors = intArrayOf(
                    ContextCompat.getColor(it, R.color.colorPrimaryDark),
                    ContextCompat.getColor(it, R.color.colorPrimary)
                )
            )
        }

        iv_logo?.let {
            Glide.with(this)
                .load(R.drawable.logo)
                .into(it)
        }

        //get token
        val token =
            SharedPreferencesHelper.instance[SharedPreferencesHelper.AUTH_TOKEN, String::class.java]

        if (token.isNotBlank()) {
            //navigate to home if token exist
            val data = token.split("-")

            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToHomeNavigationNoAnimation(
                    data.getOrNull(0) ?: "",
                    data.getOrNull(1) ?: "",
                    data.getOrNull(2) ?: ""
                )
            )
            return
        }

        tv_register?.setOnClickListener {
            //navigate to register screen
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }


        btn_login?.setOnClickBounceAnimationListener(0.9f) {
            //get username and password
            val username = edt_username?.text.toString()
            val password = edt_password?.text.toString()

            //validate username and password
            if ((username == "admin") and (password == "123")) {
                showSnackBar("Login Success", Snackbar.LENGTH_LONG)

                //save token
                SharedPreferencesHelper.instance.put(
                    SharedPreferencesHelper.AUTH_TOKEN,
                    "Admin-$username-$password"
                )

                //navigate to home screen and pass arguments
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToHomeNavigation(
                        "Admin",
                        username,
                        password
                    )
                )
            } else {
                showSnackBar("Login Fail", Snackbar.LENGTH_SHORT)
            }
        }
    }
}
