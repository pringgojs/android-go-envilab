package com.envilabindonesia.android.ui.client.edit

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseActivity
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.data.request.CompanyEditRequest
import com.envilabindonesia.android.data.response.CompanyResponse
import com.envilabindonesia.android.data.response.DistrictResponse
import com.envilabindonesia.android.data.response.ProvincesResponse
import com.envilabindonesia.android.data.response.RegencyResponse
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.asButton
import com.envilabindonesia.android.extension.getViewModel
import com.envilabindonesia.android.extension.toast
import com.envilabindonesia.android.ui.account.DistrictsViewModel
import com.envilabindonesia.android.ui.account.ProvincesViewModel
import com.envilabindonesia.android.ui.account.RegenciesViewModel
import com.envilabindonesia.android.ui.client.contact.list.ContactPersonListActivity
import com.envilabindonesia.android.util.PhoneUtil
import com.envilabindonesia.android.util.PrefsUtil
import kotlinx.android.synthetic.main.activity_client_edit.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import javax.inject.Inject

class ClientEditActivity : BaseActivity(), View.OnClickListener {

    companion object {
        const val BUNDLE = "BUNDLE"
        fun start(context: Context, companyResponse: CompanyResponse) {
            context.startActivity(Intent(context, ClientEditActivity::class.java).apply {
                putExtra(BUNDLE, companyResponse)
            })
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModelCompany by lazy { getViewModel<ClientEditViewModel>(viewModelFactory) }
    private val viewModelProvinces by lazy { getViewModel<ProvincesViewModel>(viewModelFactory) }
    private val viewModelRegencies by lazy { getViewModel<RegenciesViewModel>(viewModelFactory) }
    private val viewModelDistricts by lazy { getViewModel<DistrictsViewModel>(viewModelFactory) }

    private var responseProvinces: ProvincesResponse? = null
    private var responseRegency: RegencyResponse? = null
    private var responseDistrict: DistrictResponse? = null

    private var listProvinces: ArrayList<ProvincesResponse>? = null
    private var listRegencies: ArrayList<RegencyResponse>? = null
    private var listDistricts: ArrayList<DistrictResponse>? = null

    private var dialogProvinces: AlertDialog? = null
    private var dialogRegencies: AlertDialog? = null
    private var dialogDistricts: AlertDialog? = null

    private val displayForm = 0
    private val displayLoading = 1

    private var companyResponse: CompanyResponse? = null

    private var isSubmitProvinces: Boolean = false
    private var isSubmitRegency: Boolean = false

    override fun getLayoutRes(): Int = R.layout.activity_client_edit

    override fun init() {
        companyResponse = intent.getParcelableExtra(BUNDLE)
        initToolbar()

        observe()
        observeProvinces()
        observeRegencies()
        observeDistrics()

        initData()
        initView()
        initForm()
        listenerProvinces()
        listenerRegency()
        listenerDistricts()

        setLoadingButton(R.id.include_btn_add_company, R.string.save) {
            val request = CompanyEditRequest(
                companyName = etCompanyName.text.toString().trim(),
                companyAddress = etAddress.text.toString().trim(),
                districtId = responseDistrict?.districtId,
                companyPhone = PhoneUtil.areaAdd(etPhone.text.toString().trim()),
                companyFax = PhoneUtil.areaAdd(etFax.text.toString().trim()),
                companyId = companyResponse?.companyId
            )

            viewModelCompany.postCompanyEdit(request)
        }

        showcase()
    }

    private fun showcase() {
        showcaseToolbar(
            PrefsUtil.SHOWCASE_EDIT_COMPANY,
            R.id.company_edit_list_cp,
            R.drawable.ic_add_toolbar,
            getString(R.string.showcase_contact),
            getString(R.string.showcase_contact_create)
        ) {}
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.title = resources.getString(R.string.client_edit)
        }

        setBackButton(toolbar) {
            finish()
        }
    }

    private fun initData() {
        responseProvinces = ProvincesResponse(companyResponse?.provinceId, companyResponse?.provinceName)

        responseRegency = RegencyResponse(
            companyResponse?.provinceId,
            companyResponse?.provinceName,
            companyResponse?.regencyId,
            companyResponse?.regencyName
        )

        responseDistrict = DistrictResponse(
            companyResponse?.districtId,
            companyResponse?.districtName,
            companyResponse?.provinceId,
            companyResponse?.provinceName,
            companyResponse?.regencyId,
            companyResponse?.regencyName
        )

        if (responseRegency?.regencyId ?: 0 > 0) {
            isSubmitProvinces = true
            isSubmitRegency = true
        }
    }

    private fun initForm() {
        etCompanyName.setText(companyResponse?.companyName)
        etAddress.setText(companyResponse?.companyAddress)
        etPhone.setText(PhoneUtil.areaRemove(companyResponse?.companyPhone))
        etFax.setText(PhoneUtil.areaRemove(companyResponse?.companyFax))
    }

    private fun listenerDistricts() {
        etDistricts.setOnClickListener(this)
    }

    private fun listenerProvinces() {
        etProvinces.setOnClickListener(this)
        etProvinces.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                selectedProvinces()
            }
        })
    }

    private fun selectedProvinces() {
        etRegencies.setText("")
        etRegencies.isEnabled = true

        etDistricts.setText("")
        etDistricts.isEnabled = false

        responseProvinces?.provinceId?.let { viewModelRegencies.getRegencies(it) }
    }

    private fun listenerRegency() {
        etRegencies.setOnClickListener(this)
        etRegencies.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                selectedRegency()
            }
        })
    }

    private fun selectedRegency() {
        etDistricts.setText("")
        etDistricts.isEnabled = true
        responseRegency?.regencyId?.let { viewModelDistricts.getDistrict(it) }
    }

    private fun initView() {
        etProvinces.asButton()
        etDistricts.asButton()
        etRegencies.asButton()
    }

    override fun onClick(v: View?) {
        when (v) {
            etProvinces -> {
                val list = listProvinces?.map { it.provinceName ?: "" }?.toCollection(ArrayList())
                if (dialogProvinces == null) {
                    dialogProvinces = getDialogChoice(list ?: arrayListOf()) {
                        responseProvinces = listProvinces?.find { response -> response.provinceName == it }
                        etProvinces.setText(it)
                    }
                }

                if (dialogProvinces?.isShowing == true) dialogProvinces?.dismiss()
                dialogProvinces?.show()
            }

            etDistricts -> {
                val list = listDistricts?.map { it.districtName ?: "" }?.toCollection(ArrayList())
                dialogDistricts = getDialogChoice(list ?: arrayListOf()) {
                    responseDistrict = listDistricts?.find { response -> response.districtName == it }
                    etDistricts.setText(it)
                }

                if (dialogDistricts?.isShowing == true) dialogDistricts?.dismiss()
                dialogDistricts?.show()
            }

            etRegencies -> {
                val list = listRegencies?.map { it.regencyName ?: "" }?.toCollection(ArrayList())
                dialogRegencies = getDialogChoice(list ?: arrayListOf()) {
                    responseRegency = listRegencies?.find { response -> response.regencyName == it }
                    etRegencies.setText(it)
                }

                if (dialogRegencies?.isShowing == true) dialogRegencies?.dismiss()
                dialogRegencies?.show()
            }
        }
    }

    private fun observe() {
        viewModelCompany.onLoading().observe(this, BaseObserver {
            isLoading(it)
        })

        viewModelCompany.onError().observe(this, BaseObserver {
            it.toast(this)
        })

        viewModelCompany.onSuccess().observe(this, BaseObserver {
            it.message?.toast(this)
            finish()
        })
    }

    private fun observeDistrics() {
        viewModelDistricts.onLoading().observe(this, BaseObserver {
            vfDistricts.displayedChild = if (it) displayLoading else displayForm
        })

        viewModelDistricts.onError().observe(this, BaseObserver {

        })

        viewModelDistricts.onSuccess().observe(this, BaseObserver {
            listDistricts = ArrayList(it.result?.rows)
        })
    }

    private fun observeRegencies() {
        viewModelRegencies.onLoading().observe(this, BaseObserver {
            vfRegency.displayedChild = if (it) displayLoading else displayForm
        })

        viewModelRegencies.onError().observe(this, BaseObserver {

        })

        viewModelRegencies.onSuccess().observe(this, BaseObserver {
            listRegencies = ArrayList(it.result?.rows)
            if (isSubmitRegency) {
                isSubmitRegency = false
                etRegencies.setText(responseRegency?.regencyName)
                etDistricts.setText(responseDistrict?.districtName)
            }
        })
    }

    private fun observeProvinces() {
        viewModelProvinces.getProvinces()
        viewModelProvinces.onLoading().observe(this, BaseObserver {
            vfProvinces.displayedChild = if (it) displayLoading else displayForm
        })

        viewModelProvinces.onError().observe(this, BaseObserver {

        })

        viewModelProvinces.onSuccess().observe(this, BaseObserver {
            listProvinces = ArrayList(it.result?.rows)
            if (isSubmitProvinces) {
                isSubmitProvinces = false
                etProvinces.setText(responseProvinces?.provinceName)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.company_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.company_edit_list_cp -> {
                companyResponse?.let { ContactPersonListActivity.start(this, it) }
                    ?: getString(R.string.company_id_null).toast(this)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}
