package com.bepi.ugandroid.ui.common

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bepi.ugandroid.R
import com.bepi.ugandroid.viewModel.MainViewModel

class TitleHeader(view: View, context: Context) {
    init {
        val titleTextView = view.findViewById<View>(R.id.titleText) as TextView;
        val countDownTextView = view.findViewById<View>(R.id.textCountDown) as TextView;
        val backBlock = view.findViewById<View>(R.id.backBlock) as LinearLayout;

        //返回上一页
        backBlock.setOnClickListener { view -> Navigation.findNavController(view).popBackStack() }
    }
}