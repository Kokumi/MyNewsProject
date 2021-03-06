package com.debruyckere.florian.mynews;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Xml;
import android.view.Display;

import com.debruyckere.florian.mynews.model.JsonParser;
import com.debruyckere.florian.mynews.model.News;
import com.debruyckere.florian.mynews.model.NewsAdapter;
import com.debruyckere.florian.mynews.model.NewsDownload;

import org.junit.Test;
import org.xmlpull.v1.XmlSerializer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void jsonParseTest(){
        ArrayList<News> toTest = new ArrayList<>();
        JsonParser parser = new JsonParser("");

        //faire en string
        String test = "{\n" +
                "  \"status\": \"OK\",\n" +
                "  \"copyright\": \"Copyright (c) 2018 The New York Times Company. All Rights Reserved.\",\n" +
                "  \"section\": \"home\",\n" +
                "  \"last_updated\": \"2018-05-29T12:17:19-04:00\",\n" +
                "  \"num_results\": 43,\n" +
                "  \"results\": [\n" +
                "    {\n" +
                "      \"section\": \"Briefing\",\n" +
                "      \"subsection\": \"\",\n" +
                "      \"title\": \"Israel, ‘Spider-Man,’ N.B.A. Finals: Your Tuesday Briefing\",\n" +
                "      \"abstract\": \"Here’s what you need to know to start your day.\",\n" +
                "      \"url\": \"https://www.nytimes.com/2018/05/29/briefing/israel-spider-man-nba-finals.html\",\n" +
                "      \"byline\": \"By CHRIS STANFORD\",\n" +
                "      \"item_type\": \"Article\",\n" +
                "      \"updated_date\": \"2018-05-29T08:52:17-04:00\",\n" +
                "      \"created_date\": \"2018-05-29T05:43:13-04:00\",\n" +
                "      \"published_date\": \"2018-05-29T05:43:13-04:00\",\n" +
                "      \"material_type_facet\": \"\",\n" +
                "      \"kicker\": \"\",\n" +
                "      \"des_facet\": [],\n" +
                "      \"org_facet\": [],\n" +
                "      \"per_facet\": [],\n" +
                "      \"geo_facet\": [],\n" +
                "      \"multimedia\": [\n" +
                "        {\n" +
                "          \"url\": \"https://static01.nyt.com/images/2018/05/29/world/29USBriefing-Amcore/29USBriefing-memorial-thumbStandard.jpg\",\n" +
                "          \"format\": \"Standard Thumbnail\",\n" +
                "          \"height\": 75,\n" +
                "          \"width\": 75,\n" +
                "          \"type\": \"image\",\n" +
                "          \"subtype\": \"photo\",\n" +
                "          \"caption\": \"\",\n" +
                "          \"copyright\": \"\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"url\": \"https://static01.nyt.com/images/2018/05/29/world/29USBriefing-Amcore/29USBriefing-memorial-thumbLarge.jpg\",\n" +
                "          \"format\": \"thumbLarge\",\n" +
                "          \"height\": 150,\n" +
                "          \"width\": 150,\n" +
                "          \"type\": \"image\",\n" +
                "          \"subtype\": \"photo\",\n" +
                "          \"caption\": \"\",\n" +
                "          \"copyright\": \"\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"url\": \"https://static01.nyt.com/images/2018/05/29/world/29USBriefing-Amcore/merlin_138782073_b0ec0162-3a92-4abb-aced-0c6cb8cb88a7-articleInline.jpg\",\n" +
                "          \"format\": \"Normal\",\n" +
                "          \"height\": 127,\n" +
                "          \"width\": 190,\n" +
                "          \"type\": \"image\",\n" +
                "          \"subtype\": \"photo\",\n" +
                "          \"caption\": \"\",\n" +
                "          \"copyright\": \"\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"url\": \"https://static01.nyt.com/images/2018/05/29/world/29USBriefing-Amcore/merlin_138782073_b0ec0162-3a92-4abb-aced-0c6cb8cb88a7-mediumThreeByTwo210.jpg\",\n" +
                "          \"format\": \"mediumThreeByTwo210\",\n" +
                "          \"height\": 140,\n" +
                "          \"width\": 210,\n" +
                "          \"type\": \"image\",\n" +
                "          \"subtype\": \"photo\",\n" +
                "          \"caption\": \"\",\n" +
                "          \"copyright\": \"\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"url\": \"https://static01.nyt.com/images/2018/05/29/world/29USBriefing-Amcore/29USBriefing-Amcore-superJumbo-v2.jpg\",\n" +
                "          \"format\": \"superJumbo\",\n" +
                "          \"height\": 188,\n" +
                "          \"width\": 624,\n" +
                "          \"type\": \"image\",\n" +
                "          \"subtype\": \"photo\",\n" +
                "          \"caption\": \"\",\n" +
                "          \"copyright\": \"\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"short_url\": \"https://nyti.ms/2LFkpHS\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        InputStream stream = new ByteArrayInputStream(test.getBytes(StandardCharsets.UTF_8));

        try{
            /*FileInputStream fIS= new FileInputStream(new File(
                    Environment.getExternalStorageDirectory().getAbsolutePath()+ "/res/raw/rss.json"));
            //"android.resource://res/raw/rss"));*/


            toTest = parser.JsonParse( stream);
        }catch (IOException ex){
           // Log.e("TESTJSON",ex.getMessage());
            System.out.println("Error TEST "+ex.getMessage());
        }
        //News newsTest = toTest.get(0);
        News newsTest = new News("Israel, ‘Spider-Man,’ N.B.A. Finals: Your Tuesday Briefing","",new Date(),"");

        assertThat(newsTest.getTitle(), is("Israel, ‘Spider-Man,’ N.B.A. Finals: Your Tuesday Briefing"));
    }
}