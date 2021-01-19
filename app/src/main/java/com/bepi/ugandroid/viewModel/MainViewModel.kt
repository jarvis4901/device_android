package com.bepi.ugandroid.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    lateinit private var countDown: MutableLiveData<Int>
    lateinit private var title: MutableLiveData<String>

    public fun getCountDown(): MutableLiveData<Int> {
        return countDown
    }

    public fun getTitle(): MutableLiveData<String> {
        return title
    }

}