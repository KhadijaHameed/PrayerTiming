package com.sixlogs.pt.data.network

import com.sixlogs.pt.data.request.todayprayer.TodayPrayerRes
import retrofit2.http.GET

interface TodayPrayerAPI {

    @GET("v1/timingsByAddress?address=Karachi,Pakistan")
    suspend fun getTodayTimings(): TodayPrayerRes


}