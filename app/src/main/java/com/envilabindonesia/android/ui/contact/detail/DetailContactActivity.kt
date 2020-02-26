package com.envilabindonesia.android.ui.contact.detail

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseActivity
import com.envilabindonesia.android.data.response.ContactSupportResponse
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.fragment_contact.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*

/**
 * Created by galihadityo on 2019-04-28.
 */

class DetailContactActivity: BaseActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        when (v) {
            btnEmail -> email()
            btnCall -> call()
        }
    }

    companion object {
        val BUNDLE = "BUNDLE"
        fun start(context: Context, contactSupportResponse: ContactSupportResponse) {
            context.startActivity(Intent(context, DetailContactActivity::class.java).apply {
                putExtra(BUNDLE, contactSupportResponse)
            })
        }
    }

    private var contactSupportResponse: ContactSupportResponse? = null

    override fun getLayoutRes(): Int = R.layout.fragment_contact

    override fun init() {
        setSupportActionBar(include_toolbar.toolbar)
        setBackButton(include_toolbar.toolbar) { onBackPressed() }
        supportActionBar?.let {
            it.title = resources.getString(R.string.menu_contract)
        }

        contactSupportResponse = intent.getParcelableExtra(BUNDLE)
        tvBranch.text = contactSupportResponse?.branchname
        tvAddress.text = contactSupportResponse?.address
        tvPhone.text = contactSupportResponse?.phone
        tvEmail.text = contactSupportResponse?.email

        btnEmail.setOnClickListener(this)
        btnCall.setOnClickListener(this)
    }

    private fun email() {
        val email = tvEmail.text.toString()

        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("mailto:?to=$email")
        }

        startActivity(Intent.createChooser(intent, getString(R.string.send_email)))
    }

    private fun call() {
        TedPermission.with(this)
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

}