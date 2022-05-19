package com.sixlogs.pt.model

data  class QazaPrayerModel (
    val id: Long,
    val date: String?,
    val fajar: Boolean? = false,
    val duhar: Boolean? = false,
    val asar: Boolean? = false,
    val maghrib: Boolean? = false,
    val isha: Boolean? = false,
    )