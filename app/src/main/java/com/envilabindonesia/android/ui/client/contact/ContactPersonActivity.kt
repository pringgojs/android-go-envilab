package com.envilabindonesia.android.ui.client.contact

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuItem
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseActivity
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.data.request.ContactPersonAddRequest
import com.envilabindonesia.android.data.request.ContactPersonUpdateRequest
import com.envilabindonesia.android.data.response.CompanyResponse
import com.envilabindonesia.android.data.response.ContactPersonResponse
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.getViewModel
import com.envilabindonesia.android.extension.toast
import com.envilabindonesia.android.util.PhoneUtil
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_contact_person.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-04-21.
 */

class ContactPersonActivity: BaseActivity() {

    companion object {
        const val BUNDLE_ADD = "BUNDLE_ADD"
        const val BUNDLE_UPDATE = "BUNDLE_UPDATE"
        fun startAdd(context: Context, companyResponse: CompanyResponse) {
            context.startActivity(Intent(context, ContactPersonActivity::class.java).apply {
                putExtra(BUNDLE_ADD, companyResponse)
            })
        }

        fun startUpdate(context: Context, contactPersonResponse: ContactPersonResponse) {
            context.startActivity(Intent(context, ContactPersonActivity::class.java).apply {
                putExtra(BUNDLE_UPDATE, contactPersonResponse)
            })
        }
    }

    private var contactPersonResponse: ContactPersonResponse? = null
    private var companyResponse: CompanyResponse? = null
    private var isUpdate: Boolean = false

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy { getViewModel<ContactPersonViewModel>(viewModelFactory) }

    override fun getLayoutRes(): Int = R.layout.activity_contact_person

    override fun init() {
        bundle()
        setToolbar()
        observe()
        initData()
        setLoadingButton(R.id.include_contact_person, R.string.save) {
            if (isUpdate) postUpdate() else postAdd()
        }
    }

    private fun initData() {
        if (!isUpdate) return
        etName.setText(contactPersonResponse?.cpName)
        etEmail.setText(contactPersonResponse?.cpEmail)
        etOccupation.setText(contactPersonResponse?.cpPosition)
        etPhone.setText(PhoneUtil.areaRemove(contactPersonResponse?.cpPhone))
    }

    private fun observe() {
        viewModel.onLoading().observe(this, BaseObserver{
            isLoading(it)
        })

        viewModel.onError().observe(this, BaseObserver{
            it.toast(this)
        })

        viewModel.onSuccess().observe(this, BaseObserver{
            it.message?.toast(this)
            finish()
        })
    }

    private fun postUpdate() {
        val request = ContactPersonUpdateRequest(
            companyId = contactPersonResponse?.companyId,
            cpEmail = etEmail.text.toString().trim(),
            cpId = contactPersonResponse?.cpId,
            cpName = etName.text.toString().trim(),
            cpPhone = PhoneUtil.areaAdd(etPhone.text.toString().trim()),
            cpPosition = etOccupation.text.toString().trim()
        )

        viewModel.postContactPersonUpdate(request)
    }

    private fun postAdd() {
        val request = ContactPersonAddRequest(
            companyId = companyResponse?.companyId,
            cpEmail = etEmail.text.toString().trim(),
            cpName = etName.text.toString().trim(),
            cpPhone = PhoneUtil.areaAdd(etPhone.text.toString().trim()),
            cpPosition = etOccupation.text.toString().trim()
        )

        viewModel.postContactPersonAdd(request)
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        setBackButton(toolbar) { finish() }
        supportActionBar?.title =
                if (isUpdate) getString(R.string.contact_person_update)
                else getString(R.string.contact_person_add)
    }

    private fun bundle() {
        if (intent.hasExtra(BUNDLE_ADD)) companyResponse = intent.getParcelableExtra(BUNDLE_ADD)
        if (intent.hasExtra(BUNDLE_UPDATE)) {
            contactPersonResponse = intent.getParcelableExtra(BUNDLE_UPDATE)
            isUpdate = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isUpdate) menuInflater.inflate(R.menu.contact_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (!isUpdate) return super.onOptionsItemSelected(item)

        return when (item.itemId) {
            R.id.contact_person_call -> {
                if (etPhone.text.toString().trim().isEmpty()) {
                    getString(R.string.phone_mandatory).toast(this)
                } else {
                    TedPermission.with(this)
                        .setPermissions(Manifest.permission.CALL_PHONE)
                        .setPermissionListener(object : PermissionListener {
                            override fun onPermissionGranted() {
                                val phone = PhoneUtil.areaAdd(etPhone.text.toString().trim())
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
                true
            }

            R.id.contact_person_email -> {
                if (etEmail.text.toString().trim().isEmpty()) {
                    getString(R.string.email_mandatory).toast(this)
                } else {
                    val email = etEmail.text.toString().trim()

                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("mailto:?to=$email")
                    }

                    startActivity(Intent.createChooser(intent, getString(R.string.send_email)))
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}