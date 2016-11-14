package com.lisa.televisa.persistence;

import android.content.Context;
import android.content.SharedPreferences;

import com.lisa.televisa.model.Article;

import java.util.List;

/**
 * Created by hever on 11/11/16.
 */

public class NewsData {

    public static final String PREF_NAME                 = "NEWS_NOTIFICATIONS";
    public static final String ID_PUBLICATIONS           = "POSTID";
    public static final String PUSH_INTERNACIONAL        = "INTERNACIONAL";         //1
    public static final String PUSH_NACIONAL             = "NACIONAL";              //2
    public static final String PUSH_CDMX                 = "CDMX";                  //3
    public static final String PUSH_POLITICA             = "POLITICA";              //4
    public static final String PUSH_ECONOMIA             = "ECONOMIA";              //5
    public static final String PUSH_OPINION              = "OPINION";               //6
    public static final String PUSH_CIENCIAYTECNOLOGIA   = "CIENCIAYTECNOLOGIA";    //7
    public static final String PUSH_CULTURA              = "CULTURA";               //8
    public static final String PUSH_DEPORTES             = "DEPORTES";              //9
    public static final String PUSH_ENTRETENIMIENTO      = "ENTRETENIMIENTO";       //10
    public static final String PUSH_VIDAYESTILO          = "VIDAYESTILO";           //11
    public static final String PUSH_GLOBAL               = "GLOBAL";           //12

    public List<Article> articleList;

    private SharedPreferences manager;
    private SharedPreferences.Editor editor;

    public NewsData(Context context)
    {
        manager = context.getSharedPreferences(PREF_NAME, 0);
        editor = manager.edit();
    }

    public void notificationSave(String seccion, String activo)
    {
        editor.putString(seccion, activo);
        editor.commit();

    }

    public void setPostID(String postID){
        editor.putString(ID_PUBLICATIONS, postID);
        editor.commit();
    }


    /* REGISTRO DE LAS OPCIONES DE PUSH */

    public String getNotificationON(String seccion){

        return manager.getString(seccion, "");

    }

    public String getPostID(){
        return manager.getString(ID_PUBLICATIONS, "");
    }
}
