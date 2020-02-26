package com.envilabindonesia.android.ui.schedule.list

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseActivity
import com.envilabindonesia.android.base.adapter.BaseAdapter
import com.envilabindonesia.android.base.adapter.BaseAdapterModel
import com.envilabindonesia.android.data.response.TransactionResponse
import kotlinx.android.synthetic.main.activity_contact_person_list.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * Created by galihadityo on 2019-04-21.
 */

class ScheduleSamplingListActivity: BaseActivity() {

    companion object {
        const val BUNDLE = "ScheduleSamplingListActivity.BUNDLE"
        const val BUNDLE_TITLE = "ScheduleSamplingListActivity.BUNDLE_TITLE"

        fun start(context: Context, response: ArrayList<TransactionResponse>, title: String) {
            context.startActivity(Intent(context, ScheduleSamplingListActivity::class.java).apply {
                putParcelableArrayListExtra(BUNDLE, response)
                putExtra(BUNDLE_TITLE, title)
            })
        }
    }

    private var mAdapter: BaseAdapter<TransactionResponse, ScheduleSamplingAdapter.ViewHolder>? = null
    private var response: ArrayList<TransactionResponse> = arrayListOf()

    override fun getLayoutRes(): Int = R.layout.activity_contact_person_list

    override fun init() {
        setSupportActionBar(toolbar)
        setBackButton(toolbar) { finish() }
        supportActionBar?.title = intent.getStringExtra(BUNDLE_TITLE)
        response = intent.getParcelableArrayListExtra(BUNDLE)

        mAdapter = BaseAdapter(ScheduleSamplingAdapter()) {

        }

        rv.apply {
            layoutManager = LinearLayoutManager(this@ScheduleSamplingListActivity)
            adapter = mAdapter
        }

        val list: ArrayList<BaseAdapterModel<TransactionResponse>> =
            response.map { datas -> BaseAdapterModel("", datas) }
                .toCollection(ArrayList())

        mAdapter?.addAll(list)
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.contact_person, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.create_contact_person -> {
//                companyResponse?.let { ContactPersonActivity.startAdd(this, it) }
//                    ?: getString(R.string.company_id_null).toast(this)
//                true
//            }
//
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

}