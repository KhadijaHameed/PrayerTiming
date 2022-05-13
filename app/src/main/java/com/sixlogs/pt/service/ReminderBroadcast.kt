package com.sixlogs.pt.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock


class ReminderBroadcast : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val _notificationUtils = NotificationUtils(context)
        val _builder = _notificationUtils.setNotification("Testing", "Testing notification system" +
               // " counter${intent?.getStringExtra("count")}" +
                "time ${intent?.getStringExtra("count")}")
        _notificationUtils.getManager()!!.notify(101, _builder!!.build())

//        val intent_id = System.currentTimeMillis().toInt()
    }
}