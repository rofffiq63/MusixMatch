package under.the.bridge.util

import android.app.*
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.getSystemService
import android.os.Build
import com.readystatesoftware.chuck.internal.ui.MainActivity
import under.the.bridge.R
import under.the.bridge.features.detail.DetailActivity
import java.text.SimpleDateFormat

class NotificationPublisher : BroadcastReceiver() {
    companion object {
        var NOTIFICATION_ID = "notification-id"
        var NOTIFICATION = "notification"
        var CHANNEL_ID = "com.singhajit.notificationDemo.channelId"
    }

    override fun onReceive(context: Context?, intent: Intent?) {

        var date = intent?.getLongExtra("date", 0)

        val notificationIntent = Intent(context, DetailActivity::class.java)
                .putExtra("date", SimpleDateFormat("dd MMMM yyyy").format(date))

        val stackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addParentStack(MainActivity::class.java)
        stackBuilder.addNextIntent(notificationIntent)

        val pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = Notification.Builder(context)

        val notification = builder.setContentTitle("MusixMatch Notification")
                .setContentText("New Notification for " + SimpleDateFormat("dd MMMM yyyy HH:mm").format(date))
                .setTicker("New Message Alert!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent).build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID)
        }

        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                    CHANNEL_ID,
                    "NotificationDemo",
                    IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notification)
    }
}