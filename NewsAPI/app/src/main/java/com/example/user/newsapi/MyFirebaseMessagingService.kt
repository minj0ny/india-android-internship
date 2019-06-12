package com.example.user.newsapi

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.example.user.newsapi.network.api.RetroBaseApiService
import com.example.user.newsapi.network.dto.Articles
import com.example.user.newsapi.ui.component.detail.DetailedActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_news.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class MyFirebaseMessagingService : FirebaseMessagingService() {

    lateinit var retrofit: Retrofit
    lateinit var apiService: RetroBaseApiService
    lateinit var country: String
    lateinit var category: String
    val apikey = "810322576cc748a2a15e633256642ba2"

    var localArticles: List<Articles> = arrayListOf()

    private val TAG = "FirebaseService"

    private var index = 0
    /**
     * FirebaseInstanceIdService is deprecated.
     * this is new on firebase-messaging:17.1.0
     */
    override fun onNewToken(token: String?) {
        Log.d(TAG, "new Token: $token")
    }

    /**
     * this method will be triggered every time there is new FCM Message.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.from)

        if (remoteMessage.data != null) {
            Log.d(TAG, "Notification Message Data: ${remoteMessage.notification?.body}")

            retrofit = Retrofit.Builder().baseUrl("https://newsapi.org/").addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
            apiService = retrofit.create(RetroBaseApiService::class.java)
            country = "Us"
            category = "Sports"

            getnews(remoteMessage.notification?.body)

            Thread.sleep(3000);

            sendNotification(remoteMessage.notification?.body)

        }
    }

    private fun getnews(body: String?) {

        val localCategory: String? = if ("Headline" == category) {
            null
        } else {
            category
        }
        apiService.getResponse(country, localCategory, apikey).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                { result ->
                    localArticles = result.articles
                    Log.e("사이즈","${localArticles.size}")
                    for (index in 0..localArticles.size) {
                        if (localArticles.get(index).title == body) {
                            this.index = index
                        }
                    }
                }, { e ->
        })

    }

    private fun sendNotification(body: String?) {

        val CHANNEL_ID = "News app"
        val CHANNEL_NAME = "Smart News"
        val description = "This is Smart news"
        val importance = NotificationManager.IMPORTANCE_HIGH

        val intent = Intent(this, DetailedActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("article", localArticles[index])
        }

        var notificationManager: NotificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            channel.description = description
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.setShowBadge(false)
            notificationManager.createNotificationChannel(channel)
        }

        var pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        var notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Notification")
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(notificationSound)
                .setContentIntent(pendingIntent)

        notificationManager.notify(0, notificationBuilder.build())
    }
}

