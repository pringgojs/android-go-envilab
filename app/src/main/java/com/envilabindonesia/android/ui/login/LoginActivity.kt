package com.envilabindonesia.android.ui.login

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseActivity
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.data.request.LoginRequest
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.getViewModel
import com.envilabindonesia.android.extension.toast
import com.envilabindonesia.android.ui.forgot.ForgotPasswordActivity
import com.envilabindonesia.android.ui.home.HomeActivity
import com.envilabindonesia.android.ui.register.RegisterActivity
import com.envilabindonesia.android.util.PrefsUtil
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity(), View.OnClickListener {

    override fun getLayoutRes(): Int = R.layout.activity_login

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy { getViewModel<LoginViewModel>(viewModelFactory) }

    override fun onClick(v: View?) {
        when (v) {
            btnRegCompany -> RegisterActivity.start(this, RegisterActivity.ROLE_COMPANY)
            btnRegPartner -> RegisterActivity.start(this, RegisterActivity.ROLE_MITRA)
            btnForgotPwd -> startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }

    override fun init() {
        onClickListener()
        initButtonLogin()
        observer()
    }

    private fun observer() {
        viewModel.onSuccess().observe(this, BaseObserver {
            PrefsUtil.setLoginResponse(it.result)
            startActivity(Intent(this, HomeActivity::class.java))
            finishAffinity()
        })

        viewModel.onError().observe(this, BaseObserver {
            it.toast(this)
        })

        viewModel.onLoading().observe(this, Observer {
            isLoading(it)
        })
    }

    private fun initButtonLogin() {
        setLoadingButton(R.id.include_button, R.string.signin) {
            viewModel.postLogin(LoginRequest(
                email = etEmail.text.toString().trim(),
                password = etPassword.text.toString().trim()
            ))
        }
    }

    private fun onClickListener() {
        btnRegCompany.setOnClickListener(this)
        btnRegPartner.setOnClickListener(this)
        btnForgotPwd.setOnClickListener(this)
    }

}