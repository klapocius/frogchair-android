package com.frogchair.android.feature.account.create.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frogchair.android.feature.account.create.data.RemotePlayerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class CreateAccountViewModel : ViewModel() {

    companion object {
        const val BITMAP_WIDTH = 100
    }

    fun createAccount(username: String, imagePath: String?): LiveData<Boolean> {
        return MutableLiveData<Boolean>().apply {
            val encodedImage = getEncodedImage(imagePath)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    RemotePlayerRepository.create(username, encodedImage)
                    postValue(true)
                } catch (e: java.lang.Exception) {
                    Log.e("", e.stackTraceToString())
                    postValue(false)
                }
            }
        }
    }

    private fun getEncodedImage(imagePath: String?): String {
        if (imagePath.isNullOrBlank()) return ""
        return try {
            val bitmap = BitmapFactory.decodeFile(imagePath)
            val resizedBitmap = Bitmap.createScaledBitmap(bitmap, BITMAP_WIDTH, BITMAP_WIDTH, false)
            val stream = ByteArrayOutputStream()
            resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT)
        } catch (e: Exception) {
            Log.e("", e.stackTraceToString())
            ""
        }
    }
}