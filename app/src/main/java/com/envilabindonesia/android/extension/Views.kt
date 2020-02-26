package com.envilabindonesia.android.extension

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.Handler
import android.text.Html
import android.text.InputType
import android.text.Spanned
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.Toast
import com.envilabindonesia.android.util.PrefsUtil
import com.google.android.material.textfield.TextInputEditText
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*

/**
 * Created by galihadityo on 2019-04-03.
 */

fun String.toast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun String.fromHtml(): Spanned {
    return if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }
}

fun String.replaceNewlineHtml(): String {
    return this.replace("<br />", "\n")
}

fun String.replaceNewline(): String {
    return this.replace("\n", "<br />")
}

fun String.removeNewline(): String {
    return this.replace("<br />", "")
}

fun Int.toIdr(): String {
    var number = "0"
    if (this >= 0) {
        try {
            val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
            val customSymbol = DecimalFormatSymbols()
            customSymbol.decimalSeparator = ','
            customSymbol.groupingSeparator = '.'
            formatter.decimalFormatSymbols = customSymbol
            formatter.isGroupingUsed = true
            number = formatter.format(this)
        } catch (ex: NumberFormatException) {
            ex.printStackTrace()
        }
    }

    return "Rp $number"
}

fun View.showCurrency() {
    this.visibility = if (PrefsUtil.isCurrencyShow()) View.VISIBLE else View.INVISIBLE
}

fun String.toCapitalize(): String = this.toLowerCase().capitalize()

fun TextInputEditText.asButton() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        this.showSoftInputOnFocus = false
    }
    this.isCursorVisible = false
    this.inputType = InputType.TYPE_NULL
    this.isFocusable = false
}

fun HorizontalScrollView.reset() {
    Handler().postDelayed({
        this.fullScroll(HorizontalScrollView.FOCUS_LEFT)
    }, 100)
}

fun String.copyToClipboard(context: Context?, label: String): String {
    context?.let {
        val clipboard: ClipboardManager? = it.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        clipboard?.primaryClip = ClipData.newPlainText(label, this)
    }
    return this
}