package com.envilabindonesia.android.ui.client.add

import android.app.Activity
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
import com.envilabindonesia.android.data.request.CompanyAddRequest
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
import com.envilabindonesia.android.util.PhoneUtil
import kotlinx.android.synthetic.main.activity_client_add.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import javax.inject.Inject

class ClientAddActivity : BaseActivity(), View.OnClickListener {

    companion object {
        const val BUNDLE = "BUNDLE"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModelCompany by lazy { getViewModel<ClientAddViewModel>(viewModelFactory) }
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

    override fun getLayoutRes(): Int = R.layout.activity_client_add

    override fun init() {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.title = resources.getString(R.string.create_new_client)
        }

        observe()
        observeProvinces()
        observeRegencies()
        observeDistrics()

        initView()
        listenerProvinces()
        listenerRegency()
        listenerDistricts()

        setLoadingButton(R.id.include_btn_add_company, R.string.save) {
            val request = CompanyAddRequest(
                companyName = etCompanyName.text.toString().trim(),
                companyAddress = etAddress.text.toString().trim(),
                districtId = responseDistrict?.districtId,
                companyPhone = PhoneUtil.areaAdd(etPhone.text.toString().trim()),
                companyFax = PhoneUtil.areaAdd(etFax.text.toString().trim())
            )

            viewModelCompany.postCompanyAdd(request)
        }
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
            setResult(Activity.RESULT_OK, Intent().apply { putExtra(BUNDLE, it.result) })
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
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.close, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_close -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}
