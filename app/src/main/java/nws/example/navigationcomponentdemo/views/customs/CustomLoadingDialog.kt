package nws.example.navigationcomponentdemo.views.customs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nws.example.navigationcomponentdemo.R
import nws.example.navigationcomponentdemo.bases.BaseDialogFragment

class CustomLoadingDialog : BaseDialogFragment() {

    var isShow = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_custom_loading, container, false)
    }

    override fun onStart() {
        super.onStart()
        disableDim()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        isCancelable = false
    }
}