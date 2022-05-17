package com.sixlogs.pt.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock


class ReminderBroadcast : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {

      var time = " ${intent?.getStringExtra("pt")}"

      //    if (time == "fajr") {
        val _notificationUtils = NotificationUtils(context)
            val _builder = _notificationUtils.setNotification("Testing",
                "$time ==${intent?.getStringExtra("count")}")
            _notificationUtils.getManager()!!.notify(110, _builder!!.build())

//        val intent_id = System.currentTimeMillis().toInt()
      //  } else if (time == "dhr") {
           /* val _notificationUtils2 = NotificationUtils(context)
            val _builder2 = _notificationUtils2.setNotification("Testing2",
                "$time ## ${intent?.getStringExtra("count")}")
            _notificationUtils2.getManager()!!.notify(111, _builder2!!.build())*/
    //    }
    }
    //same notification k 2 channel
    //hr notification ka 1 channel chahye
}