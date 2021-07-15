package com.colemichaels.notificationdemo

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Icon
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.colemichaels.notificationdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),
    View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    // Creates icon for action item in notification
    private lateinit var icon: Icon
    private lateinit var resultIntent: Intent
    // PendingIntent is a type of intent which is sent to other applications to grant permissions to perform intent in the future.
    private lateinit var pendingIntent: PendingIntent

    private var notificationManager: NotificationManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        icon = Icon.createWithResource(this, android.R.drawable.ic_dialog_info)
        resultIntent = Intent(this, ResultActivity::class.java)
        pendingIntent = PendingIntent.getActivity(
            this,
            0,
            resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        createNotificationChannel(
            "com.colemichaels.notificationdemo.news",
            "NotificationDemo News",
            "Example News Channel"
        )

        binding.notifySingle.setOnClickListener(this)
        binding.notifyGroup.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.notifySingle.id -> singleNotification()
            binding.notifyGroup.id -> groupNotification()
        }
    }

    private fun createNotificationChannel(
        id: String,
        name: String,
        notificationDescription: String
    ) {
        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(id, name, importance).apply {
            description = notificationDescription
            enableLights(true)
            lightColor = Color.RED
            enableVibration(true)
            vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        }

        notificationManager?.createNotificationChannel(channel)
    }

    private fun singleNotification() {
        // Sets action item for notification which triggers pendingIntent
        val action: Notification.Action = Notification.Action.Builder(icon, "Open", pendingIntent).build()

        val notification = Notification.Builder(this@MainActivity, CHANNEL_ID)
            .setContentTitle("Example Notification")
            .setContentText("This is an example notification.")
            .setSmallIcon(icon)
            .setChannelId(CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setActions(action)
            .build()

        notificationManager?.notify(SINGLE_NOTIFICATION_ID, notification)
    }

    private fun groupNotification() {
        // If an application issues a lot of notifications to keep user interest group notifications
        // This keeps application installed an issues user with updates without spamming them
        val builderSummary: Notification.Builder = Notification.Builder(this, CHANNEL_ID)
            .setSmallIcon(icon)
            .setContentTitle("A BundleExample")
            .setContentText("You have 3 new messages")
            .setGroup(GROUP_NOTIFICATION_KEY)
            .setGroupSummary(true)

        val builder1: Notification.Builder = Notification.Builder(this, CHANNEL_ID)
            .setSmallIcon(icon)
            .setContentTitle("New Message")
            .setContentText("You have a new message from Kassidy")
            .setGroup(GROUP_NOTIFICATION_KEY)

        val builder2: Notification.Builder = Notification.Builder(this, CHANNEL_ID)
            .setSmallIcon(icon)
            .setContentTitle("New Message")
            .setContentText("You have a new message from Caitlyn")
            .setGroup(GROUP_NOTIFICATION_KEY)

        val builder3: Notification.Builder = Notification.Builder(this, CHANNEL_ID)
            .setSmallIcon(icon)
            .setContentTitle("New Message")
            .setContentText("You have a new message from Jason")
            .setGroup(GROUP_NOTIFICATION_KEY)

        notificationManager?.notify(NOTIFICATION_ID_1, builder1.build())
        notificationManager?.notify(NOTIFICATION_ID_2, builder2.build())
        notificationManager?.notify(NOTIFICATION_ID_3, builder3.build())
        notificationManager?.notify(NOTIFICATION_ID_0, builderSummary.build())
    }

    companion object {
        const val CHANNEL_ID = "com.colemichaels.notificationdemo.news"
        const val GROUP_NOTIFICATION_KEY = "group_notification_key"
        const val SINGLE_NOTIFICATION_ID = 200
        const val NOTIFICATION_ID_0 = 100
        const val NOTIFICATION_ID_1 = 101
        const val NOTIFICATION_ID_2 = 102
        const val NOTIFICATION_ID_3 = 103
    }
}