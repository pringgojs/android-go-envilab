package com.envilabindonesia.android.ui.forgot

import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseActivity
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.data.request.ForgotPasswordRequest
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.getViewModel
import com.envilabindonesia.android.extension.toast
import kotlinx.android.synthetic.main.activity_forgot_password.*
import javax.inject.Inject

class ForgotPasswordActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy { getViewModel<ForgotPasswordViewModel>(viewModelFactory) }

    override fun getLayoutRes(): Int = R.layout.activity_forgot_password

    override fun init() {
        observer()
        btnClose.setOnClickListener { finish() }
        setLoadingButton(R.id.include_button_forgot, R.string.button_forgot_password) {
            if (etEmail.text.toString().trim().isEmpty()) {
                getString(R.string.email_mandatory).toast(this)
                return@setLoadingButton
            }

            viewModel.postForgotPassword(ForgotPasswordRequest(etEmail.text.toString().trim()))
        }
    }

    private fun observer() {
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

}
