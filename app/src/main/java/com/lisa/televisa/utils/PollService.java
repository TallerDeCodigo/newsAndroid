package com.lisa.televisa.utils;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

/**
 * Created by hever on 11/14/16.
 */

public class PollService extends IntentService {

    private static final String TAG = "Pool Service";


    public static Intent newIntent(Context context){

        return newIntent(context);

    }

    public PollService(){
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent){

        if(!isNetworkAvailableAndConnected()){
            return;
        }

        Log.i(TAG,"Received an Internet" + intent);
    }


    public boolean isNetworkAvailableAndConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable && cm.getActiveNetworkInfo().isConnected();

        return isNetworkConnected;
    }

}
