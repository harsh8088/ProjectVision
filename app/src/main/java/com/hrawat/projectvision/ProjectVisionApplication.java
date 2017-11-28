package com.hrawat.projectvision;

import android.app.Application;
import android.content.Context;

/**
 * Created by hrawat on 11/10/2017.
 */
public class ProjectVisionApplication extends Application {

    private final String TAG = this.getClass().getSimpleName();
    private static ProjectVisionApplication mApp = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }

    public static Context context() {
        return mApp.getApplicationContext();
    }
}
