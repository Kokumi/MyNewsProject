package com.debruyckere.florian.mynews.model;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.debruyckere.florian.mynews.R;

import java.util.ArrayList;

/**
 * Created by Debruyckère Florian on 26/09/2018.
 */
public class AlarmReceiver extends BroadcastReceiver implements NewsDownload.Listeners{

    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("ALARM","I'M ACTIVE");
        mContext = context;
        new NewsDownload(this,"https://api.nytimes.com/svc/topstories/v2/home.json?api-key=1ae7b601c1c7409796be77cce450f631",
                context,true)
                .execute();
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void doInBackground() {

    }

    @Override
    public void onPostExecute(ArrayList<News> news) {
        if(news.size()!= 0){
            //launch notification
            Log.i("ALARM","there new news");

            NotificationManagerCompat notiManager = NotificationManagerCompat.from(mContext);
            notiManager.notify(0,NotificationConfiguration().build());
        }
    }

    /**
     * create notification
     * @return notification
     */
    public NotificationCompat.Builder NotificationConfiguration(){

        return new NotificationCompat.Builder(mContext,"MyNewsChannel")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("MyNews new news")
                .setContentText("A new news has arrived")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }
}
