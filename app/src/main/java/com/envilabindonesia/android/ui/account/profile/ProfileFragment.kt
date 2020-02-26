package com.envilabindonesia.android.ui.account.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseFragment
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.data.Constant
import com.envilabindonesia.android.data.request.ChangeProfileRequest
import com.envilabindonesia.android.data.request.ItemAttachment
import com.envilabindonesia.android.data.response.CompanyResponse
import com.envilabindonesia.android.data.response.DistrictResponse
import com.envilabindonesia.android.data.response.ProvincesResponse
import com.envilabindonesia.android.data.response.RegencyResponse
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.asButton
import com.envilabindonesia.android.extension.getViewModel
import com.envilabindonesia.android.extension.loadCircle
import com.envilabindonesia.android.extension.toast
import com.envilabindonesia.android.ui.account.AccountFragment
import com.envilabindonesia.android.ui.account.DistrictsViewModel
import com.envilabindonesia.android.ui.account.ProvincesViewModel
import com.envilabindonesia.android.ui.account.RegenciesViewModel
import com.envilabindonesia.android.ui.account.upload.UploadViewModel
import com.envilabindonesia.android.ui.client.add.ClientAddActivity
import com.envilabindonesia.android.ui.client.search.SearchClientActivity
import com.envilabindonesia.android.ui.register.RegisterActivity
import com.envilabindonesia.android.util.DialogUtil
import com.envilabindonesia.android.util.PhoneUtil
import com.envilabindonesia.android.util.PrefsUtil
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-04-05.
 */

class ProfileFragment: BaseFragment(), View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModelProfile by lazy { getViewModel<ProfileViewModel>(viewModelFactory) }
    private val viewModelUpload by lazy { getViewModel<UploadViewModel>(viewModelFactory) }
    private val viewModelProvinces by lazy { getViewModel<ProvincesViewModel>(viewModelFactory) }
    private val viewModelRegencies by lazy { getViewModel<RegenciesViewModel>(viewModelFactory) }
    private val viewModelDistricts by lazy { getViewModel<DistrictsViewModel>(viewModelFactory) }

    private var profileImage: String? = null

    private var responseProvinces: ProvincesResponse? = null
    private var responseRegency: RegencyResponse? = null
    private var responseDistrict: DistrictResponse? = null
    private var companyResponse: CompanyResponse? = null

    private var listProvinces: ArrayList<ProvincesResponse>? = null
    private var listRegencies: ArrayList<RegencyResponse>? = null
    private var listDistricts: ArrayList<DistrictResponse>? = null

    private var dialogProvinces: AlertDialog? = null
    private var dialogRegencies: AlertDialog? = null
    private var dialogDistricts: AlertDialog? = null

    private val displayForm = 0
    private val displayLoading = 1

    private val loginAs by lazy { PrefsUtil.getLoginResponse()?.registeredAs }
    private val mParentFragment by lazy { (parentFragment?.parentFragment as AccountFragment) }

    override fun getLayoutRes(): Int = R.layout.fragment_profile

    private var isSubmitProvinces: Boolean = false
    private var isSubmitRegency: Boolean = false

    override fun init() {
        mParentFragment.setCheckedChip(getString(R.string.account_profile))

        observe()
        observeUpload()
        observeProvinces()
        observeRegencies()
        observeDistrics()

        initData()
        initView()
        initForm()

        listenerProvinces()
        listenerRegency()
        listenerDistricts()
        listenerCompany()

        ivProfile.setOnClickListener {
            dialogImagePicker?.let { dialog ->
                if (dialog.isShowing) dialog.dismiss()
                dialog.show()
            }
        }

        setLoadingButton(R.id.include_button_profile, R.string.save) {
            val request = ChangeProfileRequest(
                address = etAddress.text.toString().trim(),
                email = etEmail.text.toString().trim(),
                phone = PhoneUtil.areaAdd(etMobilePhone.text.toString().trim()),
                districtId = responseDistrict?.districtId.toString(),
                docUrl1 = loginResponse?.docUrl1, // NPWP
                docUrl2 = loginResponse?.docUrl2, // KTP - SPPKP
                fullName = loginResponse?.fullName,
                profileImage = profileImage ?: loginResponse?.profileImage,
                noKTP = loginResponse?.noKTP,
                noNPWP = loginResponse?.noNPWP
            )

            companyResponse?.let { request.companyId = it.companyId }

            viewModelProfile.postChangeProfile(request)
        }

        swipe.setOnRefreshListener {
            init()
            object : CountDownTimer(1000, 1000) {
                override fun onFinish() {
                    swipe?.let {
                        it.isRefreshing = false
                    }
                }

                override fun onTick(millisUntilFinished: Long) {
                }

            }.start()
        }
    }

    private fun listenerCompany() {
        etCompanyName.setOnClickListener(this)
        btnAddCompany.setOnClickListener(this)
    }

    private fun initData() {
        responseProvinces = ProvincesResponse(loginResponse?.provinceId, loginResponse?.provinceName)

        responseRegency = RegencyResponse(
            loginResponse?.provinceId,
            loginResponse?.provinceName,
            loginResponse?.regencyId,
            loginResponse?.regencyName
        )

        responseDistrict = DistrictResponse(
            loginResponse?.districtId,
            loginResponse?.districtName,
            loginResponse?.provinceId,
            loginResponse?.provinceName,
            loginResponse?.regencyId,
            loginResponse?.regencyName
        )

        if (responseRegency?.regencyId ?: 0 > 0) {
            isSubmitProvinces = true
            isSubmitRegency = true
        }
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

            etCompanyName -> {
                startActivityForResult(
                    Intent(activity, SearchClientActivity::class.java),
                    Constant.ActivityResult.RC_SEARCH_CLIENT)
            }

            btnAddCompany -> {
                startActivityForResult(Intent(activity, ClientAddActivity::class.java),
                    Constant.ActivityResult.RC_CLIENT_ADD)
            }
        }
    }

    private fun listenerDistricts() {
        etDistricts.setOnClickListener(this)
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

    private fun initView() {
        etProvinces.asButton()
        etDistricts.asButton()
        etRegencies.asButton()
        etCompanyName.asButton()

        if (loginAs == RegisterActivity.ROLE_COMPANY) {
            layoutCompany.visibility = View.VISIBLE
        } else {
            layoutCompany.visibility = View.GONE
        }
    }

    private fun selectedProvinces() {
        etRegencies.setText("")
        etRegencies.isEnabled = true

        etDistricts.setText("")
        etDistricts.isEnabled = false

        responseProvinces?.provinceId?.let { viewModelRegencies.getRegencies(it) }
    }

    private fun selectedRegency() {
        etDistricts.setText("")
        etDistricts.isEnabled = true
        responseRegency?.regencyId?.let { viewModelDistricts.getDistrict(it) }
    }

    private fun initForm() {
        val loginResponse = PrefsUtil.getLoginResponse() ?: return
        tvName.text = loginResponse.fullName
        etEmail.setText(loginResponse.email)
        etMobilePhone.setText(PhoneUtil.areaRemove(loginResponse.phone))
        etAddress.setText(loginResponse.address)

        if (loginResponse.profileImage?.isNotEmpty() == true) ivProfile.loadCircle(loginResponse.profileImage)
        if (loginResponse.companyId ?: 0 > 0) {
            companyResponse = CompanyResponse(
                companyId = loginResponse.companyId,
                companyName = loginResponse.companyName
            )
            etCompanyName.setText(loginResponse.companyName ?: "")
        }
    }

    private fun observe() {
        viewModelProfile.onLoading().observe(this, BaseObserver {
            isLoading(it)
        })

        viewModelProfile.onError().observe(this, BaseObserver {
            activity?.let { it1 -> it.toast(it1) }
        })

        viewModelProfile.onSuccess().observe(this, BaseObserver {
            activity?.let { it1 -> it.message?.toast(it1) }
            PrefsUtil.setLoginResponse(it.result)
        })
    }

    private fun observeUpload() {
        viewModelUpload.onLoading().observe(this, BaseObserver {
            progressDialog(it)
        })

        viewModelUpload.onError().observe(this, BaseObserver {
            activity?.let { it1 -> it.toast(it1) }
        })

        viewModelUpload.onSuccess().observe(this, BaseObserver {
            profileImage = it.result?.fileUrl
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                Constant.ActivityResult.RC_CAMERA -> {
                    ivProfile?.let { view ->
                        if (validFileSize(data)) {
                            val bitmap = data.extras?.get("data") as Bitmap
                            val multipart = getMultipartBody(ItemAttachment(bitmap = bitmap))
                            viewModelUpload.postUpload(multipart)
                            view.loadCircle(bitmap)
                        }
                    }
                }

                Constant.ActivityResult.RC_GALLERY -> {
                    ivProfile?.let { view ->
                        if (validFileSize(data)) {
                            data.data?.let { uri ->
                                val multipart = getMultipartBody(ItemAttachment(uri = uri))
                                viewModelUpload.postUpload(multipart)
                                view.loadCircle(uri)
                            }
                        }
                    }
                }

                Constant.ActivityResult.RC_SEARCH_CLIENT -> {
                    companyResponse = data.getParcelableExtra(SearchClientActivity.BUNDLE)
                    etCompanyName.setText(companyResponse?.companyName ?: return)
                }

                Constant.ActivityResult.RC_CLIENT_ADD -> {
                    companyResponse = data.getParcelableExtra(ClientAddActivity.BUNDLE)
                    etCompanyName.setText(companyResponse?.companyName ?: return)
                }
            }
        }
    }

    private val dialogImagePicker by lazy {
        activity?.let { act ->
            DialogUtil.chooser(
                act,
                arrayOf( getString(R.string.take_camera), getString(R.string.take_gallery) )) {
                when (it) {
                    getString(R.string.take_camera) -> takePhoto(Constant.ActivityResult.RC_CAMERA)
                    getString(R.string.take_gallery) -> takeGallery(Constant.ActivityResult.RC_GALLERY)
                }
            }
        }
    }


}