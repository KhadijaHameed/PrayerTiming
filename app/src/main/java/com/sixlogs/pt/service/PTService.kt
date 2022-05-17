package com.sixlogs.pt.service

import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.sixlogs.homeq.applicationsetting.ApplicationSetting
import com.sixlogs.pt.MainActivity
import com.sixlogs.pt.R
import com.sixlogs.pt.storage.PTPreferences
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class PTService(
    val context: Context,
    val params: WorkerParameters
) : Worker(context, params) {

    val TAG = "TimeService"

    override fun doWork(): Result {
        Log.w(TAG, "doWork: background work started... ")
        try {
            GlobalScope.launch {
                async(start = CoroutineStart.LAZY) {
                    getDbRecord()
                }.start()

            }
         } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
        return Result.success()
    }

    @Throws(IOException::class)
    @Synchronized
    suspend fun getDbRecord() {
        reminderNotification()
        showNotification("separeONe", "first")
    }

    val nmzList = arrayListOf("Fajar", "Duhar", "Asar" , "Maghrib", "Isha")
    val hourList = arrayListOf(16,16,16,16,16)
//    val mintList = arrayListOf(1,3,3,4,5)
    val mintList = arrayListOf(54,55,56,57,58)
    val secondList = arrayListOf(0,0,0,0,0)
 //   val secondList = arrayListOf(0,15,30,45,59)
    private fun reminderNotification() {

        
        val notificationUtils = NotificationUtils(context)
        for (i in 0 until nmzList.size) {
            when(i){
                0 -> notificationUtils.setReminder(hourList[i],mintList[i],secondList[i],nmzList[i],i )
                1 -> notificationUtils.setReminder(hourList[i],mintList[i],secondList[i],nmzList[i],i )
                2 -> notificationUtils.setReminder(hourList[i],mintList[i],secondList[i],nmzList[i], i)
                3 -> notificationUtils.setReminder(hourList[i],mintList[i],secondList[i],nmzList[i], i)
                4 -> notificationUtils.setReminder(hourList[i],mintList[i],secondList[i],nmzList[i], i)

            }

        }
        
//        notificationUtils.setReminder3(14,6,0)
    }

    fun playNotificationSound() {
      /*  try {
            val sourd3 = RingtoneManager.getRingtone(
                this.applicationContext,
            //    Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + this.packageName + "/" + R.raw.notification))
//            sourd3.play()
        } catch (ex: Exception) {

        }*/
    }


    fun showNotification(title: String, message: String) {
        val intent: Intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val manager = NotificationManagerCompat.from(context)
        val sound =
            Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.packageName + "/raw/notification.mp3")


        var NOTIFICATION_CHANNEL_ID = "11"
        val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setSound(sound)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true).build()
        manager.notify(getRandomNumber(), builder)
//        playNotificationSound()
    }


    private fun getRandomNumber(): Int {
        val dd = Date()
        val ft = SimpleDateFormat("mmssSS")
        val s: String = ft.format(dd)
        return s.toInt()
    }
}

