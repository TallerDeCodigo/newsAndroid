package com.lisa.televisa;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.lisa.televisa.persistence.NewsData;

public class Settings extends AppCompatActivity {

    public NewsData newsData;

    public Switch iSwitch, nSwitch, cdmxSwitch, pSwitch, eSwitch, oSwitch, ecoSwitch, opSwitch, ciSwitch, culSwitch, depSwitch, entSwitch, vidaSwitch;

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


        iSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    newsData.notificationSave(newsData.PUSH_INTERNACIONAL, "1");
                else
                    newsData.notificationSave(newsData.PUSH_INTERNACIONAL, "0");
            }
        });

        nSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    newsData.notificationSave(newsData.PUSH_NACIONAL, "1");
                else
                    newsData.notificationSave(newsData.PUSH_NACIONAL, "0");
            }
        });

        cdmxSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    newsData.notificationSave(newsData.PUSH_CDMX, "1");
                else
                    newsData.notificationSave(newsData.PUSH_CDMX, "0");
            }
        });


        eSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    newsData.notificationSave(newsData.PUSH_ECONOMIA, "1");
                else
                    newsData.notificationSave(newsData.PUSH_ECONOMIA, "0");
            }
        });

        pSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    newsData.notificationSave(newsData.PUSH_POLITICA, "1");
                else
                    newsData.notificationSave(newsData.PUSH_POLITICA, "0");
            }
        });

        oSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    newsData.notificationSave(newsData.PUSH_OPINION, "1");
                else
                    newsData.notificationSave(newsData.PUSH_OPINION, "0");
            }
        });

        ciSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    newsData.notificationSave(newsData.PUSH_CIENCIAYTECNOLOGIA, "1");
                else
                    newsData.notificationSave(newsData.PUSH_CIENCIAYTECNOLOGIA, "0");
            }
        });

        culSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    newsData.notificationSave(newsData.PUSH_CULTURA, "1");
                else
                    newsData.notificationSave(newsData.PUSH_CULTURA, "0");
            }
        });

        depSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    newsData.notificationSave(newsData.PUSH_DEPORTES, "1");
                else
                    newsData.notificationSave(newsData.PUSH_DEPORTES, "0");
            }
        });

        entSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    newsData.notificationSave(newsData.PUSH_ENTRETENIMIENTO, "1");
                else
                    newsData.notificationSave(newsData.PUSH_ENTRETENIMIENTO, "0");
            }
        });

        vidaSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    newsData.notificationSave(newsData.PUSH_VIDAYESTILO, "1");
                else
                    newsData.notificationSave(newsData.PUSH_VIDAYESTILO, "0");
            }
        });

        getSupportActionBar().setTitle("Notificaciones");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



    }





}
