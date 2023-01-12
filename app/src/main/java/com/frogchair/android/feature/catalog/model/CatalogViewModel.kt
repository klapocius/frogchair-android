package com.frogchair.android.feature.catalog.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frogchair.android.feature.catalog.data.CatalogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CatalogViewModel : ViewModel() {

    fun getCatalog(pageNumber: Int) = MutableLiveData<CatalogResponse>().apply {
        viewModelScope.launch(Dispatchers.IO) {
            val result = try {
                CatalogResponse(CatalogRepository.getCatalog(pageNumber))
            } catch (e: Exception) {
                CatalogResponse(null, e.message)
            }
            postValue(result)
        }
    }

}