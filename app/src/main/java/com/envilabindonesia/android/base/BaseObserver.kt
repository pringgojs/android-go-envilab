package com.envilabindonesia.android.base

import androidx.lifecycle.Observer

/**
 * Created by galihadityo on 2019-03-28.
 */

class BaseObserver<T>(private val block: (T) -> Unit) : Observer<T> {

    override fun onChanged(it: T?) {
        it?.let {
            block(it)
        }
    }

}