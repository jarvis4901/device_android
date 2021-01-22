package com.bepi.ugandroid.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.bepi.ugandroid.ui.AdActivity

abstract class BaseActivity : AppCompatActivity() {
    var mContext: Context? = null

    private val COUNT_DOWN_TIME = 2000
    private var ADcountDownTimer: CountDownTimer? = null

    companion object {
        val TAG = "BaseActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        mContext = this
        //开启自定义倒计时
        ADcountDownTimer = object : CountDownTimer(COUNT_DOWN_TIME.toLong(), 1000) {
            override fun onTick(l: Long) {
                // 计时过程显示剩余时间
                val i = (l / 1000).toInt()
                Log.i(TAG, i.toString())
            }

            override fun onFinish() {
                // 跳转至广告悬停页面
                ADcountDownTimer?.cancel()
                val intent: Intent = Intent(mContext, AdActivity::class.java)
                startActivity(intent)
            }
        }.start()
        initView()
    }

    abstract fun initView()

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            //获取触摸动作 如果是ACTION_UP 则开始计时
            MotionEvent.ACTION_UP -> ADcountDownTimer?.start()
            else -> {
                //其他动作取消计时
                ADcountDownTimer?.cancel()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onPause() {
        Log.i(TAG, "ON PAUSE")
        super.onPause()
        ADcountDownTimer?.cancel()
    }

    override fun onResume() {
        Log.i(TAG, "ON Resume")
        super.onResume()
        ADcountDownTimer?.start()
    }

}