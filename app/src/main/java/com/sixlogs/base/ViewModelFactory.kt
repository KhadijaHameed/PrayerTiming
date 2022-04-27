package com.sixlogs.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sixlogs.pt.MainActivityViewModel
import com.sixlogs.activity.splash.SplashViewModel
import com.sixlogs.fragment.qazaPrayersRecords.QazaPrayersRecordsViewModel
import com.sixlogs.fragment.tasbeehat.TasbeehatViewModel
import com.sixlogs.fragment.todayPrayer.TodayPrayerTimingViewModel
import java.lang.IllegalArgumentException


class ViewModelFactory(
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return when{
                modelClass.isAssignableFrom(SplashViewModel::class.java) -> SplashViewModel() as T
                modelClass.isAssignableFrom(MainActivityViewModel::class.java) -> MainActivityViewModel() as T
                modelClass.isAssignableFrom(TodayPrayerTimingViewModel::class.java) -> TodayPrayerTimingViewModel() as T
                modelClass.isAssignableFrom(QazaPrayersRecordsViewModel::class.java) -> QazaPrayersRecordsViewModel() as T
                modelClass.isAssignableFrom(TasbeehatViewModel::class.java) -> TasbeehatViewModel() as T

                else -> throw IllegalArgumentException("ViewModel Class NOt Found")
            }
        }

    }