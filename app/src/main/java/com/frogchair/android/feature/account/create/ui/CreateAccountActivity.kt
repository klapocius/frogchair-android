package com.frogchair.android.feature.account.create.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.frogchair.android.R
import com.frogchair.android.common.utils.alphaNumericOnly
import com.frogchair.android.common.utils.onClick
import com.frogchair.android.common.utils.onResultLauncher
import com.frogchair.android.common.utils.showToast
import com.frogchair.android.feature.account.create.model.CreateAccountViewModel
import com.frogchair.android.feature.splashscreen.SplashActivity
import com.github.dhaval2404.imagepicker.ImagePicker


class CreateAccountActivity : AppCompatActivity() {

    companion object {
        private const val IMAGE_HEIGHT = 256

        fun navigate(context: Context) = Intent(context, CreateAccountActivity::class.java)
    }

    private val toolbarText: TextView by lazy { findViewById(R.id.title_toolbar_text) }
    private val toolbarHomeButton: View by lazy { findViewById(R.id.title_home_button) }

    private val editText: EditText by lazy { findViewById(R.id.create_account_edit_text) }
    private val imageView: ImageView by lazy { findViewById(R.id.create_account_profile_image) }
    private val imageButton: View by lazy { findViewById(R.id.create_account_profile_image_button) }
    private val validateButton: View by lazy { findViewById(R.id.create_account_validate_button) }

    private var chosenImagePath: String? = null

    private val viewModel: CreateAccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        toolbarText.text = "CREATE ACCOUNT"
        toolbarHomeButton.visibility = View.GONE

        imageView.onClick { startImagePicker() }
        imageButton.onClick { startImagePicker() }
        validateButton.onClick { sendInfos() }
    }

    private fun startImagePicker() {
        ImagePicker.with(this)
            .cropSquare()
            .compress(1024)
            .maxResultSize(IMAGE_HEIGHT, IMAGE_HEIGHT)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }

    private val startForProfileImageResult = onResultLauncher {
        if (it.data != null) {
            chosenImagePath = it.data!!.path
            imageView.setImageURI(it.data)
        } else {
            showToast(ImagePicker.getError(it))
        }
    }

    private fun sendInfos() {
        val username = editText.text.toString().alphaNumericOnly()
        if (username.isBlank()) {
            editText.error = "that ain't no name"
        } else {
            viewModel.createAccount(username, chosenImagePath).observe(this, { success ->
                handleCreationResult(success)
            })
        }
    }

    private fun handleCreationResult(success: Boolean) {
        if (success) {
            goToHome()
        } else {
            showToast("Unable to create account. Please try again later")
        }
    }

    private fun goToHome() {
        startActivity(SplashActivity.navigate(this))
        finish()
    }

}