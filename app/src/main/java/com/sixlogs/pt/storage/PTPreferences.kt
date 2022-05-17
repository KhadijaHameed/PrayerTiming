package com.sixlogs.pt.storage

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle

class PTPreferences(context: Context) {

    private val prefName: String = "PT"

    var sharedpreferences: SharedPreferences =
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    private var editor = sharedpreferences.edit()

    fun  deletePrefernce(){
        editor.clear().commit();
        sharedpreferences.edit().clear().apply()
    }

    fun setValue(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.commit()
    }

    fun getValue(key: String): Boolean {
        var boolean: Boolean = sharedpreferences.getBoolean(key, false)
        return boolean
    }

    fun setStringValue(key: String, value: String) {
        editor.putString(key, value)
        editor.commit()
    }

    fun getStringValue(key: String): String? {
        return sharedpreferences.getString(key, "")
    }

    fun setIntValue(key: String, value: Int) {
        editor.putInt(key, value)
        editor.commit()
    }

    fun getIntValue(key: String): Int? {
        return sharedpreferences.getInt(key, 0)
    }


}