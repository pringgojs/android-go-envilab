package com.envilabindonesia.android.ui.review

import android.content.Context
import android.content.Intent
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseActivity
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.base.adapter.BaseAdapter
import com.envilabindonesia.android.base.adapter.BaseAdapterModel
import com.envilabindonesia.android.data.request.ReviewRequest
import com.envilabindonesia.android.data.response.Rate
import com.envilabindonesia.android.data.response.RatingResponse
import com.envilabindonesia.android.data.response.TransactionResponse
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.getViewModel
import com.envilabindonesia.android.extension.toast
import kotlinx.android.synthetic.main.activity_contact_person_list.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-04-21.
 */

class ReviewListActivity: BaseActivity(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        const val BUNDLE = "ReviewListActivity.BUNDLE"

        fun start(context: Context, response: TransactionResponse) {
            context.startActivity(Intent(context, ReviewListActivity::class.java).apply {
                putExtra(BUNDLE, response)
            })
        }
    }

    private var mAdapter: BaseAdapter<Rate, ReviewAdapter.ViewHolder>? = null
    private var response: TransactionResponse? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy { getViewModel<ReviewListViewModel>(viewModelFactory) }
    private val viewModelReview by lazy { getViewModel<ReviewAddViewModel>(viewModelFactory) }

    override fun getLayoutRes(): Int = R.layout.activity_review_list

    override fun init() {
        response = intent.getParcelableExtra(BUNDLE)

        setSupportActionBar(toolbar)
        setBackButton(toolbar) { finish() }
        supportActionBar?.title = getString(R.string.button_review).toLowerCase().capitalize()

        swipeRefresh.setOnRefreshListener(this)

        observe()
        observeReview()

        mAdapter = BaseAdapter(ReviewAdapter()) {
            if (ratingResponse?.rateAccess == true) {
                val dialog = ReviewDialog.newInstance(it, response ?: return@BaseAdapter)
                dialog.setListener(object : ReviewDialog.OnReviewDialogListener {
                    override fun onSubmit(reviewRequest: ReviewRequest) {
                        viewModelReview.postReview(reviewRequest)
                    }
                })

                try {
                    if (dialog.isAdded) dialog.dismissAllowingStateLoss()
                    dialog.show(supportFragmentManager, "")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                getString(R.string.review_access_deny).toast(this)
            }
        }

        rv.adapter = mAdapter
    }

    private fun observeReview() {
        viewModelReview.onSuccess().observe(this, BaseObserver {
            it.message?.toast(this)
            onRefresh()
        })

        viewModelReview.onLoading().observe(this, BaseObserver {
            swipeRefresh.isRefreshing = it
        })

        viewModelReview.onError().observe(this, BaseObserver {
            it.toast(this)
        })
    }

    override fun onResume() {
        super.onResume()
        onRefresh()
    }

    private var ratingResponse: RatingResponse? = null

    private fun observe() {
        viewModel.onSuccess().observe(this, BaseObserver {
            ratingResponse = it.result
            val list: ArrayList<BaseAdapterModel<Rate>> =
                it.result?.rate?.map { data -> BaseAdapterModel("", data) }
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
        response?.orderId.let { it?.let { it1 -> viewModel.getRating(it1) } }
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