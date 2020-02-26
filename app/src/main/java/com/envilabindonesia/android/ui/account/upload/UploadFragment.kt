package com.envilabindonesia.android.ui.account.upload

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.CountDownTimer
import android.view.View
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseFragment
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.data.Constant
import com.envilabindonesia.android.data.request.ChangeProfileRequest
import com.envilabindonesia.android.data.request.ItemAttachment
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.getViewModel
import com.envilabindonesia.android.extension.loadRounded
import com.envilabindonesia.android.extension.toast
import com.envilabindonesia.android.ui.account.AccountFragment
import com.envilabindonesia.android.ui.account.profile.ProfileViewModel
import com.envilabindonesia.android.util.DialogUtil
import com.envilabindonesia.android.util.PrefsUtil
import kotlinx.android.synthetic.main.fragment_upload.*
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-04-05.
 */

class UploadFragment: BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModelUpload by lazy { getViewModel<UploadViewModel>(viewModelFactory) }
    private val viewModelProfile by lazy { getViewModel<ProfileViewModel>(viewModelFactory) }

    private var doc1: String? = null
    private var doc2: String? = null

    private var isDoc1: Boolean = true

    override fun getLayoutRes(): Int = R.layout.fragment_upload

    override fun init() {
        (parentFragment?.parentFragment as AccountFragment).setCheckedChip(getString(R.string.account_upload))
        if (Constant.isLoginAsMitraOrSales()) {
            tilKtp.visibility = View.VISIBLE
            tvDoc2.text = getString(R.string.ktp)
        } else {
            tilKtp.visibility = View.INVISIBLE
            tvDoc2.text = getString(R.string.skkpk)
        }

        observeUpload()
        observeProfile()

        setLoadingButton(R.id.include_button_upload, R.string.save) {
            val request = ChangeProfileRequest(
                noKTP = etKtp.text.toString().trim(),
                noNPWP = etNpwp.text.toString().trim(),
                address = loginResponse?.address,
                email = loginResponse?.email,
                phone = loginResponse?.phone,
                districtId = loginResponse?.districtId.toString(),
                docUrl1 = doc1 ?: loginResponse?.docUrl1, // NPWP
                docUrl2 = doc2 ?: loginResponse?.docUrl2, // KTP - SPPKP
                fullName = loginResponse?.fullName,
                profileImage = loginResponse?.profileImage
            )

            viewModelProfile.postChangeProfile(request)
        }

        ivDoc1.loadRounded(R.drawable.ic_upload)
        ivDoc1.setOnClickListener {
            dialogPickerNpwp?.let { dialog ->
                if (dialog.isShowing) dialog.dismiss()
                dialog.show()
            }
        }

        ivDoc2.loadRounded(R.drawable.ic_upload)
        ivDoc2.setOnClickListener {
            dialogPickerSkkpk?.let { dialog ->
                if (dialog.isShowing) dialog.dismiss()
                dialog.show()
            }
        }


        initData()
        swipe.setOnRefreshListener {
            initData()
            object : CountDownTimer(3000, 1000) {
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

    private fun initData() {
        val data = PrefsUtil.getLoginResponse() ?: return
        if (Constant.isLoginAsMitraOrSales()) etKtp.setText(data.noKTP)

        etNpwp.setText(loginResponse?.noNPWP)

        if (data.docUrl1?.isNotEmpty() == true) ivDoc1.loadRounded(data.docUrl1)
        if (data.docUrl2?.isNotEmpty() == true) ivDoc2.loadRounded(data.docUrl2)
    }

    private fun observeProfile() {
        viewModelProfile.onLoading().observe(this, BaseObserver{
            isLoading(it)
        })

        viewModelProfile.onError().observe(this, BaseObserver{
            activity?.let { it1 -> it?.toast(it1) }
        })

        viewModelProfile.onSuccess().observe(this, BaseObserver{
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
            if (isDoc1) doc1 = it.result?.fileUrl else doc2 = it.result?.fileUrl
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                Constant.ActivityResult.RC_CAMERA_NPWP -> {
                    ivDoc1?.let { view ->
                        if (validFileSize(data)) {
                            val bitmap = data.extras?.get("data") as Bitmap
                            val multipart = getMultipartBody(ItemAttachment(bitmap = bitmap))
                            isDoc1 = true
                            viewModelUpload.postUpload(multipart)
                            view.loadRounded(bitmap)
                        }
                    }
                }

                Constant.ActivityResult.RC_GALLERY_NPWP -> {
                    ivDoc1?.let { view ->
                        if (validFileSize(data)) {
                            val uri = data.data ?: return
                            val multipart = getMultipartBody(ItemAttachment(uri = uri))
                            isDoc1 = true
                            viewModelUpload.postUpload(multipart)
                            view.loadRounded(uri)
                        }
                    }
                }

                Constant.ActivityResult.RC_CAMERA_SKKPK -> {
                    ivDoc2?.let { view ->
                        if (validFileSize(data)) {
                            val bitmap = data.extras?.get("data") as Bitmap
                            val multipart = getMultipartBody(ItemAttachment(bitmap = bitmap))
                            isDoc1 = false
                            viewModelUpload.postUpload(multipart)
                            view.loadRounded(bitmap)
                        }
                    }
                }

                Constant.ActivityResult.RC_GALLERY_SKKPK -> {
                    ivDoc2?.let { view ->
                        if (validFileSize(data)) {
                            val uri = data.data ?: return
                            val multipart = getMultipartBody(ItemAttachment(uri = uri))
                            isDoc1 = false
                            viewModelUpload.postUpload(multipart)
                            view.loadRounded(uri)
                        }
                    }
                }
            }
        }
    }

    private val dialogPickerNpwp by lazy {
        activity?.let { act ->
            DialogUtil.chooser(
                act,
                arrayOf( getString(R.string.take_camera), getString(R.string.take_gallery) )) {
                when (it) {
                    getString(R.string.take_camera) -> takePhoto(Constant.ActivityResult.RC_CAMERA_NPWP)
                    getString(R.string.take_gallery) -> takeGallery(Constant.ActivityResult.RC_GALLERY_NPWP)
                }
            }
        }
    }

    private val dialogPickerSkkpk by lazy {
        activity?.let { act ->
            DialogUtil.chooser(
                act,
                arrayOf( getString(R.string.take_camera), getString(R.string.take_gallery) )) {
                when (it) {
                    getString(R.string.take_camera) -> takePhoto(Constant.ActivityResult.RC_CAMERA_SKKPK)
                    getString(R.string.take_gallery) -> takeGallery(Constant.ActivityResult.RC_GALLERY_SKKPK)
                }
            }
        }
    }
    
}