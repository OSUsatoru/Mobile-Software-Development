package com.example.mediaplayer69.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mediaplayer69.data.AppDatabase
import com.example.mediaplayer69.data.SearchInfo
import com.example.mediaplayer69.data.SearchInfoRepository
import kotlinx.coroutines.launch


class SearchInfoViewModel(application: Application) : AndroidViewModel(application){
    private val repository = SearchInfoRepository(
        AppDatabase.getInstance(application).SearchDAO()
    )

    val searchInfoRepos = repository.getSearchInfoRepos().asLiveData()

    fun addSearchInfo(searchInfo: SearchInfo){
        viewModelScope.launch {
            repository.insertSearchInfo(searchInfo)
        }
    }

    fun removeSearchInfo(searchInfo: SearchInfo){
        viewModelScope.launch {
            repository.deleteSearchInfo(searchInfo)
        }
    }
}