package com.lisa.televisa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import com.google.firebase.messaging.FirebaseMessaging;

import com.lisa.televisa.config.Config;
import com.lisa.televisa.persistence.NewsData;

public class Settings extends AppCompatActivity {

    public NewsData newsData;

    public Switch aSwitch, iSwitch, nSwitch, cdmxSwitch, pSwitch, eSwitch, oSwitch, ciSwitch, culSwitch, depSwitch, entSwitch, vidaSwitch;

    public static final String TAG = Settings.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_settings);

        newsData = new  NewsData(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        aSwitch         = (Switch) findViewById(R.id.sw_global);
        iSwitch         = (Switch) findViewById(R.id.sw_internacional);
        nSwitch         = (Switch) findViewById(R.id.sw_nacional);
        cdmxSwitch      = (Switch) findViewById(R.id.sw_cdmx);
        pSwitch         = (Switch) findViewById(R.id.sw_politica);
        eSwitch         = (Switch) findViewById(R.id.sw_economia);
        oSwitch         = (Switch) findViewById(R.id.sw_opinion);
        ciSwitch        = (Switch) findViewById(R.id.sw_tecnologia);
        culSwitch       = (Switch) findViewById(R.id.sw_cultura);
        depSwitch       = (Switch) findViewById(R.id.sw_deporte);
        entSwitch       = (Switch) findViewById(R.id.sw_entretenimiento);
        vidaSwitch      = (Switch) findViewById(R.id.sw_vida);



        Log.d(TAG,newsData.getNotificationON(newsData.PUSH_INTERNACIONAL));

        aSwitch.setChecked(newsData.getNotificationON(newsData.PUSH_GLOBAL).equals("1"));
        iSwitch.setChecked(newsData.getNotificationON(newsData.PUSH_INTERNACIONAL).equals("1"));
        nSwitch.setChecked(newsData.getNotificationON(newsData.PUSH_NACIONAL).equals("1"));
        cdmxSwitch.setChecked(newsData.getNotificationON(newsData.PUSH_CDMX).equals("1"));
        pSwitch.setChecked(newsData.getNotificationON(newsData.PUSH_POLITICA).equals("1"));
        eSwitch.setChecked(newsData.getNotificationON(newsData.PUSH_ECONOMIA).equals("1"));
        oSwitch.setChecked(newsData.getNotificationON(newsData.PUSH_OPINION).equals("1"));
        ciSwitch.setChecked(newsData.getNotificationON(newsData.PUSH_CIENCIAYTECNOLOGIA).equals("1"));
        culSwitch.setChecked(newsData.getNotificationON(newsData.PUSH_CULTURA).equals("1"));
        depSwitch.setChecked(newsData.getNotificationON(newsData.PUSH_DEPORTES).equals("1"));
        entSwitch.setChecked(newsData.getNotificationON(newsData.PUSH_ENTRETENIMIENTO).equals("1"));
        vidaSwitch.setChecked(newsData.getNotificationON(newsData.PUSH_VIDAYESTILO).equals("1"));


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                newsData.notificationSave(newsData.PUSH_GLOBAL, "1");
            } else {
                newsData.notificationSave(newsData.PUSH_GLOBAL, "0");
                FirebaseMessaging.getInstance().unsubscribeFromTopic(Config.TOPIC_GLOBAL);
            }
            }
        });

        iSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_INT);
                newsData.notificationSave(newsData.PUSH_INTERNACIONAL, "1");
            } else {
                newsData.notificationSave(newsData.PUSH_INTERNACIONAL, "0");
                FirebaseMessaging.getInstance().unsubscribeFromTopic(Config.TOPIC_INT);
            }
            }
        });

        nSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            if(isChecked) {
                newsData.notificationSave(newsData.PUSH_NACIONAL, "1");
                FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_NAC);
            } else {
                newsData.notificationSave(newsData.PUSH_NACIONAL, "0");
                FirebaseMessaging.getInstance().unsubscribeFromTopic(Config.TOPIC_NAC);
            }
            }
        });

        cdmxSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                newsData.notificationSave(newsData.PUSH_CDMX, "1");
                FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_CDMX);
            } else {
                newsData.notificationSave(newsData.PUSH_CDMX, "0");
                FirebaseMessaging.getInstance().unsubscribeFromTopic(Config.TOPIC_CDMX);
            }
            }
        });

        eSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                newsData.notificationSave(newsData.PUSH_ECONOMIA, "1");
                FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_ECO);
            } else {
                newsData.notificationSave(newsData.PUSH_ECONOMIA, "0");
                FirebaseMessaging.getInstance().unsubscribeFromTopic(Config.TOPIC_ECO);
            }
            }
        });

        pSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                newsData.notificationSave(newsData.PUSH_POLITICA, "1");
                FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_POL);
            } else {
                newsData.notificationSave(newsData.PUSH_POLITICA, "0");
                FirebaseMessaging.getInstance().unsubscribeFromTopic(Config.TOPIC_POL);
            }
            }
        });

        oSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                newsData.notificationSave(newsData.PUSH_OPINION, "1");
                FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_OPI);
            } else {
                newsData.notificationSave(newsData.PUSH_OPINION, "0");
                FirebaseMessaging.getInstance().unsubscribeFromTopic(Config.TOPIC_OPI);
            }
            }
        });

        ciSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                newsData.notificationSave(newsData.PUSH_CIENCIAYTECNOLOGIA, "1");
                FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_CYT);
            } else {
                newsData.notificationSave(newsData.PUSH_CIENCIAYTECNOLOGIA, "0");
                FirebaseMessaging.getInstance().unsubscribeFromTopic(Config.TOPIC_CYT);
            }
            }
        });

        culSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                newsData.notificationSave(newsData.PUSH_CULTURA, "1");
                FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_CUL);
            } else {
                newsData.notificationSave(newsData.PUSH_CULTURA, "0");
                FirebaseMessaging.getInstance().unsubscribeFromTopic(Config.TOPIC_CUL);
            }
            }
        });

        depSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            if(isChecked) {
                newsData.notificationSave(newsData.PUSH_DEPORTES, "1");
                FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_DEP);
            } else {
                newsData.notificationSave(newsData.PUSH_DEPORTES, "0");
                FirebaseMessaging.getInstance().unsubscribeFromTopic(Config.TOPIC_DEP);
            }
            }
        });

        entSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    newsData.notificationSave(newsData.PUSH_ENTRETENIMIENTO, "1");
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_ENT);
                } else {
                    newsData.notificationSave(newsData.PUSH_ENTRETENIMIENTO, "0");
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(Config.TOPIC_ENT);
                }
            }
        });

        vidaSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    newsData.notificationSave(newsData.PUSH_VIDAYESTILO, "1");
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_VYE);
                } else {
                    newsData.notificationSave(newsData.PUSH_VIDAYESTILO, "0");
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(Config.TOPIC_VYE);
                }
            }
        });

        getSupportActionBar().setTitle("Notificaciones");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }





}
