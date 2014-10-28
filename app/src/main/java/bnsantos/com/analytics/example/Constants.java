package bnsantos.com.analytics.example;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bruno on 22/10/14.
 */
public class Constants {
    public static final String ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static boolean enableMixPanel = false;
    public static boolean enableFlurry = false;
    public static boolean enableCountly = false;
    private static String CURRENT_USER;
    private static String CURRENT_USER_ID;
    private static String MIXPANEL_TOKEN = ""; //Set your default token here
    private static String FLURRY_TOKEN = "";  //Your flurry token
    private static String COUNTLY_SERVER = ""; //Your countly server here
    private static String COUNTLY_APP_KEY = ""; //Your countly app key here

    public static String getCurrentUser() {
        return CURRENT_USER;
    }

    public static void setCurrentUser(String CURRENT_USER) {
        Constants.CURRENT_USER = CURRENT_USER;
    }

    public static String getCurrentUserId() {
        return CURRENT_USER_ID;
    }

    public static void setCurrentUserId(int id) {
        Constants.CURRENT_USER_ID = Integer.toString(id);
    }

    public static String getMixpanelToken() {
        return MIXPANEL_TOKEN;
    }

    public static void setMixpanelToken(String MIXPANEL_TOKEN) {
        Constants.MIXPANEL_TOKEN = MIXPANEL_TOKEN;
    }

    public static String formatDate(Date calendar){
        SimpleDateFormat sdf = new SimpleDateFormat(ISO_DATE_FORMAT);
        return sdf.format(calendar);
    }

    public static String getFlurryToken() {
        return FLURRY_TOKEN;
    }

    public static void setFlurryToken(String FLURRY_TOKEN) {
        Constants.FLURRY_TOKEN = FLURRY_TOKEN;
    }

    public static String getCountlyServer() {
        return COUNTLY_SERVER;
    }

    public static void setCountlyServer(String COUNTLY_SERVER) {
        Constants.COUNTLY_SERVER = COUNTLY_SERVER;
    }

    public static String getCountlyKey() {
        return COUNTLY_APP_KEY;
    }

    public static void setCountlyKey(String COUNTLY_APP_KEY) {
        Constants.COUNTLY_APP_KEY = COUNTLY_APP_KEY;
    }
}
