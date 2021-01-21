package com.bepi.ugandroid.ui.pickup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bepi.ugandroid.R
import com.bepi.ugandroid.base.BaseFragment
import com.bepi.ugandroid.base.entity.Route
import com.bepi.ugandroid.base.ui.BaseDialog
import com.bepi.ugandroid.ui.MainViewModel
import kotlinx.android.synthetic.main.fragment_pickup.*


class PickupFragment() : BaseFragment(Route(name = "pickup", title = "取快递")) {
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_pickup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        editText.setOnClickListener(View.OnClickListener {
//
//        })
        button.setOnClickListener({
            BaseDialog()
                .setFragmentManager(childFragmentManager)
                .setTitle("添加xx")
                .setMessage("success")
                .setCanceledOnTouchOutside(true) // 点击dialog外部关闭dialog
                .setDismissMethod { // Dialog消失回调

                }
                .setPositiveButtonMethod { dialog, view -> // 点击底部右侧按钮回调
                    dialog?.dismiss()

                }
                .show()
        })
        super.onViewCreated(view, savedInstanceState)
    }


}