package com.sixlogs.homeq.applicationsetting

import android.app.Activity
import com.sixlogs.pt.storage.PTPreferences

class ApplicationSetting(activity: Activity?) {

    var preferences = activity?.let { PTPreferences(it) }

    companion object {
        const val DUMMY = "test"
        const val FAJAR = "fajar"
        const val DUHAR = "duhar"
        const val ASAR = "asar"
        const val MAGHRIB = "maghrib"
        const val ISHA = "isha"
    }

    fun deleteSession() {
        preferences?.deletePrefernce()
    }


    fun setStringValue(key: String, title: String) {
        preferences?.setStringValue(key, title)
    }

    fun getStringValue(key: String): String? {
        return preferences?.getStringValue(key)
    }

    fun setIntegerValue(Key: String, value: Int) {
        preferences?.setIntValue(Key, value)
    }

    fun getIntegerValue(key: String): Int? {
        return preferences?.getIntValue(key)
    }

}
