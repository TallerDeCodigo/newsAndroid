package com.lisa.televisa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import com.lisa.televisa.utils.PollService;

public class Splash2 extends AppCompatActivity {

    public static final String TAG = Splash2.class.getName();

    public String postID;
    public ImageView appIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postID = null;

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            Log.d(TAG, extras.toString());
            Log.d(TAG, extras.getString("postID"));
            postID = extras.getString("postID");
        }

        setContentView(R.layout.activity_splash2);

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
