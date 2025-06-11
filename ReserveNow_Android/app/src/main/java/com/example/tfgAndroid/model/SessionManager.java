package com.example.tfgAndroid.model;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_TOKEN = "auth_token";

    private static SessionManager instance;
    private SharedPreferences prefs;
    private Context context; // <-- campo para guardar contexto

    private SessionManager(Context context) {
        this.context = context.getApplicationContext(); // guardar contexto
        prefs = this.context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized void init(Context context) {
        if (instance == null) {
            instance = new SessionManager(context);
        }
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("SessionManager no inicializado. Llama a init() primero.");
        }
        return instance;
    }

    public Context getContext() {
        return context;
    }

    public void saveToken(String token) {
        prefs.edit().putString(KEY_TOKEN, token).apply();
    }

    public String getToken() {
        return prefs.getString(KEY_TOKEN, null);
    }

    public boolean isLoggedIn() {
        return getToken() != null;
    }

    public void clearSession() {
        prefs.edit().clear().apply();
    }
}

