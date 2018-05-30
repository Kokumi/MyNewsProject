package com.debruyckere.florian.mynews.model;

import android.accounts.NetworkErrorException;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Debruyckère Florian on 28/05/2018.
 */
public class JsonParser{
    public ArrayList<News> JsonParse(InputStream pStream) throws IOException {
        ArrayList<News> result = new ArrayList<>();
        News theNew;

        JsonReader reader = new JsonReader(new InputStreamReader(pStream,"UTF-8"));
        reader.beginObject();
        while(reader.hasNext()){
            String newTitle="";
            String newUrl="";
            String newBody="";
            String newTheme="";
            String newThumbnail = "";
            Date newDate=new Date();

            //reader.be();
            while (reader.hasNext()){
                String name = reader.nextName();
                //String name2 = reader.nextString();

                switch (name){
                    case "title": newTitle=reader.nextString();
                        break;
                    case "url": newUrl = reader.nextString();
                        break;
                    case "date":
                        String xmlDate = reader.nextString();
                        SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat.getDateInstance();    //new SimpleDateFormat("yyyyMMdd")
                        try{
                            newDate = format.parse(xmlDate);
                        }catch (ParseException e){
                            Log.e("DATE PARSING",e.getMessage());
                        }
                        break;
                    case "body": newBody = reader.nextString();
                        break;
                    //thumbnail_standard
                    case "thumbnail_standard": newThumbnail = reader.nextString();
                        break;
                    case "section":newTheme = reader.nextString();
                        break;
                    case "subsection": newTheme += reader.nextString();
                        break;
                    case "multimedia":
                        theNew = new News(newTitle,newTheme,newDate,newUrl,newThumbnail);
                        result.add(theNew);
                        Log.i("JSON PARSER","add new: "+newTitle);
                        reader.skipValue();
                        break;
                    case "results":reader.beginArray();
                                    reader.beginObject();
                        break;
                    default:reader.skipValue();
                        break;
                }

            }

        }

        return result;
    }
}
