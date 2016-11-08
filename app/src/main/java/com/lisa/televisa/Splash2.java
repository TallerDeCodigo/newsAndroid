package com.lisa.televisa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.lisa.televisa.request.News;

import org.json.JSONArray;
import org.json.JSONObject;

public class Splash2 extends AppCompatActivity {

    public News newsRequest;
    public static final String TAG = Splash2.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
        Thread mSplashThread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {

                        newsRequest = new News("https://www.televisa.news/wp-json/wp/v2/breaking", new News.NewsListener() {

                            @Override
                            public void onGetNews(JSONArray jsonArray) {

                                Log.d(TAG, jsonArray.toString());

                            }

                            @Override
                            public void onGetNewsFaliure() {

                            }
                        });

                        newsRequest.execute();

                        wait(3000);

                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent;

                intent = new Intent(Splash2.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        mSplashThread.start();
    }
}
