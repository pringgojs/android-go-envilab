package com.envilabindonesia.android.util

import android.content.Context
import androidx.appcompat.app.AlertDialog

/**
 * Created by galihadityo on 2019-03-24.
 */

object DialogUtil {

    fun chooser(context: Context, items: Array<CharSequence>, onClick: (CharSequence) -> Unit): AlertDialog? {
        return AlertDialog.Builder(context)
            .setItems(items) { _, which -> onClick(items[which]) }
            .create()
    }

}