package com.envilabindonesia.android.ui.client.search

import android.app.Activity
import android.content.Intent
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseActivity
import com.envilabindonesia.android.data.response.CompanyResponse
import com.envilabindonesia.android.util.ViewUtil
import kotlinx.android.synthetic.main.activity_search_client.*
import kotlinx.android.synthetic.main.layout_toolbar_search.view.*

/**
 * Created by galihadityo on 2019-04-18.
 */

class SearchClientActivity: BaseActivity() {

    companion object {
        const val BUNDLE = "SearchClientActivity.BUNDLE"

        fun startResult(activity: BaseActivity, requestCode: Int) {
            val intent = Intent(activity.applicationContext, SearchClientActivity::class.java)
            activity.startActivityForResult(intent, requestCode)
        }
    }

    override fun getLayoutRes(): Int = R.layout.activity_search_client

    override fun init() {
        toolbarSearchClient.etSearch.hint = getString(R.string.search_company_hint)

        toolbarSearchClient.btnClose.setOnClickListener {
            finish()
        }

        ViewUtil.EditText.imeSearchListener(toolbarSearchClient.etSearch) {
            (searchClientFragment as SearchClientFragment).onRefresh()
        }

    }

    fun getTextSearch(): String = toolbarSearchClient.etSearch.text.toString().trim()

    fun sendResult(companyResponse: CompanyResponse) {
        setResult(Activity.RESULT_OK, Intent().apply {
            putExtra(BUNDLE, companyResponse)
        })

        finish()
    }

}