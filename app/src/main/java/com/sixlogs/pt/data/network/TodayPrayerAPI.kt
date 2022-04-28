package com.sixlogs.pt.data.network

import com.sixlogs.pt.data.request.TodayPrayerResponse
import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TodayPrayerAPI {
//    @GET("city=karachi")
    @GET()
    suspend fun getTodayTimings(today: String): TodayPrayerResponse
    //suspend fun getTodayTimings(@Body map: HashMap<String, String>): JSONObject

//    @POST("api/ServiceType/GetServiceTypes")
//    suspend fun getCategories(@Body params: JSONObject): CategoriesResponce
}