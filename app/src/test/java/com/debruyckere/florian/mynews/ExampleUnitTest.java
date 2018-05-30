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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
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
        JsonParser parser = new JsonParser();


        try{
            FileInputStream fIS= new FileInputStream(new File(
                    //Environment.getExternalStorageDirectory().getAbsolutePath()+ "/res/raw/rss.json"));
            "android.resource://res/raw/rss"));


            toTest = parser.JsonParse((InputStream) fIS);
        }catch (IOException ex){
           // Log.e("TESTJSON",ex.getMessage());
            System.out.println("Error TEST "+ex.getMessage());
        }
        News newsTest = toTest.get(0);

        assertThat(newsTest.getTitle(), is("Israel, ‘Spider-Man,’ N.B.A. Finals: Your Tuesday Briefing"));
    }
}