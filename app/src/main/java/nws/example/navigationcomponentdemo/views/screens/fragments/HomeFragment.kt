package nws.example.navigationcomponentdemo.views.screens.fragments

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_home.*

import nws.example.navigationcomponentdemo.R
import nws.example.navigationcomponentdemo.bases.BaseFragment
import nws.example.navigationcomponentdemo.extensions.customBackground
import nws.example.navigationcomponentdemo.extensions.getNavigationResult
import nws.example.navigationcomponentdemo.extensions.setOnClickBounceAnimationListener
import nws.example.navigationcomponentdemo.helpers.SharedPreferencesHelper
import nws.example.navigationcomponentdemo.utils.NavigationResultKey

class HomeFragment : BaseFragment() {

    private var canBack = false
    private val args: HomeFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getNavigationResult<String>(NavigationResultKey.Home.NAME)?.observe(this, Observer {
            tv_name?.text = it
        })

        getNavigationResult<String>(NavigationResultKey.Home.USER_NAME)?.observe(this, Observer {
            edt_username?.setText(it)
        })

        getNavigationResult<String>(NavigationResultKey.Home.PASSWORD)?.observe(this, Observer {
            edt_password?.setText(it)
        })

        //handle back pressed
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (canBack) {
                    activity?.finish()
                } else {
                    canBack = true
                    Toast.makeText(context, "Back again to exit", Toast.LENGTH_SHORT).show()

                    Handler().postDelayed({
                        canBack = false
                    }, 2000)
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun initView() {
        context?.let {
            cl_top?.customBackground(
                orientationColor = GradientDrawable.Orientation.TOP_BOTTOM,
                colors = intArrayOf(
                    ContextCompat.getColor(it, R.color.colorPrimaryDark),
                    ContextCompat.getColor(it, R.color.colorPrimary)
                )
            )

            iv_avatar?.customBackground(
                dpsCorner = 50,
                dpsBorderWidth = 1,
                colorBorder = ContextCompat.getColor(it, android.R.color.darker_gray)
            )

            btn_logout?.customBackground(
                dpsCorner = 28,
                colors = intArrayOf(
                    ContextCompat.getColor(it, R.color.colorRed),
                    ContextCompat.getColor(it, R.color.colorRedLight)
                )
            )
        }

        //show data from arguments
        tv_name?.text = args.name
        edt_username?.setText(args.username)
        edt_password?.setText(args.password)

        btn_logout?.setOnClickBounceAnimationListener(0.9f) {
            //remove token
            SharedPreferencesHelper.instance.put(SharedPreferencesHelper.AUTH_TOKEN, "")
            //navigate to login screen
            findNavController().navigate(LoginFragmentDirections.actionGlobalLoginFragment())
        }

        iv_edit?.setOnClickListener {
            val name = tv_name?.text.toString()
            val username = edt_username?.text.toString()
            val password = edt_password?.text.toString()

            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToEditProfileFragment(
                    name, username, password
                )
            )
        }

        iv_avatar?.setOnClickListener {
            //Create notification with deep link
            val args = Bundle()
            args.putString("name", "name from notification")
            args.putString("username", "username from notification")
            args.putString("password", "password from notification")
            val deepLink = findNavController().createDeepLink()
                .setDestination(R.id.editProfileFragment)
                .setArguments(args)
                .createPendingIntent()
            val notificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager?.createNotificationChannel(
                    NotificationChannel(
                        "1234", "Deep Links", NotificationManager.IMPORTANCE_HIGH
                    )
                )
            }
            val builder = NotificationCompat.Builder(
                requireContext(), "1234"
            )
                .setContentTitle("Navigation")
                .setContentText("Deep link to Android")
                .setSmallIcon(R.drawable.ic_perm_identity_black_24dp)
                .setContentIntent(deepLink)
                .setAutoCancel(true)
            notificationManager?.notify(0, builder.build())
        }
    }

}
