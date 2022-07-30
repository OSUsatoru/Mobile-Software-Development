package com.example.android.lifecycleweather.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.lifecycleweather.data.*
import kotlinx.coroutines.launch

class ForecastViewModel:ViewModel() {
    private val repository = ForecastRepository(ForecastService.create())
    //private val _fetchResults = MutableLiveData<List<ForecastPeriod>>(null)
    private val _fetchResults = MutableLiveData<FiveDayForecast>(null)
    val fetchResults: LiveData<FiveDayForecast?> = _fetchResults

    private val _loadingStatus = MutableLiveData(LoadingStatus.SUCCESS)
    val loadingStatus : LiveData<LoadingStatus> = _loadingStatus

    fun loadFetchResults(city: String?, units: String?){
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.LOADING

            val result = repository.loadRepositoriesFetch(city,units)
            _fetchResults.value =  result.getOrNull()

            _loadingStatus.value = when(result.isSuccess){
                true -> LoadingStatus.SUCCESS
                false -> LoadingStatus.ERROR
            }

        }
    }

}