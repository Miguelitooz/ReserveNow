// MyApp.java
package com.example.tfgAndroid;

import android.app.Application;
import android.content.Context;

public class ReserveNow extends Application {
    private static ReserveNow instance;

    public static ReserveNow getInstance() {
        return instance;
    }

    public static Context getAppContext() {
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
