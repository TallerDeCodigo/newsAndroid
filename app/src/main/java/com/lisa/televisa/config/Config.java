package com.lisa.televisa.config;

/**
 * Created by hever on 11/11/16.
 */

public class Config {

    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";
    public static final String TOPIC_CDMX   = "cdmx";
    public static final String TOPIC_INT    = "internacional";
    public static final String TOPIC_NAC    = "nacional";

    public static final String TOPIC_ECO    = "economia";
    public static final String TOPIC_POL    = "politica";
    public static final String TOPIC_OPI    = "opinion";
    public static final String TOPIC_CYT    = "cienciaytecnologia";
    public static final String TOPIC_CUL    = "cultura";
    public static final String TOPIC_DEP    = "deportes";
    public static final String TOPIC_ENT    = "entretenimiento";
    public static final String TOPIC_VYE    = "vidayestilo";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "ah_firebase";
}
