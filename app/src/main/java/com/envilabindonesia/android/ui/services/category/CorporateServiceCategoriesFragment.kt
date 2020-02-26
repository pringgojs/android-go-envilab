package com.envilabindonesia.android.ui.services.category

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
import com.envilabindonesia.android.data.response.ServiceCategoryResponse
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.getViewModel
import com.envilabindonesia.android.extension.toast
import com.envilabindonesia.android.ui.services.CorporateServiceFragment
import com.envilabindonesia.android.ui.services.search.SearchServiceActivity
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_corporate_service_categories.*
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-04-20.
 */

class CorporateServiceCategoriesFragment: BaseFragment() {

    companion object {
        const val VF_DATA = 0
        const val VF_LOADING = 1
        const val VF_RETRY = 2
    }

    private var mList: ArrayList<ServiceCategoryResponse> = arrayListOf()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy { getViewModel<CorporateServiceCategoriesViewModel>(viewModelFactory) }

    override fun getLayoutRes(): Int = R.layout.fragment_corporate_service_categories

    override fun init() {
        baseActivity?.supportActionBar?.title = resources.getString(R.string.menu_corporate)
        observe()
        btnRetry.setOnClickListener { viewModel.getCategories() }
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

    private fun renderCategories(result: List<ServiceCategoryResponse>?) {
        mList = ArrayList(result)
        mList.forEach { data ->
                    val v = LayoutInflater.from(activity).inflate(R.layout.item_chip, chipGroup, false)
                    v.findViewById<Chip>(R.id.chip).text = data.catName
                    v.findViewById<Chip>(R.id.chip).setOnClickListener {
                        data.catName?.let { it1 ->
                            setCheckedChip(it1)
                            setFragmentCorporate(data)
                        }
                    }
                    chipGroup.addView(v)
        }

        // set first chip
        if (!mList.isNullOrEmpty()) {
            setCheckedChip(mList[0].catName ?: return)
            setFragmentCorporate(mList[0])
        }
    }

    private fun setCheckedChip(chipText: String) {
        chipGroup.forEach {
            val title = (it as Chip).text.toString()
            it.isChecked = chipText.equals(title, true)
        }
    }

    private fun setFragmentCorporate(data: ServiceCategoryResponse) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.fragmentPlaceholder, CorporateServiceFragment.newInstance(data), data.catId.toString())
            ?.commitNowAllowingStateLoss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.corporate_service_category, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.service_search -> {
                baseActivity?.let { SearchServiceActivity.startResult(it, Constant.ActivityResult.RC_SEARCH_SERVICE) }
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item) {
//            R.id.order_search -> {
//            }
//            {
//                //                startActivityForResult(Intent(activity, OrderSearchActivity::class.java),
////                    Constant.ActivityResult.RC_SEARCH_ORDER)
//                true
//            }
//
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
//    }
}