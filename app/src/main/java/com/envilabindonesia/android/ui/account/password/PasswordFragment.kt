package com.envilabindonesia.android.ui.account.password

import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseFragment
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.data.request.ChangePasswordRequest
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.getViewModel
import com.envilabindonesia.android.extension.toast
import com.envilabindonesia.android.ui.account.AccountFragment
import kotlinx.android.synthetic.main.fragment_password.*
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-04-05.
 */

class PasswordFragment: BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy { getViewModel<PasswordViewModel>(viewModelFactory) }

    override fun getLayoutRes(): Int = R.layout.fragment_password

    override fun init() {
        (parentFragment?.parentFragment as AccountFragment).setCheckedChip(getString(R.string.account_change_password))
        observe()
        setLoadingButton(R.id.include_button_password, R.string.save) {
            viewModel.postChangePassword(
                ChangePasswordRequest(
                    oldPassword = etPasswordOld.text.toString().trim(),
                    newPassword = etPasswordNew.text.toString().trim(),
                    retypePassword = etPasswordConfirm.text.toString().trim()
                )
            )
        }
    }

    private fun observe() {
        viewModel.onSuccess().observe(this, BaseObserver {
            etPasswordOld.setText("")
            etPasswordNew.setText("")
            etPasswordConfirm.setText("")
            etPasswordOld.requestFocus()
            activity?.let { it1 -> it.message?.toast(it1) }
        })

        viewModel.onLoading().observe(this, BaseObserver {
            isLoading(it)
        })

        viewModel.onError().observe(this, BaseObserver {
            activity?.let { it1 -> it.toast(it1) }
        })
    }

}