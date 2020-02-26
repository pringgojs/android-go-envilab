package com.envilabindonesia.android.data.repository

import com.envilabindonesia.android.util.PrefsUtil
import com.google.firebase.iid.FirebaseInstanceId
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by galihadityo on 2019-04-05.
 */

class Local {

    fun logout() {
        PrefsUtil.clearDatas()

        Single.fromCallable { deleteFcmId() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    private fun deleteFcmId() {
        try {
            FirebaseInstanceId.getInstance().deleteInstanceId()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}