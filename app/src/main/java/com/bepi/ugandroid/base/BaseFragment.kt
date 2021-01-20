package com.bepi.ugandroid.base

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bepi.ugandroid.R
import com.bepi.ugandroid.base.entity.Route
import kotlinx.android.synthetic.main.common_title.*
import java.lang.ref.WeakReference

open class BaseFragment(val route: Route) :
    Fragment() {
    private val COUNT_DELAY: Long = 120 * 1000 //定义总时长 120秒
    var countDownTimer: CountDownTimer? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }


    fun initView(view: View) {
        initHeader(view)
    }

    /**
     * 标题栏初始化
     */
    fun initHeader(view: View) {
        titleText?.setText(route.title)
        backBlock?.setOnClickListener(View.OnClickListener { view -> backToHome(view) })
        //页面倒计时
        countDownTimer = object : CountDownTimer(COUNT_DELAY, 1000) {
            override fun onFinish() = backToHome(view)//结束倒计时返回首页
            override fun onTick(millisUntilFinished: Long) {
                val second = (millisUntilFinished / 1000)
                textCountDown?.setText("${second}秒")
            }
        }.start()
    }

    fun backToHome(view: View) {
        var action: Int? = null
        when (route.name) {
            "pickup" -> action = R.id.action_pickupFragment_to_homeFragment
            "deliver" -> action = R.id.action_deliverFragment_to_homeFragment
            "storage" -> action = R.id.action_storageFragment_to_homeFragment
            else -> action = R.id.action_sendFragment_to_homeFragment
        }
        Navigation.findNavController(view).navigate(action)
    }

    override fun onPause() {
        Log.i("baseFragment", "onPause")
        super.onPause();
        countDownTimer?.cancel() //销毁定时器
        countDownTimer = null
    }
}