package com.envilabindonesia.android.base

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.adapter.BaseAdapterModel
import com.envilabindonesia.android.base.adapter.BaseAdapterLoadmore
import com.envilabindonesia.android.base.adapter.BaseViewHolderLoadmore
import kotlinx.android.synthetic.main.fragment_base_loadmore.*

/**
 * Created by galihadityo on 2019-04-15.
 */

abstract class BaseFragmentLoadmore<T, VH: RecyclerView.ViewHolder>: BaseFragment(),
    SwipeRefreshLayout.OnRefreshListener {

    abstract fun getViewHolder(): BaseViewHolderLoadmore<T, VH>

    abstract fun initView()

    interface Listener<T> {
        fun onPullRefresh()
        fun onLoadMore(page: Int, totalItem: Int)
        fun onItemSelected(t: T)

        companion object {
            const val LIMIT = 20
        }
    }

    var mPage: Int = 0

    private var adapter: BaseAdapterLoadmore<T, VH>? = null

    private var listener: Listener<T>? = null

    private lateinit var baseLoadmoreListener: BaseLoadmoreListener

    private var isLoading: Boolean = false

    override fun getLayoutRes(): Int = R.layout.fragment_base_loadmore

    override fun init() {
        initSwipeRefresh()
        initAdapter()
        initRecyclerView()
        initView()
    }

    private fun initAdapter() {
        adapter = BaseAdapterLoadmore { listener?.onItemSelected(it) }
        adapter?.setDataViewHolder(getViewHolder())
    }

    private fun initSwipeRefresh() {
        swipeLoadmore.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        resetLoadmore()
        listener?.onPullRefresh()
    }

    private fun resetLoadmore() {
        mPage = 0
        adapter?.clear()
        baseLoadmoreListener.resetState()
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)
        baseLoadmoreListener = object : BaseLoadmoreListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItem: Int, view: RecyclerView) {
                if (!isLoading) {
                    isLoading = true
                    mPage = mPage.plus(Listener.LIMIT)
                    listener?.onLoadMore(mPage, totalItem)
                }
            }
        }

        rvLoadmore.layoutManager = layoutManager
        rvLoadmore.adapter = adapter
        rvLoadmore.addOnScrollListener(baseLoadmoreListener)
    }

    fun setListener(listener: Listener<T>) {
        this.listener = listener
    }

    fun addOnRefresh(items: ArrayList<BaseAdapterModel<T>>?) {
        adapter?.clear()
        adapter?.add(items ?: arrayListOf())
    }

    fun addOnLoadmore(items: ArrayList<BaseAdapterModel<T>>?) {
        adapter?.add(items?: arrayListOf())
    }

    fun showLoading(isLoading: Boolean) {
        this.isLoading = isLoading
        if (mPage == 0) {
            swipeLoadmore.isRefreshing = isLoading
        } else {
            if (isLoading) adapter?.showLoadmore() else adapter?.hideLoadmore()
        }
    }

    fun getSwipeRefresh(): SwipeRefreshLayout = swipeLoadmore
}