package com.debruyckere.florian.mynews.model;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Debruyckère Florian on 22/05/2018.
 */
public class NewsDownload extends AsyncTask<Void,Void,ArrayList<News>> {

    private ArrayList<News> mNewsList = new ArrayList<>();
    private InputStream in;

    public interface Listeners{
        void onPreExecute();
        void doInBackground();
        void onPostExecute(ArrayList<News> news);
    }

    private final WeakReference<Listeners> mCallback;

    public NewsDownload(Listeners pCallback){
        mCallback = new WeakReference<>(pCallback);
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
        String sUrl = "http://api.nytimes.com/svc/search/v1/article?format=json&query=smoking&api-key=1ae7b601c1c7409796be77cce450f631";
        String aUrl = "https://api.nytimes.com/svc/topstories/v2/home.json?api-key=1ae7b601c1c7409796be77cce450f631";
        ArrayList<News> result;

        try{
            URL url = new URL(aUrl);

            HttpURLConnection conn =(HttpURLConnection) url.openConnection();
            in = conn.getInputStream();

            com.debruyckere.florian.mynews.model.JsonParser parser = new com.debruyckere.florian.mynews.model.JsonParser();
            result = parser.JsonParse(in);

            in.close();
        }catch (Exception e){
            Log.e("DOWNLOAD TASK ERROR",e.getMessage());
            result = new ArrayList<>();
            e.printStackTrace();
        }
        Log.i("NEWSDownload",result.get(0).getTitle());

        return result;
    }
}
