package com.lisa.televisa.request;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hever on 10/24/16.
 */

public class News extends AsyncTask<String, String, String> {

    public static final String TAG          = News.class.getName();
    public static final MediaType TypeJSON  = MediaType.parse("application/json; charset=utf-8");
    private final OkHttpClient client       = new OkHttpClient();

    private NewsListener listener;
    private Context context;
    private String url;

    public News(String url, NewsListener newsListener){
        this.url = url;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(String... values){
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... values){

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("content-type","application/json")
                .build();

        Response response = null;

        JSONObject jsonObject;

        try {

            response = client.newCall(request).execute();

            if(!response.isSuccessful()){ throw new IOException("Unexpected code");}

            String jsonData;

            try {

                jsonData = response.body().string();

            }catch (IOException e){

                Log.e(TAG, e.getMessage());
                e.printStackTrace();
                return null;

            }

            Log.i(TAG, jsonData);

            try {

                JSONArray json = new JSONArray(jsonData);
                listener.onGetNews(json);
            }catch (JSONException e){
                e.printStackTrace();
            }


            return jsonData;

        }catch (IOException e){
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        return null;
    }


    public interface NewsListener{

        void onGetNews(JSONArray jsonArray);
        void onGetNewsFaliure();
    }

}
