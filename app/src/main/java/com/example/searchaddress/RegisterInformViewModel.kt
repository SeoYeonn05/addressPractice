package com.example.searchaddress

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterInformViewModel:ViewModel() {

    private val _nickName = MutableLiveData<String>()
    private val _address = MutableLiveData<String>()

    val nickname: LiveData<String>
        get() = _nickName
    val address: LiveData<String>
        get() = _address


}