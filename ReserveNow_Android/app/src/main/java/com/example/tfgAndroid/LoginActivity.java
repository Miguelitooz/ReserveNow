package com.example.tfgAndroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfgAndroid.api.ApiService;
import com.example.tfgAndroid.api.RetrofitClient;
import com.example.tfgAndroid.model.LoginRequest;
import com.example.tfgAndroid.model.TokenResponse;
import com.example.tfgAndroid.model.User;
import com.example.tfgAndroid.model.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializar el SessionManager
        SessionManager.init(getApplicationContext());

        // Verificar si ya hay sesión iniciada
        if (SessionManager.getInstance().isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);
        EditText etEmail = findViewById(R.id.etEmail);
        Button btnRegister = findViewById(R.id.buttonRegister);
        Button btnLogin = findViewById(R.id.buttonLogin);

        btnRegister.setOnClickListener(v -> {
            String name = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            if (!name.isEmpty() && !password.isEmpty() && !email.isEmpty()) {
                registerUser(name, password, email);
            } else {
                Toast.makeText(LoginActivity.this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show();
            }
        });

        btnLogin.setOnClickListener(v -> {
            String name = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (!name.isEmpty() && !password.isEmpty()) {
                loginUser(name, password);
            } else {
                Toast.makeText(LoginActivity.this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerUser(String name, String password, String email) {
        User user = new User(name, password, email);
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Void> call = apiService.registerUser(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Error al registrar", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void loginUser(String name, String password) {
        LoginRequest loginRequest = new LoginRequest(name, password);
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<TokenResponse> call = apiService.loginUser(loginRequest);
        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().getToken();
                    SessionManager.getInstance().saveToken(token);

                    Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Intentamos leer el mensaje de error del cuerpo
                    String errorMessage = "Error al iniciar sesión";

                    try {
                        if (response.errorBody() != null) {
                            // Leer el cuerpo como String
                            String errorBody = response.errorBody().string();

                            // Aquí puedes hacer un parseo básico para detectar "Bad credentials"
                            if (errorBody.contains("Bad credentials")) {
                                errorMessage = "Contraseña o usuario incorrecto";
                            } else {
                                // Si quieres, puedes mostrar el mensaje tal cual viene, o dejar el genérico
                                errorMessage = errorBody;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
