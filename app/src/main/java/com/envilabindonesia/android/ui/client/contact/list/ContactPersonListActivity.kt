package com.envilabindonesia.android.ui.client.contact.list

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseActivity
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.base.adapter.BaseAdapter
import com.envilabindonesia.android.base.adapter.BaseAdapterModel
import com.envilabindonesia.android.data.response.CompanyResponse
import com.envilabindonesia.android.data.response.ContactPersonResponse
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.getViewModel
import com.envilabindonesia.android.extension.toast
import com.envilabindonesia.android.ui.client.contact.ContactPersonActivity
import com.envilabindonesia.android.util.PrefsUtil
import kotlinx.android.synthetic.main.activity_contact_person_list.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-04-21.
 */

class ContactPersonListActivity: BaseActivity(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        const val BUNDLE = "ContactPersonListActivity.BUNDLE"
        const val IS_SELECTION_CP = "IS_SELECTION_CP"

        fun start(context: Context, companyResponse: CompanyResponse) {
            context.startActivity(Intent(context, ContactPersonListActivity::class.java).apply {
                putExtra(BUNDLE, companyResponse)
            })
        }

        fun startResult(activity: BaseActivity, companyResponse: CompanyResponse, requestCode: Int) {
            val intent = Intent(activity.applicationContext, ContactPersonListActivity::class.java).apply {
                putExtra(BUNDLE, companyResponse)
                putExtra(IS_SELECTION_CP, true)
            }
            activity.startActivityForResult(intent, requestCode)
        }
    }

    private var mAdapter: BaseAdapter<ContactPersonResponse, ContactPersonAdapter.ViewHolder>? = null
    private var companyResponse: CompanyResponse? = null
    private var isSelectionContactPerson: Boolean = false

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy { getViewModel<ContactPersonListViewModel>(viewModelFactory) }

    override fun getLayoutRes(): Int = R.layout.activity_contact_person_list

    override fun init() {
        companyResponse = intent.getParcelableExtra(BUNDLE)
        isSelectionContactPerson = intent.hasExtra(IS_SELECTION_CP)
            .and(intent.getBooleanExtra(IS_SELECTION_CP, false))

        setSupportActionBar(toolbar)
        setBackButton(toolbar) { finish() }
        supportActionBar?.title = getString(R.string.contact_person)

        swipeRefresh.setOnRefreshListener(this)

        observe()

        mAdapter = BaseAdapter(ContactPersonAdapter()) {
            if (isSelectionContactPerson) {
                setResult(Activity.RESULT_OK, Intent().apply {
                    putExtra(BUNDLE, it)
                })
                finish()
            } else {
                ContactPersonActivity.startUpdate(this, it)
            }
        }

        rv.adapter = mAdapter

        showcaseCreate()
    }

    private fun showcaseCreate() {
        showcaseToolbar(
            PrefsUtil.SHOWCASE_CREATE_CONTACT,
            R.id.create_contact_person,
            R.drawable.ic_add_toolbar,
            getString(R.string.showcase_contact),
            getString(R.string.showcase_contact_create)
        ) {}
    }

    override fun onResume() {
        super.onResume()
        onRefresh()
    }

    private fun observe() {
        viewModel.onSuccess().observe(this, BaseObserver {
            val list: ArrayList<BaseAdapterModel<ContactPersonResponse>> =
                it.result?.rows?.map { data -> BaseAdapterModel("", data) }
                    ?.toCollection(ArrayList())
                    ?: arrayListOf()

            mAdapter?.addAll(list)
        })

        viewModel.onLoading().observe(this, BaseObserver {
            swipeRefresh.isRefreshing = it
        })

        viewModel.onError().observe(this, BaseObserver {
            it.toast(this)
        })
    }

    override fun onRefresh() {
        companyResponse?.companyId?.let { viewModel.getContactPerson(it) }
            ?: getString(R.string.company_id_null).toast(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.contact_person, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.create_contact_person -> {
                companyResponse?.let { ContactPersonActivity.startAdd(this, it) }
                    ?: getString(R.string.company_id_null).toast(this)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}