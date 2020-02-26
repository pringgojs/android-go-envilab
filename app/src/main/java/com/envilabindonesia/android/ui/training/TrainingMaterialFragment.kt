package com.envilabindonesia.android.ui.training

import androidx.core.view.forEach
import androidx.navigation.Navigation
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseFragment
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_training.*

/**
 * Created by galihadityo on 2019-04-05.
 */

class TrainingMaterialFragment: BaseFragment() {

    private val navController by lazy {
        activity?.let { Navigation.findNavController(it, R.id.navHostTraining) }
    }

    override fun getLayoutRes(): Int = R.layout.fragment_training

    override fun init() {
        baseActivity?.supportActionBar?.title = resources.getString(R.string.menu_training)
    }

    private fun setCheckedListener() {
        chipGroup.setOnCheckedChangeListener { _, i ->
            when (i) {
                chipVideo.id -> navController?.navigate(R.id.trainingVideoFragment)
                chipPdf.id -> navController?.navigate(R.id.trainingPdfFragment)
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

}