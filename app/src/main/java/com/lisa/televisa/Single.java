package com.lisa.televisa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lisa.televisa.model.Article;

import org.w3c.dom.Text;

/**
 * Created by hever on 11/10/16.
 */

public class Single extends AppCompatActivity {

    public TextView txtTitle, txtContent;
    public ImageView thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_single);

        txtTitle    = (TextView) findViewById(R.id.txtTitle);
        txtContent  = (TextView) findViewById(R.id.txtContent);
        thumbnail   = (ImageView) findViewById(R.id.imageView2);

        Bundle extras = getIntent().getExtras();

        String title    = "";
        String content  = "";

        if (extras != null) {
            title   = extras.getString("title");
            content = extras.getString("content");
        }

        txtTitle.setText(Html.fromHtml(title));
        txtContent.setText(Html.fromHtml(content));

        Glide.with(getApplicationContext()).load("http://televisa.news/wp-content/uploads/2016/11/clinton_preventaja.jpg").into(thumbnail);


    }
}
