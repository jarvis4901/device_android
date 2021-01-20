package com.bepi.ugandroid.ui.pickup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bepi.ugandroid.R
import com.bepi.ugandroid.base.BaseFragment
import com.bepi.ugandroid.base.entity.Route
import com.bepi.ugandroid.ui.MainViewModel


class PickupFragment() : BaseFragment(Route(name = "pickup", title = "取快递")) {
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_pickup, container, false)
    }


}