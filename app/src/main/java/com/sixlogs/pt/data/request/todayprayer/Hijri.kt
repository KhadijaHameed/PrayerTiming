package com.sixlogs.pt.data.request.todayprayer

data class Hijri(
    val date: String,
    val day: String,
    val designation: DesignationX,
    val format: String,
    val holidays: List<Any>,
    val month: MonthX,
    val weekday: WeekdayX,
    val year: String
)