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
    public static boolean enableLocalytics = false;
    private static String CURRENT_USER;
    private static String CURRENT_USER_ID;
    private static String CURRENT_COMPANY;
    private static String MIXPANEL_TOKEN = "YOUR_TOKEN_HERE";
    private static String FLURRY_TOKEN = "YOUR_TOKEN_HERE";
    private static String COUNTLY_SERVER = "YOUR_SERVER_HERE";
    private static String COUNTLY_APP_KEY = "YOUR_TOKEN_HERE";

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

    public static String getCurrentCompany() {
        return CURRENT_COMPANY;
    }

    public static void setCurrentCompany(String CURRENT_COMPANY) {
        Constants.CURRENT_COMPANY = CURRENT_COMPANY;
    }

    public static String getMixpanelToken() {
        return MIXPANEL_TOKEN;
    }

    public static void setMixpanelToken(String MIXPANEL_TOKEN) {
        Constants.MIXPANEL_TOKEN = MIXPANEL_TOKEN;
    }

    public static String formatDate(Date calendar) {
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
