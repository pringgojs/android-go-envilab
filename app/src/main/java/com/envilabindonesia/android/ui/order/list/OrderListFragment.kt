package com.envilabindonesia.android.ui.order.list

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.forEach
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseFragment
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.data.Constant
import com.envilabindonesia.android.data.response.TransactionResponse
import com.envilabindonesia.android.data.response.TransactionStatusResponse
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.getViewModel
import com.envilabindonesia.android.extension.reset
import com.envilabindonesia.android.extension.toCapitalize
import com.envilabindonesia.android.extension.toast
import com.envilabindonesia.android.ui.order.detail.OrderDetailActivity
import com.envilabindonesia.android.ui.order.list.item.OrderListPerItemFragment
import com.envilabindonesia.android.ui.order.search.OrderSearchActivity
import com.envilabindonesia.android.util.PrefsUtil
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_order_list.*
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-04-20.
 */

class OrderListFragment: BaseFragment() {

    companion object {
        const val VF_DATA = 0
        const val VF_LOADING = 1
        const val VF_RETRY = 2
    }

    private var mList: ArrayList<TransactionStatusResponse> = arrayListOf()
    private var isSubordinate: Boolean? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy { getViewModel<OrderListViewModel>(viewModelFactory) }

    override fun getLayoutRes(): Int = R.layout.fragment_order_list

    override fun init() {
        isSubordinate = PrefsUtil.isOrderSubordinate()

        observe()
        btnRetry.setOnClickListener { viewModel.getTransactionStatus() }

        if (isSubordinate == true) {
            baseActivity?.supportActionBar?.title = resources.getString(R.string.menu_order_subordinate)
        } else {
            baseActivity?.supportActionBar?.title = resources.getString(R.string.menu_order)
            showcaseCreate()
        }
    }

    private fun showcaseCreate() {
        showcaseToolbar(
            PrefsUtil.SHOWCASE_HOME_ORDER,
            R.id.order_add,
            R.drawable.ic_add_toolbar,
            getString(R.string.showcase_order),
            getString(R.string.showcase_order_create)
        ) {}
    }

    override fun onResume() {
        super.onResume()
        btnRetry.performClick()
    }

    private fun observe() {
        viewModel.onLoading().observe(this, BaseObserver{
            if (it) vf.displayedChild = VF_LOADING
        })

        viewModel.onError().observe(this, BaseObserver{
            vf.displayedChild = VF_RETRY
            activity?.let { it1 -> it.toast(it1) }
        })

        viewModel.onSuccess().observe(this, BaseObserver{
            vf.displayedChild = VF_DATA
            renderCategories(it.result)
        })
    }

    private fun renderCategories(result: List<TransactionStatusResponse>?) {
        chipGroup.removeAllViews()
        mList = ArrayList(result)
        mList.forEach { data ->
                    val v = LayoutInflater.from(activity).inflate(R.layout.item_chip, chipGroup, false)
                    v.findViewById<Chip>(R.id.chip).text = data.statusName?.toCapitalize() ?: "null"
                    v.findViewById<Chip>(R.id.chip).setOnClickListener {
                        data.statusName?.let { it1 ->
                            setCheckedChip(it1)
                            setFragmentCorporate(data)
                        }
                    }
                    chipGroup.addView(v)
        }

        // set first chip
        if (!mList.isNullOrEmpty()) {
            setCheckedChip(mList[0].statusName ?: return)
            setFragmentCorporate(mList[0])
            layoutChip.reset()
        }
    }

    private fun setCheckedChip(chipText: String) {
        chipGroup.forEach {
            val title = (it as Chip).text.toString()
            it.isChecked = chipText.equals(title, true)
        }
    }

    private fun setFragmentCorporate(data: TransactionStatusResponse) {
//        activity?.supportFragmentManager
        if (isAdded) {
            childFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragmentPlaceholder,
                    OrderListPerItemFragment.newInstance(data, isSubordinate ?: false),
                    data.statusId.toString()
                )
                .commitNowAllowingStateLoss()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (isSubordinate == true)
            inflater.inflate(R.menu.order_subordinate, menu)
        else
            inflater.inflate(R.menu.order, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.order_add -> {
                getHomeActivity().addOrder()
                true
            }

            R.id.order_search -> {
                startActivityForResult(Intent(activity, OrderSearchActivity::class.java),
                    Constant.ActivityResult.RC_SEARCH_ORDER)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == Constant.ActivityResult.RC_SEARCH_ORDER) {
            data?.let {
                toDetailActivity(it.getParcelableExtra(OrderSearchActivity.BUNDLE))
            }
        }
    }

    private fun toDetailActivity(transactionResponse: TransactionResponse) {
        activity?.let { OrderDetailActivity.start(it, transactionResponse) }
    }

}