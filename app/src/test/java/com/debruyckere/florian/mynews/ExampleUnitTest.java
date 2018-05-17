package com.debruyckere.florian.mynews;

import com.debruyckere.florian.mynews.model.News;
import com.debruyckere.florian.mynews.model.NewsAdapter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void RecycleTest(){
        News testNews = new News("Android P real name is Android Pancake",
                "Technologie/Android",new Date(),"nep","Place");
        ArrayList<News> testArray = new ArrayList<>();
        testArray.add(testNews);

        NewsAdapter testAda= new NewsAdapter(testArray);
    }
}