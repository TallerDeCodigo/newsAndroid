package com.lisa.televisa;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lisa.televisa.request.News;
import com.lisa.televisa.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hever on 11/10/16.
 */

public class Single extends AppCompatActivity {

    public static final String TAG = Single.class.getName();
    public TextView txtTitle, txtContent, date;
    public ImageView thumbnail;
    public String link = "";
    public Toolbar toolbar;
    public News newsRequest;
    public Constants constants;
    public ProgressBar loading;
    public ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fluid_single);

        txtTitle    = (TextView) findViewById(R.id.txtTitle);
        txtContent  = (TextView) findViewById(R.id.txtContent);
        thumbnail   = (ImageView) findViewById(R.id.imageView2);
        date        = (TextView) findViewById(R.id.txtTime);
        loading     = (ProgressBar) findViewById(R.id.loader);
        scrollView  = (ScrollView) findViewById(R.id.scroll_view);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Onlive.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplication().startActivity(i);

            }
        });

        Bundle extras = getIntent().getExtras();

        String title        = "";
        String content      = "";
        String image        = "";
        String time         = "";
        String postID       = "";
        String home         = "";

        if (extras != null) {
            title   = extras.getString("title");
            content = extras.getString("content");
            image   = extras.getString("image");
            time    = extras.getString("date");
            link    = extras.getString("link");
            home    = extras.getString("home");
            postID  = extras.getString("postID");
        }

        if(postID == "" || postID == null || home == "") {

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat dateformat = new SimpleDateFormat("MMMM dd, yyyy");

            try {
                Date date = format.parse(time);
                time = dateformat.format(date);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            date.setText("Fuente: Noticieros Televisa  |  " + time.substring(0, 1).toUpperCase() + time.substring(1));

            txtTitle.setText(Html.fromHtml(title));

            Spanned result;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                result = Html.fromHtml(content);
            } else {
                result = Html.fromHtml(content);
            }

            txtContent.setText(result);

            Glide.with(getApplicationContext()).load(image).dontAnimate().fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).into(thumbnail);
        }else{
            loading.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
            getPostByID(postID);
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.single, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, link);
                sendIntent.setType("text/plain");
                this.startActivity(Intent.createChooser(sendIntent, "Compartir"));
                return true;

            case android.R.id.home:
                finish();

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void getPostByID(String postID){

        newsRequest = new News(getApplicationContext(), constants.URL_SERVICES + "post/" + postID, new News.NewsListener() {
            @Override
            public void onGetNews(String jsonArticles) {

                try {

                    JSONArray jsonArray = new JSONArray(jsonArticles);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {

                            String content          = jsonArray.getJSONObject(i).getString("post_content");
                            String date_gmt         = jsonArray.getJSONObject(i).getString("post_date_gmt");
                            String excerpt          = jsonArray.getJSONObject(i).getString("post_excerpt");
                            String featured_media   = jsonArray.getJSONObject(i).getString("image");
                            String guid             = "";
                            int id                  = 0;
                            String link             = "";
                            String modified         = "";
                            String modified_gmt     = "";
                            String slug             = "";
                            String title            = jsonArray.getJSONObject(i).getString("post_title");
                            String type             = jsonArray.getJSONObject(i).getString("post_type");
                            String _links           = "";

                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                            SimpleDateFormat dateformat = new SimpleDateFormat("MMMM dd, yyyy");

                            try {
                                Date date = format.parse(date_gmt);
                                date_gmt = dateformat.format(date);
                            } catch (ParseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            date.setText("Fuente: Noticieros Televisa  |  " + date_gmt.substring(0, 1).toUpperCase() + date_gmt.substring(1));

                            txtTitle.setText(Html.fromHtml(title));

                            Spanned result;

                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                result = Html.fromHtml(content);
                            } else {
                                result = Html.fromHtml(content);
                            }

                            txtContent.setText(result);

                            Glide.with(getApplicationContext()).load(featured_media).dontAnimate().fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).into(thumbnail);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        loading.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);

                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onGetNewsFaliure() {

            }
        });
        newsRequest.execute();
    }


}
