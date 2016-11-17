package com.lisa.televisa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.lisa.televisa.model.Article;
import com.lisa.televisa.request.News;
import com.lisa.televisa.utils.Helpers;
import com.lisa.televisa.utils.PollService;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Splash2 extends AppCompatActivity {

    public static final String TAG = Splash2.class.getName();

    public String postID;
    public ImageView appIcon;
    public News newsRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash2);


        postID = null;

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            if(extras.getString("postID") != null)
                postID = extras.getString("postID");
        }

        appIcon = (ImageView)findViewById(R.id.appIcon);

        Thread mSplashThread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        Log.i(TAG,"Init app");

                        wait(3000);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Init Feed Home Breaking News
                Intent intent = new Intent(Splash2.this, MainActivity.class);
                intent.putExtra("postID", postID);
                startActivity(intent);
                finish();
            }
        };
        mSplashThread.start();
    }
}
