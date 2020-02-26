package com.envilabindonesia.android.ui.services.search

import android.app.Activity
import android.content.Intent
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseActivity
import com.envilabindonesia.android.data.response.ServiceCorporateResponse
import com.envilabindonesia.android.util.ViewUtil
import kotlinx.android.synthetic.main.activity_search_service.*
import kotlinx.android.synthetic.main.layout_toolbar_search.view.*

/**
 * Created by galihadityo on 2019-04-18.
 */

class SearchServiceActivity: BaseActivity() {

    companion object {
        const val BUNDLE = "SearchServiceActivity.BUNDLE"

        fun startResult(activity: BaseActivity, requestCode: Int) {
            val intent = Intent(activity.applicationContext, SearchServiceActivity::class.java)
            activity.startActivityForResult(intent, requestCode)
        }
    }

    override fun getLayoutRes(): Int = R.layout.activity_search_service

    override fun init() {
        toolbarSearchClient.etSearch.hint = getString(R.string.search_service_hint)

        toolbarSearchClient.btnClose.setOnClickListener {
            finish()
        }

        ViewUtil.EditText.imeSearchListener(toolbarSearchClient.etSearch) {
            (searchServiceFragment as SearchServiceFragment).onRefresh()
        }

    }

    fun getTextSearch(): String = toolbarSearchClient.etSearch.text.toString().trim()

    fun sendResult(serviceCorporateResponse: ServiceCorporateResponse) {
        setResult(Activity.RESULT_OK, Intent().apply {
            putExtra(BUNDLE, serviceCorporateResponse)
        })

        finish()
    }

}