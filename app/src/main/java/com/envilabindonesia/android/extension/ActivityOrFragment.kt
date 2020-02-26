package com.envilabindonesia.android.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

/**
 * Created by galihadityo on 2019-03-27.
 */

internal inline fun <reified T : ViewModel> AppCompatActivity.getViewModel(factory: ViewModelProvider.Factory = ViewModelProvider.NewInstanceFactory()): T {
    return ViewModelProviders.of(this, factory).get(T::class.java)
}

internal inline fun <reified T : ViewModel> Fragment.getViewModel(factory: ViewModelProvider.Factory = ViewModelProvider.NewInstanceFactory()): T {
    return ViewModelProviders.of(this, factory).get(T::class.java)
}