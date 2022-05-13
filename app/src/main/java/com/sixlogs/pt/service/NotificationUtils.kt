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
import java.util.*


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
    fun setReminder(h: Int, m: Int, s: Int) {
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
        alarmIntent.putExtra("count","mint:$m sec$s ")

        val intent_id = System.currentTimeMillis().toInt()
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, intent_id, alarmIntent,
//            PendingIntent.FLAG_ONE_SHOT)
            PendingIntent.FLAG_UPDATE_CURRENT);
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
        Log.d("Alarm","Alarms set for everyday 8 am.$a ");

        a+=1


        /*// working on random notification generators
        val mgrAlarm = _context?.getSystemService(ALARM_SERVICE) as AlarmManager
        val intentArray: ArrayList<PendingIntent> = ArrayList<PendingIntent>()

    *//*    //pi 1
        val pendingIntent3 = PendingIntent.getBroadcast(applicationContext, intent_id, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmIntent.setData((Uri.parse("custom://"+System.currentTimeMillis())));
        alarmManager!!.cancel(pendingIntent3)
        //pi 2
        val pendingIntent1 = PendingIntent.getBroadcast(applicationContext, intent_id, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmIntent.setData((Uri.parse("custom://"+System.currentTimeMillis())));
        alarmManager!!.cancel(pendingIntent1)
        //pi 3
        val pendingIntent2 = PendingIntent.getBroadcast(applicationContext, intent_id, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmIntent.setData((Uri.parse("custom://"+System.currentTimeMillis())))
        alarmManager!!.cancel(pendingIntent2)*//*

           var i = 0
            while (i < 5) {
                val intent = Intent(_context, ReminderBroadcast::class.java)
                // Loop counter `i` is used as a `requestCode`
                val pendingIntent = PendingIntent.getBroadcast(_context, i, intent, 0)
                // Single alarms in 1, 2, ..., 10 minutes (in `i` minutes)
                mgrAlarm[AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 2000 * i] = pendingIntent

                intentArray.add(pendingIntent)
                ++i
            }*/


        // working for 5 times
        val mgrAlarm = _context?.getSystemService(ALARM_SERVICE) as AlarmManager
        val intentArray: ArrayList<PendingIntent> = ArrayList<PendingIntent>()

        val alarms: ArrayList<Calendar> = ArrayList<Calendar>()

       //set first
        val alarmStartTime1: Calendar = Calendar.getInstance()
        val now1: Calendar = Calendar.getInstance()
        alarmStartTime1.set(Calendar.HOUR_OF_DAY, 17)
        alarmStartTime1.set(Calendar.MINUTE, 40)
        alarmStartTime1.set(Calendar.SECOND, 10)


        if (now1.after(alarmStartTime1)) {
            Log.d("Hey", "Added a day $a")
            alarmStartTime1.add(Calendar.DATE, 1)
        }
        //set second
        val alarmStartTime2: Calendar = Calendar.getInstance()
        val now2: Calendar = Calendar.getInstance()
        alarmStartTime2.set(Calendar.HOUR_OF_DAY, 17)
        alarmStartTime2.set(Calendar.MINUTE, 40)
        alarmStartTime2.set(Calendar.SECOND, 20)


        if (now2.after(alarmStartTime2)) {
            Log.d("Hey", "Added a day2 $a")
            alarmStartTime2.add(Calendar.DATE, 1)
        }

        //set third
        val alarmStartTime3: Calendar = Calendar.getInstance()
        val now3: Calendar = Calendar.getInstance()
        alarmStartTime3.set(Calendar.HOUR_OF_DAY, 17)
        alarmStartTime3.set(Calendar.MINUTE, 40)
        alarmStartTime3.set(Calendar.SECOND, 30)


        if (now3.after(alarmStartTime2)) {
            Log.d("Hey", "Added a day3 $a")
            alarmStartTime3.add(Calendar.DATE, 1)
        }

        //set fourth
        val alarmStartTime4: Calendar = Calendar.getInstance()
        val now4: Calendar = Calendar.getInstance()
        alarmStartTime4.set(Calendar.HOUR_OF_DAY, 17)
        alarmStartTime4.set(Calendar.MINUTE,40)
        alarmStartTime4.set(Calendar.SECOND, 40)


        if (now4.after(alarmStartTime2)) {
            Log.d("Hey", "Added a day4 $a")
            alarmStartTime4.add(Calendar.DATE, 1)
        }

        //set fifth
        val alarmStartTime5: Calendar = Calendar.getInstance()
        val now5: Calendar = Calendar.getInstance()
        alarmStartTime5.set(Calendar.HOUR_OF_DAY, 17)
        alarmStartTime5.set(Calendar.MINUTE,40)
        alarmStartTime5.set(Calendar.SECOND, 50)


        if (now5.after(alarmStartTime2)) {
            Log.d("Hey", "Added a day5 $a")
            alarmStartTime5.add(Calendar.DATE, 1)
        }


        alarms.add(alarmStartTime1)
        alarms.add(alarmStartTime2)
        alarms.add(alarmStartTime3)
        alarms.add(alarmStartTime4)
        alarms.add(alarmStartTime5)

        var i = 0
        while (i < 5) {
            val intent = Intent(_context, ReminderBroadcast::class.java)
            intent.putExtra("count", alarms.get(i))
            // Loop counter `i` is used as a `requestCode`
            val pendingIntent = PendingIntent.getBroadcast(_context, i, intent, 0)
            // Single alarms in 1, 2, ..., 10 minutes (in `i` minutes)
            mgrAlarm[AlarmManager.ELAPSED_REALTIME_WAKEUP,  alarms.get(i).getTimeInMillis()] = pendingIntent

            intentArray.add(pendingIntent)
            ++i
        }

    
    }
}