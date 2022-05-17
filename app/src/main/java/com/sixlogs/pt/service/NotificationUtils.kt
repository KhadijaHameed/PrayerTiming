package com.sixlogs.pt.service

import android.R
import android.app.*
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat
import com.sixlogs.pt.MainActivity
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
        var  p_i_1 = PendingIntent.getActivity(applicationContext, 12, alarmIntent,
//            PendingIntent.FLAG_ONE_SHOT)
            PendingIntent.FLAG_UPDATE_CURRENT)

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
    fun setReminder(h: Int, m: Int, s: Int) {
       /* val _intent = Intent(applicationContext, ReminderBroadcast::class.java)
        val _pendingIntent = PendingIntent.getBroadcast(applicationContext, 0, _intent, 0)
        val _alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager?
        _alarmManager!![AlarmManager.RTC_WAKEUP, timeInMillis] = _pendingIntent*/

        Log.d("Hey","var hour $h minute $m second $s" )

        var hour = h
        var mint = m
        var second = s
        //
        val alarmManager = getSystemService(ALARM_SERVICE)as AlarmManager?
        val alarmIntent  = Intent(applicationContext, ReminderBroadcast::class.java)
        alarmIntent.putExtra("count","mint:$m sec$s ")
        alarmIntent.putExtra("pt","fajr") 
       var  pendingIntent1 = PendingIntent.getBroadcast(applicationContext, 12, alarmIntent,
//            PendingIntent.FLAG_ONE_SHOT)
            PendingIntent.FLAG_UPDATE_CURRENT);
        alarmIntent.setData((Uri.parse("custom://"+System.currentTimeMillis())));
       // alarmManager!!.cancel(pendingIntent1)


      val alarmStartTime: Calendar = Calendar.getInstance()
        val now: Calendar = Calendar.getInstance()
        alarmStartTime.set(Calendar.HOUR_OF_DAY, hour)
        alarmStartTime.set(Calendar.MINUTE, mint)
        alarmStartTime.set(Calendar.SECOND, second)


        if (now.after(alarmStartTime)) {
            Log.d("Hey", "Added a day $a")
            alarmStartTime.add(Calendar.DATE, 1)
        }


       // alarmManager!!.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent1);
        alarmManager!!.set(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), pendingIntent1);

    }
    fun setReminder2(h: Int, m: Int, s: Int) {
       /* val _intent = Intent(applicationContext, ReminderBroadcast::class.java)
        val _pendingIntent = PendingIntent.getBroadcast(applicationContext, 0, _intent, 0)
        val _alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager?
        _alarmManager!![AlarmManager.RTC_WAKEUP, timeInMillis] = _pendingIntent*/

        Log.d("Hey","var hour $h minute $m second $s" )

        var hour = h
        var mint = m
        var second = s
        //
        val alarmManager = getSystemService(ALARM_SERVICE)as AlarmManager?
        val alarmIntent  = Intent(applicationContext, ReminderBroadcast::class.java)
        alarmIntent.putExtra("count","mint:$m sec$s ")
        alarmIntent.putExtra("pt","dhr")
       //alarmIntent.putExtra("intcid",2)

        val intent_id = System.currentTimeMillis().toInt()
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, 13, alarmIntent,
//            PendingIntent.FLAG_ONE_SHOT)
            PendingIntent.FLAG_UPDATE_CURRENT);
        alarmIntent.setData((Uri.parse("custom://"+System.currentTimeMillis())));
     //   alarmManager!!.cancel(pendingIntent)


      val alarmStartTime: Calendar = Calendar.getInstance()
        val now: Calendar = Calendar.getInstance()
        alarmStartTime.set(Calendar.HOUR_OF_DAY, hour)
        alarmStartTime.set(Calendar.MINUTE, mint)
        alarmStartTime.set(Calendar.SECOND, second)


        if (now.after(alarmStartTime)) {
            Log.d("Hey", "Added a day $a")
            alarmStartTime.add(Calendar.DATE, 1)
        }


       // alarmManager!!.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        alarmManager!!.set(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), pendingIntent);

    }
}