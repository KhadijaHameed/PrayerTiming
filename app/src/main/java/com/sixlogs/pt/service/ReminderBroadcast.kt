package com.sixlogs.pt.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class ReminderBroadcast : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val _notificationUtils = NotificationUtils(context)
        val _builder = _notificationUtils.setNotification("Testing", "Testing notification system")
        _notificationUtils.getManager()!!.notify(101, _builder!!.build())
    }
}