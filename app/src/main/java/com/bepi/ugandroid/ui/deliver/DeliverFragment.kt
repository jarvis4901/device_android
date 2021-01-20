package com.bepi.ugandroid.ui.deliver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bepi.ugandroid.R
import com.bepi.ugandroid.base.BaseFragment
import com.bepi.ugandroid.base.entity.Route


class DeliverFragment : BaseFragment(Route(name = "deliver", title = "派快递件")){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_deliver, container, false)
    }
}