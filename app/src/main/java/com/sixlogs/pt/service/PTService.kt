package com.sixlogs.pt.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.sixlogs.pt.MainActivity
import com.sixlogs.pt.R
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
    private var notificationMessage = "TimeService successfully uploaded...!"


    override fun doWork(): Result {
        Log.w(TAG, "doWork: background work started... ")
        try {
            GlobalScope.launch {
                async(start = CoroutineStart.LAZY) {
                    getDbRecord()
                }.start()

            }
            //showNotification_("WorkWallet?test", notificationMessage)
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
//        val db = HomeQDatabase.getAppDatabase(context)
//        val flagList = db.homeOwnerFlag().getAllFlags()
//        for (item in flagList) {
//           if(item.status == 5){
     //   sendServerRequest()
//            }
//        }
    }

    private fun reminderNotification() {
        
//        val currentTime = System.currentTimeMillis()
//        val tenSeconds = (1000 * 10).toLong()
//        val triggerReminder = currentTime + tenSeconds //triggers a reminder after 10 seconds.
        val notificationUtils = NotificationUtils( context)
        notificationUtils.setReminder(11,9,0)
     //   val notificationUtils2 = NotificationUtils( context)
        notificationUtils.setReminder2(11,10,0)

    }



    fun playNotificationSound() {
      /*  try {
            val sourd2 = RingtoneManager.getRingtone(
                this.applicationContext,
            //    Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + this.packageName + "/" + R.raw.notification))
//            sourd2.play()
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

