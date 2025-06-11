package com.example.tfgAndroid.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfgAndroid.LoginActivity;
import com.example.tfgAndroid.R;
import com.example.tfgAndroid.api.RetrofitClient;
import com.example.tfgAndroid.api.ApiService;
import com.example.tfgAndroid.model.UserDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment {

    private TextView tvUsername, tvEmail;
    private Button btnLogout, btnDeleteProfile;
    private SharedPreferences sharedPreferences;
    private ApiService apiService;
    private Integer userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        tvUsername = view.findViewById(R.id.tvUsername);
        tvEmail = view.findViewById(R.id.tvEmail);
        btnLogout = view.findViewById(R.id.btnLogout);
        btnDeleteProfile = view.findViewById(R.id.btnDeleteProfile);

        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        cargarPerfil();

        btnLogout.setOnClickListener(v -> cerrarSesion());
        btnDeleteProfile.setOnClickListener(v -> eliminarPerfil());

        return view;
    }

    private void cargarPerfil() {
        Call<UserDTO> call = apiService.getMyUser();
        call.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserDTO user = response.body();
                    tvUsername.setText("Usuario: " + user.getName());
                    tvEmail.setText("Email: " + user.getEmail());
                    userId = user.getId();
                } else {
                    Toast.makeText(getContext(), "Error al obtener perfil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                Toast.makeText(getContext(), "Fallo de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cerrarSesion() {
        sharedPreferences.edit().clear().apply();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }
    private void eliminarPerfil() {
        if (userId == null) return;

        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Darte de baja")
                .setMessage("¿Estás seguro de que quieres dar de baja a tu perfil? Esta acción es irreversible.")
                .setPositiveButton("Darse de baja", (dialog, which) -> {
                    // Proceder con la eliminación si el usuario confirma
                    Call<Void> call = apiService.deleteUser(userId);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Perfil eliminado", Toast.LENGTH_SHORT).show();
                                cerrarSesion();
                            } else {
                                Toast.makeText(getContext(), "Error al eliminar perfil", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getContext(), "Fallo al eliminar perfil", Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss()) // Cierra el diálogo sin eliminar
                .show();
    }

}
