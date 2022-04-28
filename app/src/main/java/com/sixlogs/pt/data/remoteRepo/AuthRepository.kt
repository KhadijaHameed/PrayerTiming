package com.sixlogs.pt.data.remoteRepo

import com.sixlogs.pt.data.network.TodayPrayerAPI

class AuthRepository( private val api: TodayPrayerAPI): BaseRepository() {

    suspend fun getTodayPrayers(reqToday: String) = safeApiCall {
        api.getTodayTimings(reqToday)
    }
}