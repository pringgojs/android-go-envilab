package com.envilabindonesia.android.ui.order.search

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseActivity
import com.envilabindonesia.android.data.response.CompanyResponse
import com.envilabindonesia.android.data.response.TransactionResponse
import com.envilabindonesia.android.extension.RxBus
import com.envilabindonesia.android.extension.RxEvent
import com.envilabindonesia.android.util.ViewUtil
import kotlinx.android.synthetic.main.activity_search_order.*
import kotlinx.android.synthetic.main.layout_toolbar_search.view.*

/**
 * Created by galihadityo on 2019-04-18.
 */

class OrderSearchActivity: BaseActivity() {

    companion object {
        const val BUNDLE = "BUNDLE"

        fun startResult(activity: BaseActivity, requestCode: Int) {
            val intent = Intent(activity.applicationContext, OrderSearchActivity::class.java)
            activity.startActivityForResult(intent, requestCode)
        }
    }

    override fun getLayoutRes(): Int = R.layout.activity_search_order

    override fun init() {
        toolbarSearch.etSearch.hint = getString(R.string.search_order_hint)

        toolbarSearch.btnClose.setOnClickListener {
            finish()
        }

        ViewUtil.EditText.imeSearchListener(toolbarSearch.etSearch) {
            (searchOrderFragment as OrderSearchFragment).onRefresh()
        }

    }

    fun getTextSearch(): String = toolbarSearch.etSearch.text.toString().trim()

    fun sendResult(transactionResponse: TransactionResponse) {
        setResult(Activity.RESULT_OK, Intent().apply { putExtra(BUNDLE, transactionResponse) })
        finish()
    }

}