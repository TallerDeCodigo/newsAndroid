package com.lisa.televisa.persistence;

import android.content.Context;
import android.content.SharedPreferences;

import com.lisa.televisa.model.Article;

/**
 * Created by hever on 11/11/16.
 */

public class NewsData {

    public static final String PREF_NAME = "NEWS_NOTIFICATIONS";
    public static final String ID_PUBLICATIONS = "POSTID";

    private SharedPreferences manager;
    private SharedPreferences.Editor editor;

    public NewsData(Context context){
        manager = context.getSharedPreferences(PREF_NAME, 0);
        editor = manager.edit();
    }

    public void notificationSave(String POSTID){

        editor.putString(ID_PUBLICATIONS, POSTID);
        editor.commit();

    }

    public String getPostID(){
        return manager.getString(ID_PUBLICATIONS, "");
    }
}
