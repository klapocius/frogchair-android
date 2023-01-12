package com.frogchair.android.feature.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import com.frogchair.android.R
import com.frogchair.android.common.utils.onClick
import com.frogchair.android.feature.catalog.ui.CatalogActivity
import com.frogchair.android.feature.more.settings.ui.SettingsActivity

class MoreMenuDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "MoreMenuDialogFragment"

        fun newInstance() = MoreMenuDialogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.dialog_more_menu, container)

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

        val outsideDialogOpacity = 0.85f
        dialog?.window?.setDimAmount(outsideDialogOpacity)
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val closeButton = view.findViewById<View>(R.id.dialog_more_menu_cross)
        closeButton.onClick { dismiss() }

//        val helpButton = view.findViewById<View>(R.id.dialog_more_menu_help_area)
//        helpButton.onClick {
//            dismiss()
//        }

        val catalogButton = view.findViewById<View>(R.id.dialog_more_menu_catalog_area)
        catalogButton.onClick {
            dismiss()
            startActivity(CatalogActivity.navigate(requireContext()))
        }

        val settingsButton = view.findViewById<View>(R.id.dialog_more_menu_settings_area)
        settingsButton.onClick { startActivity(SettingsActivity.navigate(requireContext())) }
    }

}