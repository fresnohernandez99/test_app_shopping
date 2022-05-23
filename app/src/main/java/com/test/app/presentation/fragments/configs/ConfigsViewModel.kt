package com.test.app.presentation.fragments.configs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConfigsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Configs Fragment"
    }
    val text: LiveData<String> = _text
}