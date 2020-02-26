package com.envilabindonesia.android.base

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ViewFlipper
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import com.envilabindonesia.android.R
import com.envilabindonesia.android.data.request.ItemAttachment
import com.envilabindonesia.android.extension.toast
import com.envilabindonesia.android.ui.home.HomeActivity
import com.envilabindonesia.android.util.BitmapUtil
import com.envilabindonesia.android.util.MultipartHelper
import com.envilabindonesia.android.util.PrefsUtil
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import dagger.android.support.DaggerFragment
import okhttp3.MultipartBody
import java.io.File

/**
 * Created by galihadityo on 2019-03-02.
 */

abstract class BaseFragment: DaggerFragment() {

    val loginResponse by lazy { PrefsUtil.getLoginResponse() }
    var baseActivity: BaseActivity? = null

    private var loadingFlipper: ViewFlipper? = null
    private var button: AppCompatButton? = null
    private var progressDialog: AlertDialog? = null

    @LayoutRes
    abstract fun getLayoutRes(): Int

    fun getHomeActivity(): HomeActivity = baseActivity as HomeActivity

    abstract fun init()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(getLayoutRes(), container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActivity = context as BaseActivity
    }

    fun setLoadingButton(@IdRes idIncludeLayout: Int, @StringRes idText: Int, onclick:() -> Unit) {
        val v = activity?.findViewById<View>(idIncludeLayout) ?: return
        loadingFlipper = v.findViewById(R.id.vf)    // viewflipper `id` mandatory `R.id.vf`
        button = v.findViewById(R.id.button)        // button `id` mandatory `R.id.button`
        button?.let {
            it.text = getString(idText)
            it.setOnClickListener {
                onclick()
            }
        }
    }

    fun isLoading(load: Boolean) {
        loadingFlipper?.let {
            it.displayedChild = if (load) 1 else 0
        }
    }

    fun getMultipartBody(itemAttachment: ItemAttachment): MultipartBody.Part =
        MultipartHelper.createPartFromData(activity, itemAttachment)

    fun progressDialog(isShow: Boolean) {
        if (progressDialog == null) {
            val builder = activity?.let {
                AlertDialog.Builder(it).apply {
                    setCancelable(false)
                    setView(R.layout.progress_dialog)
                }
            }
            progressDialog = builder?.create()
        }

        progressDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

        if (isShow) {
            if (progressDialog?.isShowing == false) progressDialog?.show()
        } else {
            if (progressDialog?.isShowing == true) progressDialog?.dismiss()
        }
    }

    fun takeGallery(requestCode: Int?) {
        TedPermission.with(activity)
            .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    try {
                        val intent = Intent(Intent.ACTION_PICK)
                        intent.type = "image/*"
                        activity?.packageManager?.let {
                            intent.resolveActivity(it)
                            startActivityForResult(intent, requestCode ?: 0)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                }
            })
            .check()
    }

    fun takePhoto(requestCode: Int?) {
        TedPermission.with(activity)
            .setPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    try {
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        activity?.packageManager?.let {
                            intent.resolveActivity(it)
                            startActivityForResult(intent, requestCode ?: 0)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                }
            })
            .check()
    }

    fun validFileSize(data: Intent?): Boolean {
        if (data == null) {
            activity?.let { getString(R.string.fail_take_image).toast(it) }
            return false
        }

        val size = 1
        val message = "File/Image size tidak boleh melebihi ${size}MB"

        val limit = 1024L * 1024L * size   // 1MB
        val uri = data.data

        // check file size
        if (uri == null) {
            // camera
            if (File(BitmapUtil.getUri(data.getParcelableExtra("data")).path).length() > limit) {
                activity?.let { message.toast(it) }
                return false
            }
        } else {
            // file and image
            if (File(uri.path).length() > limit) {
                activity?.let { message.toast(it) }
                return false
            }
        }

        return true
    }

    fun getDialogChoice(list: ArrayList<String>, onclick: (String) -> Unit): AlertDialog? {
        val adapter = ArrayAdapter<String>(activity ?: return null, android.R.layout.simple_list_item_1)
        list.forEach { adapter.add(it) }

        val builder = AlertDialog.Builder(activity ?: return null)
        builder.setAdapter(adapter) { _, which ->
            onclick(list[which])
        }

        return builder.create()
    }

    fun showcaseToolbar(sharedPrefKey: String, @IdRes idItem: Int, @DrawableRes idDrawable: Int, title: String, subTitle: String, onclick: () -> Unit) {
        baseActivity?.showcaseToolbar(sharedPrefKey, idItem, idDrawable, title, subTitle, onclick)
    }

    fun showcaseView(sharedPrefKey: String, @IdRes idItem: Int, title: String, subTitle: String, onclick: () -> Unit) {
        baseActivity?.showcaseView(sharedPrefKey, idItem, title, subTitle, onclick)
    }

    fun showcaseViewRect(sharedPrefKey: String, @IdRes idItem: Int, title: String, subTitle: String, onclick: () -> Unit) {
        baseActivity?.showcaseViewRect(sharedPrefKey, idItem, title, subTitle, onclick)
    }

}