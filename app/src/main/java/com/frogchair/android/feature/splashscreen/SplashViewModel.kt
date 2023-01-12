package com.frogchair.android.feature.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frogchair.android.common.model.PlayerData
import com.frogchair.android.common.network.LocalPlayerRepository
import com.frogchair.android.feature.account.create.data.RemotePlayerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    fun getPlayer(): LiveData<PlayerData?> {
        return MutableLiveData<PlayerData?>().apply {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val playerData = RemotePlayerRepository.getPlayer()
                    LocalPlayerRepository.savePlayer(playerData)
                    postValue(playerData)
                } catch (e: Exception) {
                    postValue(null)
                }

            }
        }
    }

}