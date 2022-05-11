package com.sixlogs.pt.data.request.todayprayer

data class Date(
    val gregorian: Gregorian,
    val hijri: Hijri,
    val readable: String,
    val timestamp: String
)