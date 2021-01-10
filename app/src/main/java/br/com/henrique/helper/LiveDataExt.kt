package br.com.henrique.helper

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

@MainThread
fun <T> LiveData<T>.setState(value: T) {
    require(this is MutableLiveData) { "$this isn't valid MutableLiveDataInstance" }

    this.value = value
}

@MainThread
fun <T> LifecycleOwner.listenState(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, { it?.let { (action(it)) } })
}