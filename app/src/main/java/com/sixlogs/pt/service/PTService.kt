package com.sixlogs.pt.service

import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
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



    fun reminderNotification() {
        val notificationUtils = NotificationUtils( context)
        val currentTime = System.currentTimeMillis()
        val tenSeconds = (1000 * 10).toLong()
        val triggerReminder = currentTime + tenSeconds //triggers a reminder after 10 seconds.
        notificationUtils.setReminder(triggerReminder,18,0,0)
    }



    /*private fun notifyingActivityServiceIsCompletedDoProcessyourWork() {
        val intent = Intent("FileCompleted")
        //   intent.setAction("FileCompleted")
        intent.putExtra("file", "Yeaa all file completed...!")
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
    }*/


    @Throws(IOException::class)
    @Synchronized
    private fun sendServerRequest() {
        try {

            notificationMessage = " successfully updated..!"

        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }

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
    }


    private fun getRandomNumber(): Int {
        val dd = Date()
        val ft = SimpleDateFormat("mmssSS")
        val s: String = ft.format(dd)
        return s.toInt()
    }
}

