package com.sixlogs.pt.data.request

data class Location(
    val city: String,
    val country: String,
    val country_code: String,
    val elevation: Double,
    val latitude: Double,
    val local_offset: Double,
    val longitude: Double,
    val timezone: String
)