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
            mPersonal = false;
        }
    }

    /**
     * Load more complete Search Parameters
     * @param pParam list of search parameters
     * @param pPersonal if search parameters must be consider
     * @param pParamNewsName parameter to search a word
     */
    public JsonParser(ArrayList<String> pParam, Boolean pPersonal, String pParamNewsName){
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
        Log.i("JSONPARSER","PARSING");

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
            Boolean hasImage=true;


            while (reader.hasNext()){
                String name = reader.nextName();
                Log.i("JSONPARSER","line name: "+name);
                switch (name){
                    case "title": newTitle=reader.nextString();
                        break;
                    case"headline":
                        reader.beginObject();
                        Boolean out = true;

                        while(out){
                            try{
                                name = reader.nextName();
                                switch (name){
                                    case "main": newTitle = reader.nextString();
                                        break;
                                        default: reader.skipValue();
                                        break;
                                }

                            }
                            catch (IllegalStateException e){
                                reader.endObject();
                                out = false;
                            }
                        }

                        break;
                    case "url": newUrl = reader.nextString();
                        break;
                    case "web_url": newUrl = reader.nextString();
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
                    case "pub_date":
                        xmlDate = reader.nextString();
                        format = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
                        try{
                            newDate = format.parse(xmlDate);
                        }catch (ParseException e){
                            Log.e("DATE PARSING",e.getMessage());
                        }
                        Log.i("NEWS","DATE discover");
                        break;
                    case "section":newTheme = reader.nextString();
                        break;
                    case "subsection": newSubTheme = reader.nextString();
                        break;
                    case "news_desk": newTheme = reader.nextString();
                         if(reader.nextName().equals("section_name")){
                            newSubTheme = reader.nextString();
                         }
                         else {
                            Log.i("JSONTHEME","no sub theme");
                            reader.skipValue();
                         }
                        break;
                    case "multimedia":
                        // take the first image
                        hasImage = true;
                        out = true;

                        try {
                            reader.beginArray();
                            reader.beginObject();
                        }catch (IllegalStateException e){
                            reader.endArray();
                            out =false;
                            hasImage =false;
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
                                    case "crop_name":
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
                    case "short_url":reader.skipValue();
                                    reader.endObject();
                                    try {
                                        reader.beginObject();
                                    }catch (IllegalStateException e){
                                        Log.i("JSON PARSER","end of News");
                                        reader.endArray();
                                    }
                        break;
                    case "uri": reader.skipValue();
                                reader.endObject();
                                try{
                                    reader.beginObject();
                                }catch (IllegalStateException e){
                                    reader.endArray();
                                }
                        break;

                    case "response":reader.beginObject();
                        break;
                    case "docs": reader.beginArray();
                                reader.beginObject();
                        break;


                    default:
                        reader.skipValue();
                        break;
                }
                try {

                    // save the news only if there all needed data
                    if(!newTitle.equals("") & !newTheme.equals("") &
                            !newUrl.equals("") & (!newThumbnail.equals("") | !hasImage) ) {   //& !newSubTheme.equals("")
                        Log.d("JSON PARSER","image: "+hasImage);
                        if(mPersonal ){

                            if(mParamFilter.size() != 0) {
                                //verify if there a theme as been choose

                                int index = 0;
                                String filterTheme=newTheme+newSubTheme;
                                while (mParamFilter.size() > index) {
                                    if (filterTheme.contains(mParamFilter.get(index)) & newTitle.contains(mParamNewsName)) {

                                        theNew = new News(newTitle, newTheme + newSubTheme, newDate, newUrl, newThumbnail);
                                        result.add(theNew);
                                        break;
                                    }

                                    index++;
                                }
                            }else{
                                //if the user doesn't choose a theme

                                if (newTitle.contains(mParamNewsName)) {

                                    theNew = new News(newTitle, newTheme + newSubTheme, newDate, newUrl, newThumbnail);
                                    result.add(theNew);
                                    break;
                                }
                            }

                        }else{
                            // if the search parameter don't have to be consider
                            theNew = new News(newTitle, newTheme + newSubTheme, newDate, newUrl, newThumbnail);
                            result.add(theNew);
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