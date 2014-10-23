package bnsantos.com.mixpanelexample;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bruno on 22/10/14.
 */
public class Constants {
    public static final String ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static String CURRENT_USER;
    private static String CURRENT_USER_ID;
    private static String MIXPANEL_TOKEN = ""; //Set your default token here

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
}
