package bnsantos.com.analytics.example;

import android.app.Application;

import com.localytics.android.LocalyticsActivityLifecycleCallbacks;

/**
 * Created by bruno on 22/04/15.
 */
public class AnalyticsApp extends Application {
    @Override
    public void onCreate()
    {
        super.onCreate();

        // Register LocalyticsActivityLifecycleCallbacks
        registerActivityLifecycleCallbacks(
                new LocalyticsActivityLifecycleCallbacks(this));
    }
}
