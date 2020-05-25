package nws.example.navigationcomponentdemo.views.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_edit_profile.*

import nws.example.navigationcomponentdemo.R
import nws.example.navigationcomponentdemo.bases.BaseFragment
import nws.example.navigationcomponentdemo.extensions.customBackground
import nws.example.navigationcomponentdemo.extensions.setNavigationResult
import nws.example.navigationcomponentdemo.extensions.setOnClickBounceAnimationListener
import nws.example.navigationcomponentdemo.helpers.SharedPreferencesHelper
import nws.example.navigationcomponentdemo.utils.NavigationResultKey

class EditProfileFragment : BaseFragment() {

    private val args: EditProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun initView() {
        edt_name?.setText(args.name)
        edt_username?.setText(args.username)
        edt_password?.setText(args.password)

        context?.let {
            btn_update?.customBackground(
                dpsCorner = 28,
                colors = intArrayOf(
                    ContextCompat.getColor(it, R.color.colorPrimaryDark),
                    ContextCompat.getColor(it, R.color.colorPrimary)
                )
            )
        }

        btn_update?.setOnClickBounceAnimationListener(0.9f) {
            val name = edt_name?.text.toString()
            val username = edt_username?.text.toString()
            val password = edt_password?.text.toString()

            setNavigationResult(name, NavigationResultKey.Home.NAME)
            setNavigationResult(username, NavigationResultKey.Home.USER_NAME)
            setNavigationResult(password, NavigationResultKey.Home.PASSWORD)

            findNavController().previousBackStackEntry?.savedStateHandle?.set(NavigationResultKey.Home.PASSWORD, password)

            showSnackBar("Update Successful", Snackbar.LENGTH_LONG)

            SharedPreferencesHelper.instance.put(
                SharedPreferencesHelper.AUTH_TOKEN,
                "$name-$username-$password"
            )
            findNavController().popBackStack()
        }
    }

}
