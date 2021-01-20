package com.bepi.ugandroid.base

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bepi.ugandroid.R
import com.bepi.ugandroid.entity.base.Route
import kotlinx.android.synthetic.main.common_title.*
import kotlinx.android.synthetic.main.common_title.view.*
import java.lang.ref.WeakReference
import java.util.*

open class BaseFragment(val route: Route) :
    Fragment() {
    private val COUNT_DELAY: Long = 2 * 1000 //定义总时长 120秒
    var countDownTimer: CountDownTimer? = null
    protected var titleRef: WeakReference<TextView>? = null


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
        titleRef = WeakReference(view.findViewById<View>(R.id.titleText) as TextView)
        titleRef?.get()?.setText(route.title)
        //页面倒计时
        countDownTimer = object : CountDownTimer(COUNT_DELAY, 1000) {
            override fun onFinish() {
                //倒计时结束返回首页
                var action: Int? = null
                when (route.name) {
                    "pickup" -> action = R.id.action_pickupFragment_to_homeFragment
                    "deliver" -> action = R.id.action_deliverFragment_to_homeFragment
                    "storage" -> action = R.id.action_storageFragment_to_homeFragment
                    else -> action = R.id.action_sendFragment_to_homeFragment
                }
                Navigation.findNavController(view).navigate(action)
            }

            override fun onTick(millisUntilFinished: Long) {
                val second = (millisUntilFinished / 1000)
                textCountDown.setText("${second}秒")
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel() //销毁定时器
        countDownTimer = null
    }
}