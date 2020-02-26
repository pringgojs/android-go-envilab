package com.envilabindonesia.android.ui.account

import androidx.core.view.forEach
import androidx.navigation.Navigation
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseFragment
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_account.*

/**
 * Created by galihadityo on 2019-04-05.
 */

class AccountFragment: BaseFragment() {

    private val navController by lazy {
        activity?.let { Navigation.findNavController(it, R.id.navHostAccount) }
    }

    override fun getLayoutRes(): Int = R.layout.fragment_account

    override fun init() {
        baseActivity?.supportActionBar?.title = resources.getString(R.string.menu_account)
    }

    private fun setCheckedListener() {
        chipGroup.setOnCheckedChangeListener { _, i ->
            when (i) {
                chipProfile.id -> navController?.navigate(R.id.profileFragment)
                chipPassword.id -> navController?.navigate(R.id.passwordFragment)
                chipUpload.id -> navController?.navigate(R.id.uploadFragment)
            }
        }
    }

    fun setCheckedChip(chipText: String) {
        chipGroup.forEach {
            val title = (it as Chip).text.toString()
            it.isChecked = chipText.equals(title, true)
        }
        setCheckedListener()
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setHasOptionsMenu(true)
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.account, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.account_logout -> {
//                (activity as HomeActivity).logout()
//                true
//            }
//
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

//    val dialogProfileImage by lazy {
//        activity?.let { act ->
//            DialogUtil.chooser(
//                act,
//                arrayOf( getString(R.string.take_camera), getString(R.string.take_gallery) )) {
//                when (it) {
//                    getString(R.string.take_camera) -> {
//                        Intent().apply {
//                            action = MediaStore.ACTION_IMAGE_CAPTURE
//                            this.resolveActivity(act.packageManager)
//                            startActivityForResult(this, HomeActivity.RC_CAMERA)
//                        }
//                    }
//
//                    getString(R.string.take_gallery) -> {
//                        Intent().apply {
//                            action = Intent.ACTION_PICK
//                            type = "image/*"
//                            startActivityForResult(this, HomeActivity.RC_GALLERY)
//                        }
//                    }
//                }
//            }
//        }
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK) {
//            when (requestCode) {
//                HomeActivity.RC_CAMERA_NPWP -> {
//                    ivNpwp?.let {
//                            view -> data?.let {
//                            intent -> view.loadRounded(intent.extras?.get("data") as Bitmap)
//                    }}
//                }
//
//                HomeActivity.RC_GALLERY_NPWP -> {
//                    ivNpwp?.let {
//                            view -> data?.let { view.loadRounded(data.data ?: return) }
//                    }
//                }
//
//                HomeActivity.RC_CAMERA_SKKPK -> {
//                    ivSppkp?.let {
//                            view -> data?.let {
//                            intent -> view.loadRounded(intent.extras?.get("data") as Bitmap)
//                    }}
//                }
//
//                HomeActivity.RC_GALLERY_SKKPK -> {
//                    ivSppkp?.let {
//                            view -> data?.let { view.loadRounded(data.data ?: return) }
//                    }
//                }
//
//                HomeActivity.RC_CAMERA -> {
//                    val i = data
//                    RxBus.post(
//                        RxEvent.ImageBitmap(
//                            BitmapUtil.getBitmap(
//                                activity?.contentResolver ?: return,
//                                data?.data ?: return)
//                        )
//                    )
////                    ivProfile?.let {
////                            view -> data?.let {
////                            intent -> {
////                        view.loadCircle(intent.extras?.get("data") as Bitmap)
//////                        viewModel.postUpload(getMultipartBody(ItemAttachment(bitmap = intent.extras?.get("data") as Bitmap)))
////                    }
////                    }
////                    }
//                }
//
//                HomeActivity.RC_GALLERY -> {
//                    ivProfile?.let {
//                            view -> data?.let {
//                        val uri = data.data ?: return
//                        view.loadCircle(uri)
////                        viewModel.postUpload(getMultipartBody(ItemAttachment(uri = uri)))
//                    }
//                    }
//                }
//            }
//        }
//    }

}