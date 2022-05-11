package com.sixlogs.pt.fragment.todayPrayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sixlogs.pt.data.remoteRepo.AuthRepository
import com.sixlogs.pt.data.remoteRepo.Resource
import com.sixlogs.pt.data.request.todayprayer.TodayPrayerRes
import kotlinx.coroutines.launch

class TodayPrayerTimingViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private var TodayPrayerRes: MutableLiveData<Resource<TodayPrayerRes>> = MutableLiveData()

    val todayRes: LiveData<Resource<TodayPrayerRes>> get() = TodayPrayerRes

    fun getTodays()= viewModelScope.launch {
      TodayPrayerRes.value = repository.getTodayPrayers()
    }



}