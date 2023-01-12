package com.frogchair.android.common.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import com.frogchair.android.R
import com.frogchair.android.common.utils.onClick

class GenericDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "GenericDialogFragment"
        private const val KEY_TITLE = "KEY_TITLE"
        private const val KEY_MESSAGE = "KEY_MESSAGE"

        fun newInstance(title: String, message: String) = GenericDialogFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_TITLE, title)
                putString(KEY_MESSAGE, message)
            }
        }
    }

    private val titleView by lazy { view?.findViewById<TextView>(R.id.dialog_error_title) }
    private val messageView by lazy { view?.findViewById<TextView>(R.id.dialog_error_text) }
    private val button by lazy { view?.findViewById<TextView>(R.id.dialog_error_button) }

    private val title by lazy { arguments?.getString(KEY_TITLE) ?: "" }
    private val message by lazy { arguments?.getString(KEY_MESSAGE) ?: "" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.dialog_error, container)

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleView?.text = title
        messageView?.text = message

        button?.text = "OK"
        button?.onClick { dismiss() }
    }

}