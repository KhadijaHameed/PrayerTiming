package com.sixlogs.pt.service

import android.R
import android.app.*
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import java.util.*
import kotlin.math.min


class NotificationUtils(base: Context?) : ContextWrapper(base) {


    private var _notificationManager: NotificationManager? = null
    private var _context: Context? = null

    init {
        _context = base
        createChannel()
    }

    fun setNotification(title: String?, body: String?): NotificationCompat.Builder? {
        return NotificationCompat.Builder(this, "1")
            .setSmallIcon(R.drawable.btn_minus)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "1",
                "TIMELINE_CHANNEL_NAME",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            getManager()!!.createNotificationChannel(channel)
        }
    }

    fun getManager(): NotificationManager? {
        if (_notificationManager == null) {
            _notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        }
        return _notificationManager
    }

    var a = 1
    fun setReminder(timeInMillis: Long, h: Int, m: Int, s: Int) {
       /* val _intent = Intent(applicationContext, ReminderBroadcast::class.java)
        val _pendingIntent = PendingIntent.getBroadcast(applicationContext, 0, _intent, 0)
        val _alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager?
        _alarmManager!![AlarmManager.RTC_WAKEUP, timeInMillis] = _pendingIntent*/


        var hour = h
        var mint = m
        var second = s
        //
        val alarmManager = getSystemService(ALARM_SERVICE)as AlarmManager?
        val alarmIntent  = Intent(applicationContext, ReminderBroadcast::class.java)

        val pendingIntent = PendingIntent.getBroadcast(applicationContext, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmIntent.setData((Uri.parse("custom://"+System.currentTimeMillis())));
        alarmManager!!.cancel(pendingIntent)


      val alarmStartTime: Calendar = Calendar.getInstance()
        val now: Calendar = Calendar.getInstance()
        alarmStartTime.set(Calendar.HOUR_OF_DAY, hour)
        alarmStartTime.set(Calendar.MINUTE, mint)
        alarmStartTime.set(Calendar.SECOND, second)


        if (now.after(alarmStartTime)) {
            Log.d("Hey", "Added a day $a")
            alarmStartTime.add(Calendar.DATE, 1)
        }


        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
      //  alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime2.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);
        Log.d("Alarm","Alarms set for everyday 8 am.$a ");

        a+=1
//        val notificationUtils = NotificationUtils(_context)
//        notificationUtils.
           // setReminder(70000,17,40,0)
    }
}