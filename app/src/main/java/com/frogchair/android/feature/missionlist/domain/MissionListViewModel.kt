package com.frogchair.android.feature.missionlist.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frogchair.android.feature.missionlist.data.MissionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MissionListViewModel : ViewModel() {

    fun getChapters(): LiveData<MissionResponse> {
        return MutableLiveData<MissionResponse>().apply {
            viewModelScope.launch(Dispatchers.IO) {
                val result = try {
                    MissionResponse(MissionRepository.getMissions())
                } catch (e: Exception) {
                    MissionResponse(null, e.message)
                }
                postValue(result)
            }
        }
    }

}