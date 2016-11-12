package com.lisa.televisa;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lisa.televisa.model.Article;
import com.lisa.televisa.seccions.BreakingNews;
import com.lisa.televisa.seccions.noticia;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.lisa.televisa.R.color.black;

/**
 * Created by hever on 11/10/16.
 */

public class Single extends AppCompatActivity {

    public TextView txtTitle, txtContent, date;
    public ImageView thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.app_bar_single);

        txtTitle    = (TextView) findViewById(R.id.txtTitle);
        txtContent  = (TextView) findViewById(R.id.txtContent);
        thumbnail   = (ImageView) findViewById(R.id.imageView2);
        date        = (TextView) findViewById(R.id.txtTime);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}}, new int[]{black}));

        Bundle extras = getIntent().getExtras();

        String title    = "";
        String content  = "";
        String image    = "";
        String time     = "";

        if (extras != null) {
            title   = extras.getString("title");
            content = extras.getString("content");
            image   = extras.getString("image");
            time    = extras.getString("date");
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat dateformat = new SimpleDateFormat("MMMM dd, yyyy");

        try {
            Date date = format.parse(time);
            time = dateformat.format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        date.setText("Fuente Noticioeros Televisa  |  " + time.substring(0, 1).toUpperCase() + time.substring(1));

        txtTitle.setText(Html.fromHtml(title));

        Spanned result;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(content,Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(content);
        }

        txtContent.setText(result);

        Glide.with(getApplicationContext()).load(image).into(thumbnail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setTitle(Html.fromHtml(title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }


}
