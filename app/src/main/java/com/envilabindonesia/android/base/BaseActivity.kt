package com.envilabindonesia.android.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ViewFlipper
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import com.envilabindonesia.android.R
import com.envilabindonesia.android.data.repository.Local
import com.envilabindonesia.android.extension.RxBus
import com.envilabindonesia.android.extension.RxEvent
import com.envilabindonesia.android.extension.toast
import com.envilabindonesia.android.ui.login.LoginActivity
import com.envilabindonesia.android.util.PrefsUtil
import dagger.android.support.DaggerAppCompatActivity
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.RectanglePromptBackground
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal

/**
 * Created by galihadityo on 2019-03-02.
 */

abstract class BaseActivity: DaggerAppCompatActivity() {

    private var loadingFlipper: ViewFlipper? = null
    private var loadingFlipperSecondary: ViewFlipper? = null
    private var button: AppCompatButton? = null
    private var buttonSecondary: AppCompatButton? = null
    private var rxDisposable: Disposable? = null

    companion object {
        private const val DELAY_SHOWCASE = 500L
    }

    @LayoutRes
    abstract fun getLayoutRes(): Int

    abstract fun init()

    override fun onPause() {
        super.onPause()
        rxDisposable?.let {
            if (!it.isDisposed) it.dispose()
        }
    }

    override fun onResume() {
        super.onResume()
        registerRxBus()
    }

    private fun registerRxBus() {
        rxDisposable = RxBus.subscribe<RxEvent.Logout>()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                it.message?.let { m -> m.toast(this@BaseActivity) }

                Local().logout()
                startActivity(Intent(this, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        calligraphy()
        setContentView(getLayoutRes())
        init()
    }

    fun setLoadingButton(@IdRes idIncludeLayout: Int, @StringRes idText: Int, onclick:() -> Unit) {
        val v = findViewById<View>(idIncludeLayout)
        loadingFlipper = v.findViewById(R.id.vf)    // viewflipper `id` mandatory `R.id.vf`
        button = v.findViewById(R.id.button)        // button `id` mandatory `R.id.button`
        button?.let {
            it.text = getString(idText)
            it.setOnClickListener {
                onclick()
            }
        }
    }

    fun setLoadingButtonSecondary(@IdRes idIncludeLayout: Int, @StringRes idText: Int, onclick:() -> Unit) {
        val v = findViewById<View>(idIncludeLayout)
        loadingFlipperSecondary = v.findViewById(R.id.vf)    // viewflipper `id` mandatory `R.id.vf`
        buttonSecondary = v.findViewById(R.id.button)        // button `id` mandatory `R.id.button`
        buttonSecondary?.let {
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

    fun isLoadingSecondary(load: Boolean) {
        loadingFlipperSecondary?.let {
            it.displayedChild = if (load) 1 else 0
        }
    }

    private fun calligraphy() {
        val config = CalligraphyConfig.Builder()
            .setDefaultFontPath(resources.getString(R.string.path_font_robot_regular))
            .setFontAttrId(R.attr.fontPath)
            .build()

        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(CalligraphyInterceptor(config))
                .build()
        )
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase ?: return))
    }

    fun getDialogChoice(list: ArrayList<String>, onclick: (String) -> Unit): AlertDialog? {
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
        list.forEach { adapter.add(it) }

        val builder = AlertDialog.Builder(this)
        builder.setAdapter(adapter) { _, which ->
            onclick(list[which])
        }

        return builder.create()
    }

    fun setTitleBar(title: String? = null) {
        if (supportActionBar == null) {
            getString(R.string.actionbar_not_set)
            return
        }

        title?.let {
            supportActionBar?.title = it
        }
    }

    fun setBackButton(toolbar: Toolbar, onclick: () -> Unit) {
        if (supportActionBar == null) {
            getString(R.string.actionbar_not_set)
            return
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener { onclick() }
    }

    fun showcaseToolbar(sharedPrefKey: String, @IdRes idItem: Int, @DrawableRes idDrawable: Int, title: String, subTitle: String, onclick: () -> Unit) {
        val isShown = PrefsUtil.read(sharedPrefKey, Boolean::class.java)
        if (isShown != null) return
        Handler().postDelayed({
            applicationContext?.let {
                PrefsUtil.write(sharedPrefKey, true)
                MaterialTapTargetPrompt.Builder(this)
                    .setTarget(idItem)
                    .setIcon(idDrawable)
                    .setPrimaryText(title)
                    .setSecondaryText(subTitle)
                    .setPromptStateChangeListener { _, state ->
                        if (state == MaterialTapTargetPrompt.STATE_DISMISSED) {
                            onclick()
                        }
                    }
                    .show()
            }
        }, DELAY_SHOWCASE)
    }


    fun showcaseViewRect(sharedPrefKey: String, @IdRes idItem: Int, title: String, subTitle: String, onclick: () -> Unit) {
        val isShown = PrefsUtil.read(sharedPrefKey, Boolean::class.java)
        if (isShown != null) return
        Handler().postDelayed({
            applicationContext?.let {
                PrefsUtil.write(sharedPrefKey, true)
                MaterialTapTargetPrompt.Builder(this)
                    .setTarget(idItem)
                    .setPrimaryText(title)
                    .setSecondaryText(subTitle)
                    .setPromptBackground(RectanglePromptBackground())
                    .setPromptFocal(RectanglePromptFocal())
                    .setPromptStateChangeListener { _, state ->
                        if (state == MaterialTapTargetPrompt.STATE_DISMISSED) {
                            onclick()
                        }
                    }
                    .show()
            }
        }, DELAY_SHOWCASE)
    }

    fun showcaseView(sharedPrefKey: String, @IdRes idItem: Int, title: String, subTitle: String, onclick: () -> Unit) {
        val isShown = PrefsUtil.read(sharedPrefKey, Boolean::class.java)
        if (isShown != null) return
        Handler().postDelayed({
            applicationContext?.let {
                PrefsUtil.write(sharedPrefKey, true)
                MaterialTapTargetPrompt.Builder(this)
                    .setTarget(idItem)
                    .setPrimaryText(title)
                    .setSecondaryText(subTitle)
                    .setPromptStateChangeListener { _, state ->
                        if (state == MaterialTapTargetPrompt.STATE_DISMISSED) {
                            onclick()
                        }
                    }
                    .show()
            }
        }, DELAY_SHOWCASE)
    }

}