package bnsantos.com.mixpanelexample;

/**
 * Created by bruno on 22/10/14.
 */
public class Constants {
    private static String CURRENT_USER;
    private static String MIXPANEL_TOKEN = ""; //Set your default token here

    public static String getCurrentUser() {
        return CURRENT_USER;
    }

    public static void setCurrentUser(String CURRENT_USER) {
        Constants.CURRENT_USER = CURRENT_USER;
    }

    public static String getMixpanelToken() {
        return MIXPANEL_TOKEN;
    }

    public static void setMixpanelToken(String MIXPANEL_TOKEN) {
        Constants.MIXPANEL_TOKEN = MIXPANEL_TOKEN;
    }
}
