package nws.example.navigationcomponentdemo.views.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_register.*

import nws.example.navigationcomponentdemo.R
import nws.example.navigationcomponentdemo.bases.BaseFragment
import nws.example.navigationcomponentdemo.extensions.customBackground
import nws.example.navigationcomponentdemo.extensions.setOnClickBounceAnimationListener
import nws.example.navigationcomponentdemo.helpers.SharedPreferencesHelper

class RegisterFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun initView() {
        context?.let {
            btn_register?.customBackground(
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

        btn_register?.setOnClickBounceAnimationListener(0.9f) {
            //get data
            val name = edt_name?.text.toString()
            val userName = edt_username?.text.toString()
            val password = edt_password?.text.toString()

            //check blank
            if (name.isBlank() or userName.isBlank() or password.isBlank()) {
                showSnackBar("Please fill all field", Snackbar.LENGTH_LONG)
                return@setOnClickBounceAnimationListener
            }

            //save token
            SharedPreferencesHelper.instance.put(SharedPreferencesHelper.AUTH_TOKEN, "$name-$userName-$password")

            //navigate to home screen and pass arguments
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeNavigation(
                name,
                userName,
                password
            ))
        }
    }

}
