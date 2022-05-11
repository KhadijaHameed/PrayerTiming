package com.sixlogs.pt.data.request.todayprayer

data class Method(
    val id: Int,
    val location: Location,
    val name: String,
    val params: Params
)