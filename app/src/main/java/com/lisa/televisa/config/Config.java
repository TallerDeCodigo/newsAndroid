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

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "ah_firebase";
}
