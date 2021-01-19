package com.bepi.ugandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bepi.ugandroid.R
import kotlinx.android.synthetic.main.common_title.view.*

open class BaseFragment(val title: String) :
    Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }


    fun initView(view: View) {
        initHeader(view)
    }

    fun initHeader(view: View) {
        val titleTextView = view.findViewById<View>(R.id.titleText) as TextView;
        if (titleTextView != null) {
            titleTextView.setText(title);
        }
    }


}