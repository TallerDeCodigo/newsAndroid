package com.lisa.televisa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
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
        String image    = "";

        if (extras != null) {
            title   = extras.getString("title");
            content = extras.getString("content");
            image   = extras.getString("image");
        }

        txtTitle.setText(Html.fromHtml(title));

        Spanned result;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(content,Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(content);
        }

        txtContent.setText(result);




        Glide.with(getApplicationContext()).load(image).into(thumbnail);


    }
}
