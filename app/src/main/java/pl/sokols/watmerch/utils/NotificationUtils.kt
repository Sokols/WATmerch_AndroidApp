package pl.sokols.watmerch.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import pl.sokols.watmerch.R
import pl.sokols.watmerch.ui.MainActivity

class NotificationUtils {

    companion object {
        fun displayNotification(context: Context, title: String, message: String) {
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val intent = Intent(context, MainActivity::class.java)

            intent.action = Intent.ACTION_MAIN
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
            val notificationChannel = NotificationChannel(
                context.getString(R.string.channel_id),
                context.getString(R.string.app_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationManager.createNotificationChannel(notificationChannel)
            val builder = Notification.Builder(context, context.getString(R.string.channel_id))
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_logo)
            notificationManager.notify(1234, builder.build())
        }
    }
}