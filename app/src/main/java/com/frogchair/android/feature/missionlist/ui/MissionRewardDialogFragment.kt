package com.frogchair.android.feature.missionlist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import com.frogchair.android.R
import com.frogchair.android.common.utils.onClick

class MissionRewardDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "MissionRewardDialogFragment"

        fun newInstance(reward: String) = MissionRewardDialogFragment().apply {
            arguments = Bundle().apply {
//                putString("title", title)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.dialog_mission_reward, container)

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val okButton = view.findViewById<View>(R.id.dialog_mission_reward_button)
        okButton.onClick {
            dismiss()
        }
    }

}