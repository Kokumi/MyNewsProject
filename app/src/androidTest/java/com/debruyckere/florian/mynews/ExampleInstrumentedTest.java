package com.debruyckere.florian.mynews;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.debruyckere.florian.mynews.model.News;
import com.debruyckere.florian.mynews.model.NewsDownload;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void DownloadTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        final ArrayList<News> toTest = new ArrayList<>();
        final Object sync= new Object();

        new NewsDownload(new NewsDownload.Listeners() {
            @Override
            public void onPreExecute() {
                Log.i("TEST","Inside entrance");
            }

            @Override
            public void doInBackground() {
                Log.i("TEST","Inside deep");
            }

            @Override
            public void onPostExecute(ArrayList<News> news) {
                Log.i("TEST","Inside out");
                toTest.addAll(news);
                synchronized (sync){
                    sync.notify();
                }
            }
        },"https://api.nytimes.com/svc/topstories/v2/home.json?api-key=1ae7b601c1c7409796be77cce450f631"
                ,appContext,false)
                .execute();

        synchronized (sync){
            try {
                sync.wait();
            }catch (InterruptedException e){
                Log.i("TEST","fail");
            }

        }
        assertTrue(toTest.size()!=0);
    }
}
