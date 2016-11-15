package com.lisa.televisa;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lisa.televisa.model.Article;
import com.lisa.televisa.seccions.BreakingNews;
import com.lisa.televisa.seccions.noticia;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.view.View.GONE;
import static com.lisa.televisa.R.color.black;
import static com.lisa.televisa.R.drawable.blank;

/**
 * Created by hever on 11/10/16.
 */

public class Single extends AppCompatActivity {

    public TextView txtTitle, txtContent, date;
    public ImageView thumbnail;
    public String link = "";
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fluid_single);

        txtTitle    = (TextView) findViewById(R.id.txtTitle);
        txtContent  = (TextView) findViewById(R.id.txtContent);
        thumbnail   = (ImageView) findViewById(R.id.imageView2);
        date        = (TextView) findViewById(R.id.txtTime);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(getApplicationContext(), Single.class);
                                getApplication().startActivity(i);
                            }
                        }).show();*/
                Intent i = new Intent(getApplicationContext(), Onlive.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplication().startActivity(i);

            }
        });

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
            link    = extras.getString("link");
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

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fondo);
        //final BitmapDrawable bd = new BitmapDrawable(bitmap);
        //final ColorDrawable cd = new ColorDrawable(Color.rgb(68, 74, 83));
        //cd.setAlpha(0);
        //getSupportActionBar().setBackgroundDrawable(cd);


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


}
