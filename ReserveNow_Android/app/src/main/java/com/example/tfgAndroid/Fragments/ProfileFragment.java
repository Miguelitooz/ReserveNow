package com.example.tfgAndroid.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfgAndroid.R;
import com.example.tfgAndroid.adapter.ReservationsAdapter;
import com.example.tfgAndroid.api.ApiService;
import com.example.tfgAndroid.api.RetrofitClient;
import com.example.tfgAndroid.model.ReservationDTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private RecyclerView recyclerView;
    private ReservationsAdapter adapter;
    private List<ReservationDTO> reservations = new ArrayList<>();
    private ApiService apiService;
    private LinearLayout layoutNoReservations;
    private TextView txtNoReservations;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        recyclerView = view.findViewById(R.id.recyclerReservations);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ReservationsAdapter(reservations);
        recyclerView.setAdapter(adapter);

        layoutNoReservations = view.findViewById(R.id.layoutNoReservations);
        txtNoReservations = view.findViewById(R.id.txtNoReservations);

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Button btnGoToAnotherActivity = view.findViewById(R.id.btnGoToAnotherActivity);
        btnGoToAnotherActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reemplazar el fragmento actual por CreateReservationFragment
                Fragment createReservationFragment = new CreateReservationFragment();
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, createReservationFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        cargarReservas();

        return view;
    }


    // Método público que permite recargar reservas desde la Activity
    public void recargarReservas() {
        cargarReservas();
    }

    private void actualizarUI(List<ReservationDTO> lista) {
        if (lista == null || lista.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            layoutNoReservations.setVisibility(View.VISIBLE);
            txtNoReservations.setVisibility(View.VISIBLE);
        } else {
            reservations.clear();
            reservations.addAll(lista);
            adapter.notifyDataSetChanged();

            recyclerView.setVisibility(View.VISIBLE);
            layoutNoReservations.setVisibility(View.GONE);
            txtNoReservations.setVisibility(View.GONE);
        }
    }

    private void cargarReservas() {
        Call<List<ReservationDTO>> call = apiService.getUserReservations();
        call.enqueue(new Callback<List<ReservationDTO>>() {
            @Override
            public void onResponse(Call<List<ReservationDTO>> call, Response<List<ReservationDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    actualizarUI(response.body());
                } else {
                    Toast.makeText(getContext(), "No se pudo obtener las reservas", Toast.LENGTH_SHORT).show();
                    actualizarUI(null);
                }
            }

            @Override
            public void onFailure(Call<List<ReservationDTO>> call, Throwable t) {
                Toast.makeText(getContext(), "Error de red al cargar reservas", Toast.LENGTH_SHORT).show();
                actualizarUI(null);
            }
        });
    }
}
