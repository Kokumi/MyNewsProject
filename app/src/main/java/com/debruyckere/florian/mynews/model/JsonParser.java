package com.debruyckere.florian.mynews.model;

import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Debruyckère Florian on 28/05/2018.
 */
public class JsonParser{

    private Boolean mPersonal=false;
    private ArrayList<String> mParamFilter = new ArrayList<>();
    private String mParamNewsName;

    /**
     * Load Search Parameter
     * @param pFilterParameter search parameters
     */
    public JsonParser(String pFilterParameter){
        if(!pFilterParameter.equals("")){
            mPersonal = true;
        }
    }

    /**
     * Load more complete Search Parameters
     * @param pParam list of search parameters
     * @param pPersonal if search parameters must be consider
     * @param pParamNewsName parameter to search a word
     */
    public JsonParser(ArrayList<String> pParam, Boolean pPersonal, String pParamNewsName){
        Log.i("JSON PARSER","Constructeur with ");
        mParamFilter = pParam;
        mPersonal = pPersonal;
        mParamNewsName = pParamNewsName;
        try{
            Log.i("Json Parser create",mParamNewsName);
        }catch (NullPointerException e){
            Log.i("Json parser Create","no paramName");
        }

    }

    public ArrayList<News> JsonParse(InputStream pStream) throws IOException {
        ArrayList<News> result = new ArrayList<>();
        News theNew;

        JsonReader reader = new JsonReader(new InputStreamReader(pStream,"UTF-8"));
        reader.beginObject();
        while(reader.hasNext()){

            // to avoid NullPointerException
            String newTitle="";
            String newUrl="";
            String newTheme="";
            String newSubTheme="";
            String newThumbnail = "";
            Date newDate=new Date();

            while (reader.hasNext()){
                String name = reader.nextName();


                switch (name){
                    case "title": newTitle=reader.nextString();
                        break;
                    case "url": newUrl = reader.nextString();
                        break;
                    case "published_date":
                        String xmlDate = reader.nextString();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
                        try{
                            newDate = format.parse(xmlDate);
                        }catch (ParseException e){
                            Log.e("DATE PARSING",e.getMessage());
                        }
                        break;
                    case "section":newTheme = reader.nextString();
                        break;
                    case "subsection": newSubTheme = reader.nextString();
                        break;
                    case "multimedia":
                        // take the first image
                        Boolean out = true;

                        try {
                            reader.beginArray();
                            reader.beginObject();
                        }catch (IllegalStateException e){
                            out =false;
                        }
                        while(out){
                            try {
                                name = reader.nextName();
                                switch (name) {
                                    case "url":
                                        newThumbnail = reader.nextString();
                                        break;
                                    case "copyright":
                                        reader.skipValue();
                                        reader.endObject();
                                        reader.beginObject();
                                        break;
                                        default: reader.skipValue();
                                        break;
                                }
                            }catch (IllegalStateException e){
                                reader.endArray();
                                out = false;
                            }
                        }
                        break;
                    case "media":
                        // same thing as multimedia
                        out = true;
                        try {
                            reader.beginArray();
                            reader.beginObject();
                        }catch (IllegalStateException e){
                            out = false;
                        }
                        while(out){
                            try{
                                name = reader.nextName();
                                switch (name){
                                    default:reader.skipValue();
                                    break;
                                    case "type":
                                        if (!reader.nextString().equals("image")) {
                                            //go out if it's not an image
                                            out = false;
                                        }
                                    break;
                                    case "media-metadata":
                                        reader.beginArray();
                                        reader.beginObject();
                                        break;
                                    case "url": newThumbnail = reader.nextString();
                                    break;
                                    case "width":           //last name of the object
                                        reader.skipValue();
                                        reader.endObject();
                                        try {
                                            reader.beginObject();
                                        }catch (IllegalStateException e){
                                            reader.endArray();
                                            reader.endObject();
                                        }
                                        break;

                                }

                            }catch (IllegalStateException e){
                                reader.endArray();
                                reader.endObject();
                                try{
                                    reader.beginObject();
                                }catch (IllegalStateException eS){
                                    reader.endArray();
                                }
                                out = false;
                            }
                        }

                        break;
                    case "results":reader.beginArray();
                                    reader.beginObject();
                        break;
                    case "short_url":Log.i("JSON PARSER","Change NEW");
                                    reader.skipValue();
                                    reader.endObject();
                                    try {
                                        reader.beginObject();
                                    }catch (IllegalStateException e){
                                        Log.i("JSON PARSER","end of News");
                                        reader.endArray();
                                    }
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
                try {
                    // save the news only if there all needed data
                    if(!newTitle.equals("") & !newTheme.equals("") &
                            !newUrl.equals("") & !newThumbnail.equals("") ) {   //& !newSubTheme.equals("")

                        if(mPersonal ){

                            if(mParamFilter.size() != 0) {
                                //verify if there a theme as been choose

                                int index = 0;
                                String filterTheme=newTheme+newSubTheme;
                                while (mParamFilter.size() > index) {
                                    if (filterTheme.contains(mParamFilter.get(index)) & newTitle.contains(mParamNewsName)) {

                                        theNew = new News(newTitle, newTheme + newSubTheme, newDate, newUrl, newThumbnail);
                                        result.add(theNew);
                                        Log.i("JSON PARSER", "add new: " + newTitle);
                                        break;
                                    }

                                    index++;
                                }
                            }else{
                                //if the user doesn't choose a theme

                                if (newTitle.contains(mParamNewsName)) {

                                    theNew = new News(newTitle, newTheme + newSubTheme, newDate, newUrl, newThumbnail);
                                    result.add(theNew);
                                    Log.i("JSON PARSER", "add new: " + newTitle);
                                    break;
                                }
                            }

                        }else{
                            // if the search parameter don't have to be consider
                            theNew = new News(newTitle, newTheme + newSubTheme, newDate, newUrl, newThumbnail);
                            result.add(theNew);
                            Log.i("JSON PARSER", "add new: " + newTitle);
                        }

                        // empty the current news
                        newTitle = "";
                        newTheme = "";
                        newSubTheme="";
                        newUrl = "";
                        newThumbnail = "";
                        newDate = new Date();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }

        return result;
    }
}