package com.debruyckere.florian.mynews.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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
    private String mParamNewsName;
    private Boolean mPersonal;
    private SharedPreferences mPrefs;
    private ArrayList<String> mParam = new ArrayList<>();

    public interface Listeners{
        void onPreExecute();
        void doInBackground();
        void onPostExecute(ArrayList<News> news);
    }

    private final WeakReference<Listeners> mCallback;

    public NewsDownload(Listeners pCallback , String pUrl, Context pContext,Boolean pPersonalChoice){
        mCallback = new WeakReference<>(pCallback);
        mUrl = pUrl;
        mPersonal = pPersonalChoice;

        if(pPersonalChoice){
            mPrefs = pContext.getSharedPreferences("PARAMETER",Context.MODE_PRIVATE);
            //mFilterParameterPass = prefs.getString("FilterParameter","");

            SharedLoader();
        }
    }

    public NewsDownload(Listeners pCallback,String pUrl, String pSearchTerm, ArrayList<Boolean> pChoiceList){
        mCallback = new WeakReference<>(pCallback);
        mParamNewsName = pSearchTerm;
        mUrl = pUrl;
        mPersonal = true;

        int index = 0;
        while(index <pChoiceList.size()){
            if(pChoiceList.get(index)){
                switch (index){
                    case 0:mParam.add("Arts");
                    break;
                    case 1: mParam.add("Business");
                    break;
                    case 2: mParam.add("Politics");
                    break;
                    case 3: mParam.add("Travels");
                    break;
                    case 4: mParam.add("Sports");
                    break;
                    case 5: mParam.add("Climate");
                }
            }

            index++;
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

            //JsonParser parser = new JsonParser(mFilterParameterPass);
            JsonParser parser = new JsonParser(mParam,mPersonal,mParamNewsName);
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

    public void SharedLoader(){

        try{
            int index = 0;

            mParamNewsName = mPrefs.getString("paramSearch",null);
            while(index <= 6){
                if(mPrefs.getBoolean("paramValue"+index,false)){
                    mParam.add(mPrefs.getString("paramName"+index,null));
                }

                index++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
