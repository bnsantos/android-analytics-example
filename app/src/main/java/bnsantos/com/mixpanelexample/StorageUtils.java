package bnsantos.com.mixpanelexample;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bruno on 22/10/14.
 */
public class StorageUtils {
    private static final String TAG = StorageUtils.class.getName();
    private static final String LOG_MIXPANEL_EXAMPLE = "LOG_MIXPANEL_EXAMPLE";
    private static final String SEPARATOR = "MIX-PANEL-EXAMPLE:";

    public static void clear(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE).edit();
        editor.clear();
        Log.d(TAG, "Clear shared preferences " + editor.commit());
    }

    public static void addLog(Context context, String line) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        StringBuffer raw = new StringBuffer(sharedPreferences.getString(LOG_MIXPANEL_EXAMPLE, ""));
        if (raw.length() > 0) {
            raw.append(SEPARATOR + line);
        } else {
            raw.append(line);
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOG_MIXPANEL_EXAMPLE, raw.toString());
        Log.d(TAG, "Added line into sharedPrefs" + editor.commit());
    }

    public static List<String> getLog(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        String raw = sharedPreferences.getString(LOG_MIXPANEL_EXAMPLE, "");
        List<String> logs = new ArrayList<String>();
        if (raw.length() != 0) {
            String[] log = raw.split(SEPARATOR);
            logs.addAll(Arrays.asList(log));
        }
        return logs;
    }

    public static String getRawLog(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        String raw = sharedPreferences.getString(LOG_MIXPANEL_EXAMPLE, "");
        return raw.replace(SEPARATOR, "\n");
    }
}
