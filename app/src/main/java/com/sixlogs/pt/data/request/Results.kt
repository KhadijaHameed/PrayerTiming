package com.sixlogs.pt.data.request

data class Results(
    val datetime: List<Datetime>,
    val location: Location,
    val settings: Settings
)