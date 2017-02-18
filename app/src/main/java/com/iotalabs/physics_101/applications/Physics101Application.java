package com.iotalabs.physics_101.applications;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.iotalabs.physics_101.R;
import com.parse.Parse;

import android.app.Application;

/**
 * Created by karangarg on 06/02/17.
 */

public class Physics101Application extends Application {

    private Tracker mTracker;

    @Override public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
            .applicationId("bLPcvdQl08iiSgZX7TZtIZmohe9mk68WJHJkGOOm")
            .clientKey("gIlPYTF4hvK4vGDek8o7RBmzTH90wnS5fb4Ein9P")
            .server("https://parseapi.back4app.com/").build()
        );
    }

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }

}
