package com.envilabindonesia.android.ui.verification

import android.app.Activity
import android.content.Intent
import android.os.CountDownTimer
import android.view.View
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseActivity
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.data.response.RegisterResponse
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.getViewModel
import com.envilabindonesia.android.extension.toast
import com.envilabindonesia.android.util.TimeUtil
import com.poovam.pinedittextfield.PinField
import kotlinx.android.synthetic.main.activity_verification.*
import javax.inject.Inject

class VerificationActivity : BaseActivity(), View.OnClickListener {

    private var registerResponse: RegisterResponse? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy { getViewModel<VerificationViewModel>(viewModelFactory) }

    override fun getLayoutRes(): Int = R.layout.activity_verification

    override fun init() {
        startTimer()
        bundle()
        onClickListener()
        observerSubmit()
        observerResend()
    }

    private fun bundle() {
        registerResponse = intent.getParcelableExtra(BUNDLE)
        tvEmail.text = registerResponse?.email ?: ""
    }

    private fun onClickListener() {
        btnClose.setOnClickListener(this)
        btnResend.setOnClickListener(this)
        etVerifyCode.onTextCompleteListener = object : PinField.OnTextCompleteListener {
            override fun onTextComplete(enteredText: String): Boolean {
                viewModel.getValidateCode(registerResponse?.email ?: "", enteredText)
                return false // Return true to keep the keyboard open else return false to close the keyboard
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            btnClose -> finish()
            btnResend -> viewModel.getResendCode(registerResponse?.email ?: return)
        }
    }

    private fun observerSubmit() {
        viewModel.onSuccess().observe(this, BaseObserver {
            it.message?.toast(this)
            setResult(Activity.RESULT_OK)
            finish()
        })

        viewModel.onLoading().observe(this, BaseObserver {
            loadSubmit.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.onError().observe(this, BaseObserver {
            it.toast(this)
        })
    }

    private fun observerResend() {
        viewModel.onSuccessResend().observe(this, BaseObserver {
            it.message?.toast(this)
            startTimer()
            vfResend.displayedChild = 0
        })

        viewModel.onLoadingResend().observe(this, BaseObserver {
            vfResend.displayedChild = if (it) 2 else 1
        })

        viewModel.onErrorResend().observe(this, BaseObserver {
            it.toast(this)
        })
    }

    private fun startTimer() {
        object : CountDownTimer(60000, 1000) {
            override fun onFinish() {
                vfResend?.let {
                    it.displayedChild = VIEW_RESEND
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                tvTimer?.let {
                    it.text = TimeUtil.convertMinuteSeconds(millisUntilFinished)
                }
            }

        }.start()
    }

    companion object {
        const val BUNDLE = "bundle"
        const val VIEW_RESEND = 1

        fun startResult(activity: BaseActivity, registerResponse: RegisterResponse, requestCode: Int) {
            val intent = Intent(activity.applicationContext, VerificationActivity::class.java)
            intent.putExtra(BUNDLE, registerResponse)
            activity.startActivityForResult(intent, requestCode)
        }
    }

}