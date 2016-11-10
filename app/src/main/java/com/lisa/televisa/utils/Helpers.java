package com.lisa.televisa.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.lisa.televisa.MainActivity;
import com.lisa.televisa.model.Article;
import com.lisa.televisa.request.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by hever on 11/10/16.
 */

public class Helpers {

    public static final String TAG = Helpers.class.getName();
    public News newsRequest;
    public Context context;
    public String source_url = "";
    public List<Article> articleList;

    public Helpers(){}

    public String getImageJSON(String Url){

        String url = "https://televisa.news/wp-json/wp/v2/media/" + Url;

        newsRequest = new News(context, url, new News.NewsListener(){

            @Override
            public void onGetNews(String json) {

                try {

                    JSONObject jsonObject = new JSONObject(json);

                    try {

                        source_url   = jsonObject.getString("source_url");
                        Log.d(TAG, source_url);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }catch (JSONException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onGetNewsFaliure() {

                Toast.makeText(context, "Error en la carga de imágen", Toast.LENGTH_SHORT).show();

            }
        });

        newsRequest.execute();

        return source_url;
    }

    public List<Article> getArticlesByUrl(String URL){

        newsRequest = new News(context, URL, new News.NewsListener() {

            @Override
            public void onGetNews(String json) {

                try {

                    JSONArray jsonArray = new JSONArray(json);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {

                            String content          = jsonArray.getJSONObject(i).getJSONObject("content").getString("rendered");
                            String date_gmt         = jsonArray.getJSONObject(i).getString("date_gmt");
                            String excerpt          = jsonArray.getJSONObject(i).getJSONObject("excerpt").getString("rendered");
                            String featured_media   = jsonArray.getJSONObject(i).getString("featured_media");
                            String guid             = jsonArray.getJSONObject(i).getString("guid");
                            int id                  = 0;
                            String link             = jsonArray.getJSONObject(i).getString("link");
                            String modified         = jsonArray.getJSONObject(i).getString("modified");
                            String modified_gmt     = jsonArray.getJSONObject(i).getString("modified_gmt");
                            String slug             = jsonArray.getJSONObject(i).getString("slug");
                            String title            = jsonArray.getJSONObject(i).getJSONObject("title").getString("rendered");

                            String type             = jsonArray.getJSONObject(i).getString("type");
                            String _links           = jsonArray.getJSONObject(i).getString("_links");

                            //featured_media          = helpers.getImageJSON(featured_media);

                            Article n = new Article(content, date_gmt, excerpt, featured_media, guid, id, link, modified, modified_gmt, slug, title, type, _links);
                            articleList.add(n);

                            Log.d(TAG, featured_media);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onGetNewsFaliure() {

                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        newsRequest.execute();

        return articleList;
    }
}
