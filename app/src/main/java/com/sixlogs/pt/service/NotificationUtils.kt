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
import com.sixlogs.pt.activity.MainActivity
import java.util.*


class NotificationUtils(base: Context?) : ContextWrapper(base) {


    private var _notificationManager: NotificationManager? = null
    private var _context: Context? = null

    init {
        _context = base
        createChannel()
    }

    fun setNotification(title: String?, body: String?): NotificationCompat.Builder? {


        val alarmIntent  = Intent(applicationContext, MainActivity::class.java)

        alarmIntent.putExtra("Prayer", ""+body?.split("!!")?.get(0))
        alarmIntent.putExtra("Date", ""+body?.split("!!")?.get(1))

        val  p_i_1 = PendingIntent.getActivity(applicationContext, 12, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        return NotificationCompat.Builder(this, "1")
            .setSmallIcon(R.drawable.btn_minus)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setContentIntent(p_i_1)
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
    fun setReminder(h: Int, m: Int, s: Int, nmzName: String, reqCode: Int) {

//        Log.d("Hey","var hour $h minute $m second $s nmz $nmzName  reqCode $reqCode" )
        Log.d("Hey","var $h:$m:0$s  nmz$nmzName  reqCode $reqCode" )

        val alarmManager = getSystemService(ALARM_SERVICE)as AlarmManager?
        val alarmIntent  = Intent(applicationContext, ReminderBroadcast::class.java)
        alarmIntent.putExtra("count","mint:$m sec$s ")
        alarmIntent.putExtra("pt",nmzName)
        alarmIntent.putExtra("dt",nmzName)
        val pendingIntent1 = PendingIntent.getBroadcast(applicationContext, reqCode, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmIntent.data = (Uri.parse("custom://"+System.currentTimeMillis()));

       val alarmStartTime: Calendar = Calendar.getInstance()
        val now: Calendar = Calendar.getInstance()
        alarmStartTime.set(Calendar.HOUR_OF_DAY, h)
        alarmStartTime.set(Calendar.MINUTE, m)
        alarmStartTime.set(Calendar.SECOND, s)

        if (now.after(alarmStartTime)) {
            Log.d("Hey", "Added a day $a$reqCode")
            alarmStartTime.add(Calendar.DATE, 1)
        }

        alarmManager!!.set(AlarmManager.RTC_WAKEUP, alarmStartTime.timeInMillis, pendingIntent1);
    }


}