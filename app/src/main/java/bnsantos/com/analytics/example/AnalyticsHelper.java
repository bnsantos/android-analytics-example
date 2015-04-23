package bnsantos.com.analytics.example;

import android.content.Context;

import com.flurry.android.FlurryAgent;
import com.localytics.android.Localytics;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import ly.count.android.api.Countly;

/**
 * Created by bruno on 31/01/15.
 */
public class AnalyticsHelper {
    private final Context mContext;
    private MixpanelAPI mMixpanelAPI;

    public AnalyticsHelper(Context context) {
        mContext = context;
        initMixPanel();
        initFlurry();
        initCountly();
        initLocalytics();
    }

    private void initMixPanel() {
        if (Constants.enableMixPanel) {
            mMixpanelAPI = MixpanelAPI.getInstance(mContext, Constants.getMixpanelToken());
            mMixpanelAPI.identify(Constants.getCurrentUserId());
            mMixpanelAPI.getPeople().set("last_login", Calendar.getInstance().getTime());
            mMixpanelAPI.getPeople().set("name", Constants.getCurrentUser());
            mMixpanelAPI.getPeople().set("gender", "female");
            mMixpanelAPI.getPeople().set("company", Constants.getCurrentCompany());

            mMixpanelAPI.alias(Constants.getCurrentUserId(), Constants.getCurrentUser());
            JSONObject props = new JSONObject();
            try {
                props.put("User Type", "Crazy woman");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mMixpanelAPI.registerSuperProperties(props);
        }
    }

    private void initFlurry() {
    }

    private void initCountly() {
        if (Constants.enableCountly) {
            Countly.sharedInstance().setLoggingEnabled(true);
            Countly.sharedInstance().init(mContext, Constants.getCountlyServer(), Constants.getCountlyKey());
        }
    }

    private void initLocalytics(){
        if(Constants.enableLocalytics){
            Localytics.setIdentifier("customer_name", Constants.getCurrentUser());
            Localytics.setIdentifier("gender", "female");
            Localytics.setIdentifier("company", Constants.getCurrentCompany());

            Localytics.setProfileAttribute("company",  Constants.getCurrentCompany());
            Localytics.setProfileAttribute("gender",  Constants.getCurrentCompany());
            Localytics.setProfileAttribute("customer_name",  Constants.getCurrentUser());
        }
    }

    public void trackEvent(OperationType operationType) {
        sendMixPanelEvent(operationType);
        sendFlurryEvent(operationType);
        logCountlyEvent(operationType);
        sendLocalyticsEvent(operationType);
    }

    public void onStart() {
        if (Constants.enableFlurry) {
            FlurryAgent.setLogEnabled(true);
            FlurryAgent.onStartSession(mContext, Constants.getFlurryToken());
            setFlurryUser();
        }
        if (Constants.enableCountly) {
            Countly.sharedInstance().onStart();
        }
    }

    public void onStop() {
        if (Constants.enableFlurry) {
            FlurryAgent.onEndSession(mContext);
        }
        if (Constants.enableCountly) {
            Countly.sharedInstance().onStop();
        }
    }

    public void onDestroy() {
        if (Constants.enableMixPanel) {
            mMixpanelAPI.flush();
        }
    }

    private void sendMixPanelEvent(OperationType operationType) {
        if (Constants.enableMixPanel) {
            mMixpanelAPI.getPeople().increment(operationType.name(), 1);

            JSONObject props = new JSONObject();
            try {
                props.put("Gender", "Female");
                props.put("Plan", "Premium");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mMixpanelAPI.track(operationType.name(), props);
        }
    }

    private void sendFlurryEvent(OperationType operationType) {
        if (Constants.enableFlurry) {
            Map<String, String> eventParams = new HashMap<String, String>();

            eventParams.put("User", Constants.getCurrentUser());
            eventParams.put("Plan", "Premium");
            eventParams.put("company", Constants.getCurrentCompany());

            FlurryAgent.logEvent(operationType.name(), eventParams);
        }
    }

    private void logCountlyEvent(OperationType operationType) {
        if (Constants.enableCountly) {
            Map<String, String> eventParams = new HashMap<String, String>();
            eventParams.put("User", Constants.getCurrentUser());
            eventParams.put("UserId", Constants.getCurrentUserId());
            eventParams.put("Plan", "Premium");
            eventParams.put("Gender", "Female");
            eventParams.put("Device", "android");
            eventParams.put("Language", mContext.getResources().getConfiguration().locale.getLanguage());
            Countly.sharedInstance().recordEvent(operationType.name(), eventParams, 1);
        }
    }

    private void setFlurryUser() {
        if (Constants.enableFlurry) {
            FlurryAgent.setGender(com.flurry.android.Constants.FEMALE);
            FlurryAgent.setUserId(Constants.getCurrentUserId());
        }
    }

    public void onResume(String screenName){
        if(Constants.enableLocalytics){
            Localytics.openSession();
            Localytics.tagScreen(screenName);
            Localytics.upload();
        }
    }

    private void sendLocalyticsEvent(OperationType operationType){
        if(Constants.enableLocalytics){
            Map<String, String> eventParams = new HashMap<String, String>();
            eventParams.put("User", Constants.getCurrentUser());
            eventParams.put("UserId", Constants.getCurrentUserId());
            eventParams.put("Plan", "Premium");
            eventParams.put("Gender", "Female");
            eventParams.put("Device", "android");
            eventParams.put("company", Constants.getCurrentCompany());
            eventParams.put("Language", mContext.getResources().getConfiguration().locale.getLanguage());
            Localytics.tagEvent(operationType.name(), eventParams);
        }
    }
}
