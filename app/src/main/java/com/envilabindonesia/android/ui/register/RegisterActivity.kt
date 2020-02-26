package com.envilabindonesia.android.ui.register

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseActivity
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.data.Constant
import com.envilabindonesia.android.data.request.RegisterRequest
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.getViewModel
import com.envilabindonesia.android.extension.toast
import com.envilabindonesia.android.ui.verification.VerificationActivity
import com.envilabindonesia.android.util.PhoneUtil
import kotlinx.android.synthetic.main.activity_register.*
import javax.inject.Inject

class RegisterActivity : BaseActivity(), View.OnClickListener {

    private var role: Int? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy { getViewModel<RegisterViewModel>(viewModelFactory) }

    override fun getLayoutRes(): Int = R.layout.activity_register

    override fun init() {
        defaultView()
        onClickListener()
        binding()
        observe()
    }

    private fun binding() {

    }

    private fun observe() {
        viewModel.onLoading().observe(this, BaseObserver {
            if (it)
                vfButton.displayedChild = DISPLAY_LOADING
            else
                defaultView()
        })

        viewModel.onSuccess().observe(this, BaseObserver {
            it.result?.let { it1 -> VerificationActivity.startResult(this, it1, Constant.ActivityResult.RC_VERIFY) }
        })

        viewModel.onError().observe(this, BaseObserver {
            it.toast(this)
        })
    }

    private fun onClickListener() {
        btnClose.setOnClickListener(this)
        btnSubmitPartner.setOnClickListener(this)
        btnSubmitCompany.setOnClickListener(this)
    }

    private fun defaultView() {
        role = intent.getIntExtra(BUNDLE, 0)
        if (role == ROLE_MITRA) {
            vfButton.displayedChild = DISPLAY_MITRA
            vfSubtitle.displayedChild = DISPLAY_MITRA
        } else {
            vfButton.displayedChild = DISPLAY_COMPANY
            vfSubtitle.displayedChild = DISPLAY_COMPANY
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            btnClose -> finish()
            btnSubmitPartner, btnSubmitCompany -> viewModel.doRegister(
                RegisterRequest(
                    fullName = etFullName.text.toString().trim(),
                    email = etEmail.text.toString().trim(),
                    password = etPassword.text.toString().trim(),
                    phone = PhoneUtil.areaAdd(etMobilePhone.text.toString().trim()),
                    registeredAs = role
                )
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == Constant.ActivityResult.RC_VERIFY) {
            finish()
        }
    }

    companion object {
        const val BUNDLE = "bundle"

        const val DISPLAY_LOADING = 0
        const val DISPLAY_MITRA = 1
        const val DISPLAY_COMPANY = 2

        const val ROLE_MITRA = 1
        const val ROLE_COMPANY = 2
        const val ROLE_SALES = 3

        fun start(context: Context, role: Int) {
            val intent = Intent(context, RegisterActivity::class.java)
            intent.putExtra(BUNDLE, role)
            context.startActivity(intent)
        }
    }
}