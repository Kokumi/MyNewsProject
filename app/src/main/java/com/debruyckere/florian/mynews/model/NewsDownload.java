package com.debruyckere.florian.mynews.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Debruyckère Florian on 22/05/2018.
 */
public class NewsDownload extends AsyncTask<Void,Void,ArrayList<News>> {

    private String mUrl;
    private String mFilterParameterPass="";

    public interface Listeners{
        void onPreExecute();
        void doInBackground();
        void onPostExecute(ArrayList<News> news);
    }

    private final WeakReference<Listeners> mCallback;

    public NewsDownload(Listeners pCallback , String pUrl, Context pContext,Boolean pPersonalChoice){
        mCallback = new WeakReference<>(pCallback);
        mUrl = pUrl;

        if(pPersonalChoice){
            //SharedPreferences prefs = pContext.getSharedPreferences("PARAMETER",Context.MODE_PRIVATE);
            //mFilterParameterPass = prefs.getString("FilterParameter","");

            //TODO: Create Parameter option
            mFilterParameterPass = "Politics";   //for test
        }
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mCallback.get().onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<News> news) {
        super.onPostExecute(news);
        /*try {
            in.close();
        }catch (IOException e){
            e.printStackTrace();
        }*/

        mCallback.get().onPostExecute(news);
    }

    @Override
    protected ArrayList<News> doInBackground(Void... voids) {
        mCallback.get().doInBackground();
        ArrayList<News> result;
        InputStream in;

        try{
            URL url = new URL(mUrl);

            HttpURLConnection conn =(HttpURLConnection) url.openConnection();
            in = conn.getInputStream();

            JsonParser parser = new JsonParser(mFilterParameterPass);
            result = parser.JsonParse(in);

            in.close();
        }catch (Exception e){
            Log.e("DOWNLOAD TASK ERROR",e.getMessage());
            result = new ArrayList<>();
            e.printStackTrace();
        }
        if(result.size()!=0){Log.i("NEWSDownload","news saved: "+result.size());}
        else{
            Log.d("NEWS DOWNLOAD","No news saved");
        }

        return result;
    }
}
