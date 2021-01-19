package com.bepi.ugandroid.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bepi.ugandroid.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //绑定按钮跳转
        homeBtnPickUp.setOnClickListener { view -> navigate(view) }
        homeBtnDeliver.setOnClickListener { view -> navigate(view) }
        homeBtnStorage.setOnClickListener { view -> navigate(view) }
        homeBtnSend.setOnClickListener { view -> navigate(view) }

    }


    /**
     * 首页四个按钮的跳转
     */
    fun navigate(v: View) {
        var action: Int? = null
        when (v.id) {
            R.id.homeBtnPickUp -> action = R.id.action_homeFragment_to_pickupFragment
            R.id.homeBtnDeliver -> action = R.id.action_homeFragment_to_deliverFragment
            R.id.homeBtnStorage -> action = R.id.action_homeFragment_to_storageFragment
            else -> action = R.id.action_homeFragment_to_sendFragment
        }
        if (action != null) {
            Navigation.findNavController(v).navigate(action)
        }
    }

}