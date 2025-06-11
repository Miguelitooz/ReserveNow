package com.example.tfgAndroid.api;

import android.content.Intent;
import android.util.Log;

import com.example.tfgAndroid.ReserveNow;
import com.example.tfgAndroid.model.SessionManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = SessionManager.getInstance().getToken();
        Request request = chain.request();

        if (token != null && !token.isEmpty()) {
            request = request.newBuilder()
                    .addHeader("Authorization", "Bearer " + token)
                    .build();
        }

        Response response = chain.proceed(request);

        if (response.code() == 401 || response.code() == 403) {
            Log.d("AuthInterceptor", "Token inválido o sesión expirada. Cerrando sesión.");
            SessionManager.getInstance().clearSession();

            // Emitir el broadcast para logout
            Intent intent = new Intent("com.example.tfgAndroid.ACTION_LOGOUT");
            intent.setPackage("com.example.tfgAndroid"); // asegurar que sea interno
            ReserveNow.getAppContext().sendBroadcast(intent);
        }

        return response;
    }
}
