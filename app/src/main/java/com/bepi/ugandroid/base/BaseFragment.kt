package com.bepi.ugandroid.base

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bepi.ugandroid.R
import com.bepi.ugandroid.base.entity.Route
import kotlinx.android.synthetic.main.common_title.*

abstract class BaseFragment(val route: Route) :
    Fragment() {
    private val COUNT_DELAY: Long = 120 * 1000 //定义总时长 120秒
    private var countDownTimer: CountDownTimer? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        if (route.hasHeader) {
            initHeader(view)
        }
    }


    abstract fun initView()


    // 标题栏初始化
    fun initHeader(view: View) {
        titleText?.setText(route.title)
        backBlock?.setOnClickListener(View.OnClickListener { v -> backToHome(v) })
        if (route.hasLiveTime) {
            //页面倒计时
            countDownTimer = object : CountDownTimer(COUNT_DELAY, 1000) {
                override fun onFinish() = backToHome(view)//结束倒计时返回首页
                override fun onTick(millisUntilFinished: Long) {
                    val second = (millisUntilFinished / 1000)
                    textCountDown?.setText("${second}秒")
                }
            }.start()
        }

    }

    //返回首页
    fun backToHome(view: View) {
        when (route.name) {
            "pickup" -> Navigation.findNavController(view)
                .navigate(R.id.action_pickupFragment_to_homeFragment)
            "deliver" -> Navigation.findNavController(view)
                .navigate(R.id.action_deliverFragment_to_homeFragment)
            "storage" -> Navigation.findNavController(view)
                .navigate(R.id.action_storageFragment_to_homeFragment)
            else -> Navigation.findNavController(view)
                .navigate(R.id.action_sendFragment_to_homeFragment)
        }
    }


    override fun onPause() {
        Log.i("baseFragment", "onPause")
        super.onPause();
        countDownTimer?.cancel() //销毁定时器
        countDownTimer = null
    }

}