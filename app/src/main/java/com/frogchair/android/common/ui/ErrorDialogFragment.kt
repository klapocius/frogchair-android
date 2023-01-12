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

class ErrorDialogFragment : DialogFragment() {

    interface Listener {
        fun onRetry()
    }

    companion object {
        const val TAG = "ErrorDialogFragment"
        private const val KEY_ERROR_MESSAGE = "KEY_ERROR_MESSAGE"

        fun newInstance(message: String?) = ErrorDialogFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_ERROR_MESSAGE, message)
            }
        }
    }

    private val retryButton by lazy { view?.findViewById<View>(R.id.dialog_error_button) }
    private val messageView by lazy { view?.findViewById<TextView>(R.id.dialog_error_text) }

    private val errorMessage by lazy { arguments?.getString(KEY_ERROR_MESSAGE) ?: null }
    var listener: Listener? = null


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

        errorMessage?.let { message ->
            messageView?.text = message
        }

        retryButton?.onClick {
            listener?.onRetry()
            dismiss()
        }
    }

}