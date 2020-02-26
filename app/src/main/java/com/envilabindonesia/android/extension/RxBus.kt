package com.envilabindonesia.android.extension

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by galihadityo on 2019-04-10.
 */

class RxBus {
    companion object {
        val publisher: PublishSubject<Any> = PublishSubject.create()

        inline fun <reified T> subscribe(): Observable<T> {
            return publisher.filter {
                it is T
            }.map {
                it as T
            }
        }

        fun post(event: Any) {
            publisher.onNext(event)
        }
    }
}