package com.bepi.ugandroid.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bepi.ugandroid.R
import com.bepi.ugandroid.base.BaseActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    companion object {
        val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
    }

    override fun initView() {
        setContentView(R.layout.activity_main)
        initBanner()
    }

    //初始化顶部banner
    fun initBanner() {
        val resource: Int = R.mipmap.banner
        Glide.with(this).load(resource).into(imgBanner)
    }
}