package com.sixlogs.pt.fragment.todayPrayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sixlogs.pt.data.remoteRepo.AuthRepository
import com.sixlogs.pt.data.remoteRepo.Resource
import com.sixlogs.pt.data.request.TodayPrayerResponse
import kotlinx.coroutines.launch

class TodayPrayerTimingViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private var todayPrayerResponse: MutableLiveData<Resource<TodayPrayerResponse>> = MutableLiveData()

    val todayRes: LiveData<Resource<TodayPrayerResponse>> get() = todayPrayerResponse

    fun getTodays()= viewModelScope.launch {
      todayPrayerResponse.value = repository.getTodayPrayers("today")
    }

}