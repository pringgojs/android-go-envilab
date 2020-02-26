package com.envilabindonesia.android.ui.contact

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseFragment
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.base.adapter.BaseAdapterModel
import com.envilabindonesia.android.data.response.ContactSupportResponse
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.getViewModel
import com.envilabindonesia.android.extension.toast
import com.envilabindonesia.android.util.PrefsUtil
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.zopim.android.sdk.api.ZopimChat
import com.zopim.android.sdk.model.VisitorInfo
import com.zopim.android.sdk.prechat.ZopimChatActivity
import kotlinx.android.synthetic.main.fragment_contact_list.*
import javax.inject.Inject


/**
 * Created by galihadityo on 2019-03-11.
 */

class ContactFragment: BaseFragment(), SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy { getViewModel<ContactViewModel>(viewModelFactory) }

    override fun getLayoutRes(): Int = R.layout.fragment_contact_list

    override fun init() {
        baseActivity?.supportActionBar?.title = resources.getString(R.string.menu_contract)

        swipe.setOnRefreshListener(this)
        btnEmail.setOnClickListener(this)
        btnCall.setOnClickListener(this)
        btnWa.setOnClickListener(this)

        observe()
        onRefresh()

        val login = PrefsUtil.getLoginResponse()
        ZopimChat.setVisitorInfo(
            VisitorInfo.Builder()
                .name(login?.fullName ?: return)
                .email(login.email ?: return)
                .phoneNumber(login.phone ?: return)
                .build()
        )

        btnChat.setOnClickListener {
            startActivity(Intent(activity, ZopimChatActivity::class.java))
        }

        showcaseCall()
    }

    private fun showcaseCall() {
        showcaseViewRect(
            PrefsUtil.SHOWCASE_SUPPORT_CALL,
            R.id.btnCall,
            getString(R.string.showcase_support_call),
            getString(R.string.showcase_support_call_subtitle)
        ) {
            showcaseEmail()
        }
    }

    private fun showcaseEmail() {
        showcaseViewRect(
            PrefsUtil.SHOWCASE_SUPPORT_EMAIL,
            R.id.btnEmail,
            getString(R.string.showcase_support_email),
            getString(R.string.showcase_support_email_subtitle)
        ) {
            showcaseWa()
        }
    }

    private fun showcaseChat() {
        showcaseViewRect(
            PrefsUtil.SHOWCASE_SUPPORT_CHAT,
            R.id.btnChat,
            getString(R.string.showcase_support_chat),
            getString(R.string.showcase_support_chat_subtitle)
        ) {}
    }

    private fun showcaseWa() {
        showcaseViewRect(
            PrefsUtil.SHOWCASE_SUPPORT_WA,
            R.id.btnWa,
            getString(R.string.showcase_support_wa),
            getString(R.string.showcase_support_wa_subtitle)
        ) {}
    }

    private fun observe() {
        viewModel.onSuccess().observe(this, BaseObserver {
            val list: ArrayList<BaseAdapterModel<ContactSupportResponse>> =
                it.result?.map { datas -> BaseAdapterModel(datas.branchname, datas) }
                    ?.toCollection(ArrayList())
                    ?: arrayListOf()

            if (list.size == 0) {
                layoutContact.visibility = View.INVISIBLE
                return@BaseObserver
            } else {
                layoutContact.visibility = View.VISIBLE
            }

            val contactSupportResponse = list.first().data
            tvBranch.text = contactSupportResponse?.branchname
            tvAddress.text = contactSupportResponse?.address
            tvPhone.text = contactSupportResponse?.phone
            tvEmail.text = contactSupportResponse?.email
            tvWa.text = contactSupportResponse?.whatsapp
            tvNotes.text = contactSupportResponse?.note
        })

        viewModel.onLoading().observe(this, BaseObserver {
            swipe.isRefreshing = it
        })

        viewModel.onError().observe(this, BaseObserver {
            activity?.let { it1 -> it.toast(it1) }
        })
    }

    override fun onRefresh() {
        viewModel.getContactSupport()
    }

    override fun onClick(v: View?) {
        when (v) {
            btnEmail -> email()
            btnCall -> call()
            btnWa -> whatsapp()
        }
    }

    private fun email() {
        val email = tvEmail.text.toString()

        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("mailto:?to=$email")
        }

        startActivity(Intent.createChooser(intent, getString(R.string.send_email)))
    }

    private fun call() {
        TedPermission.with(activity)
            .setPermissions(Manifest.permission.CALL_PHONE)
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    val phone = tvPhone.text.toString()
                        .replace(" ", "")

                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("tel:$phone")
                    }

                    startActivity(Intent.createChooser(intent, ""))
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                }
            })
            .check()
    }

    private fun whatsapp() {
        val phoneNumber = tvWa.text.toString()
        val pm = activity?.packageManager ?: return
        try {
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://wa.me/$phoneNumber")
            }.also {
                startActivity(it)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            activity?.let { getString(R.string.error_wa_not_install).toast(it) }
        }
    }

}