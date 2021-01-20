package com.bepi.ugandroid.ui.send

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bepi.ugandroid.R
import com.bepi.ugandroid.base.BaseFragment
import com.bepi.ugandroid.base.entity.Route

class SendFragment : BaseFragment(Route(name = "send", title = "寄件")) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_send, container, false)
    }

}