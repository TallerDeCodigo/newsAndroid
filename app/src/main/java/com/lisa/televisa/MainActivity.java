package com.lisa.televisa;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;

import android.support.v4.content.LocalBroadcastManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lisa.televisa.persistence.NewsData;
import com.lisa.televisa.request.News;
import com.lisa.televisa.seccions.BreakingNews;
import com.lisa.televisa.seccions.Ciencia;
import com.lisa.televisa.seccions.Deportes;
import com.lisa.televisa.seccions.Economia;
import com.lisa.televisa.seccions.Entreteniemiento;
import com.lisa.televisa.seccions.Internacional;
import com.lisa.televisa.seccions.Nacional;
import com.lisa.televisa.seccions.Opinion;
import com.lisa.televisa.seccions.Politica;
import com.lisa.televisa.seccions.Vida;
import com.lisa.televisa.config.Config;
import com.lisa.televisa.utils.Constants;
import com.lisa.televisa.utils.NotificationUtils;
import com.lisa.televisa.seccions.noticia;

import com.google.firebase.messaging.FirebaseMessaging;
import com.lisa.televisa.utils.PollService;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    //Public variables

    public static final String TAG = MainActivity.class.getName();

    public NewsData newsData;
    public static Activity activity;
    public News newsRequest;
    public Constants constants;
    public RelativeLayout onLive;
    public TextView txtTitle, txtHashTag;
    public RelativeLayout mRoot;
    public PollService Check;

    //Private variables

    private boolean viewIsAtHome;
    private int viewCurret;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        newsData    = new NewsData(getApplicationContext());

        onLive      = (RelativeLayout) findViewById(R.id.live_ad);
        onLive.setVisibility(View.GONE);
        onLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Onlive.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplication().startActivity(i);
            }
        });

        txtTitle    = (TextView) findViewById(R.id.live_text);
        txtHashTag  = (TextView) findViewById(R.id.live_tema);

        mRoot = (RelativeLayout) findViewById(R.id.content_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Onlive.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplication().startActivity(i);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, final Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {

                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications

                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    final String message  = intent.getStringExtra("message");
                    final String postID   = intent.getStringExtra("postID");

                    Log.d(TAG, "Mensaje: " + message);
                    Log.d(TAG, "postID: " + postID);

                    Snackbar.make(mRoot, message, Snackbar.LENGTH_INDEFINITE)
                        .setAction("Ver", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                getSinglePost(postID);

                            }
                        }).setDuration(10000).show();
                }
            }
        };

        Bundle extras = getIntent().getExtras();

        String postID = "";



        //load breaking news
        displayView(0);
        //Display REG Fire Base
        displayFirebaseRegId();
        //Display Live
        Check = new PollService();
        if (Check.isOnline()){
            getOnLive();
        }



        if (extras != null) {
            postID = extras.getString("postID");
            if(postID != null){
                Log.d(TAG, extras.getString("postID"));
                getSinglePost(postID);
            }
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } /*else {
            super.onBackPressed();
        } */

        if (!viewIsAtHome) { //if the current view is not the News fragment
            displayView(0); //display the News fragment
        } else {
            moveTaskToBack(true);  //If view is in News fragment, exit application
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, Settings.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if(viewCurret == item.getItemId()) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        displayView(item.getItemId());
        return true;
    }

    public void displayView(int viewId) {

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (viewId) {

            case 0:
                fragment = new BreakingNews();
                viewIsAtHome = true;
                viewCurret = 0;
                break;

            case R.id.nav_internacional:
                fragment = new Internacional();
                viewIsAtHome = false;
                viewCurret = viewId;
                break;

            case R.id.nav_nacional:
                fragment = new Nacional();
                viewIsAtHome = false;
                viewCurret = viewId;
                break;


            case R.id.nav_cdmx:
                fragment = new noticia();
                viewIsAtHome = false;
                viewCurret = viewId;
                break;


            case R.id.nav_politica:
                fragment = new Politica();
                viewIsAtHome = false;
                viewCurret = viewId;
                break;

            case R.id.nav_economia:
                fragment = new Economia();
                viewIsAtHome = false;
                viewCurret = viewId;
                break;

            case R.id.nav_opinion:
                fragment = new Opinion();
                viewIsAtHome = false;
                viewCurret = viewId;
                break;

            case R.id.nav_tecnologia:
                fragment = new Ciencia();
                viewIsAtHome = false;
                viewCurret = viewId;
                break;

            case R.id.nav_cultura:
                fragment = new Ciencia();
                viewIsAtHome = false;
                viewCurret = viewId;
                break;

            case R.id.nav_deportes:
                fragment = new Deportes();
                viewIsAtHome = false;
                viewCurret = viewId;
                break;

            case R.id.nav_entretenimiento:
                fragment = new Entreteniemiento();
                viewIsAtHome = false;
                viewCurret = viewId;
                break;

            case R.id.nav_vida:
                fragment = new Vida();
                viewIsAtHome = false;
                viewCurret = viewId;
                break;

            case R.id.ic_about:
                Intent intent = new Intent(this, About.class);
                this.startActivity(intent);
                break;

            case R.id.settings:
                Intent i = new Intent(this, Settings.class);
                this.startActivity(i);
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContent, fragment);
            ft.commit();
        }

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    private void displayFirebaseRegId() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId))
            Log.d(TAG, "Firebase Reg Id: " + regId);
        else
            Log.d(TAG, "Firebase Reg Id is not received yet!");
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());

        Check = new PollService();
        if (Check.isOnline()){
            Log.d("entra","ENTRA");
            getOnLive();
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("televisa.NEWS");
            alertDialog.setMessage("Esta aplicación requiere una conexión a internet.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Salir",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            MainActivity.this.finish();
                            System.exit(0);
                        }
                    });
            alertDialog.show();
        }

    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    public void getOnLive() {

        newsRequest = new News(getApplicationContext(), constants.URL_SERVICES + "stuff/onlive", new News.NewsListener() {

            @Override
            public void onGetNews(String jsonArticles) {

                try {

                    Log.d(TAG, jsonArticles);

                    JSONArray jsonArray = new JSONArray(jsonArticles);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {

                            String title         = jsonArray.getJSONObject(i).getString("title");
                            String hashtag       = jsonArray.getJSONObject(i).getString("hashtag");
                            String vivo          = jsonArray.getJSONObject(i).getString("vivo");
                            RelativeLayout rela = (RelativeLayout) findViewById(R.id.activity_noticia);
                            final float scale = getResources().getDisplayMetrics().density;
                            int padding_56dp = (int) (56 * scale + 0.5f);
                            int padding_90dp = (int) (90 * scale + 0.5f);

                            if(vivo.equals("1")) {
                                onLive.setVisibility(View.VISIBLE);
                                rela.setPadding(0,padding_90dp,0,0);
                                txtTitle.setText(title);
                                txtHashTag.setText(hashtag);

                            }
                            else {
                                onLive.setVisibility(View.GONE);
                                rela.setPadding(0,padding_56dp,0,0);
                                txtTitle.setText("");
                                txtHashTag.setText("");
                            }

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

            }
        });
        newsRequest.execute();
    }

    public void getSinglePost(String postID){
        Intent i = new Intent(getApplicationContext(), Single.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("title", "");
        i.putExtra("content", "");
        i.putExtra("image", "");
        i.putExtra("date", "");
        i.putExtra("home", "home");
        i.putExtra("link", "");
        i.putExtra("postID", postID);
        getApplication().startActivity(i);
    }
}

