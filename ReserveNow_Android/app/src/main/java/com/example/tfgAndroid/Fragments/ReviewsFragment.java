// ReviewsFragment.java
package com.example.tfgAndroid.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfgAndroid.R;
import com.example.tfgAndroid.adapter.ReviewsAdapter;
import com.example.tfgAndroid.api.ApiService;
import com.example.tfgAndroid.api.RetrofitClient;
import com.example.tfgAndroid.model.CreateReviewDTO;
import com.example.tfgAndroid.model.ReviewDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewsFragment extends Fragment {

    private RecyclerView rvReviews;
    private ReviewsAdapter adapter;
    private ApiService apiService;

    private Spinner spinner;
    private EditText etReviewText;
    private Button btnSubmit;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inf,
                             @Nullable ViewGroup ct,
                             @Nullable Bundle bs) {
        return inf.inflate(R.layout.fragment_reviews, ct, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle bs) {
        super.onViewCreated(v, bs);

        // Referencia vistas
        spinner      = v.findViewById(R.id.spinnerRestaurants);
        etReviewText = v.findViewById(R.id.etReviewText);
        btnSubmit    = v.findViewById(R.id.btnSubmitReview);
        rvReviews    = v.findViewById(R.id.rvReviews);

        // Recycler + Adapter
        rvReviews.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new ReviewsAdapter(new ArrayList<>());
        rvReviews.setAdapter(adapter);

        // API
        apiService = RetrofitClient
                .getRetrofitInstance()
                .create(ApiService.class);

        // Spinner de restaurantes
        List<String> names = Arrays.asList("El rincon Italiano", "La parrilla", "El asador");
        List<Long>   ids   = Arrays.asList(1L, 2L, 3L);
        ArrayAdapter<String> spAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                names
        );
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spAdapter);

        // Carga inicial de reseñas
        loadReviews();

        // Envío de reseña
        btnSubmit.setOnClickListener(x ->
                submitReview(ids.get(spinner.getSelectedItemPosition()))
        );
    }

    private void loadReviews() {
        apiService.listAllReviews().enqueue(new Callback<List<ReviewDTO>>() {
            @Override
            public void onResponse(Call<List<ReviewDTO>> call,
                                   Response<List<ReviewDTO>> resp) {
                if (resp.isSuccessful() && resp.body() != null) {
                    adapter.setItems(resp.body());
                }
            }
            @Override public void onFailure(Call<List<ReviewDTO>> call, Throwable t) { }
        });
    }

    private void submitReview(Long restaurantId) {
        String text = etReviewText.getText().toString().trim();
        if (text.isEmpty()) {
            etReviewText.setError("Escribe algo");
            return;
        }
        CreateReviewDTO body = new CreateReviewDTO(restaurantId, text);
        apiService.createReview(body).enqueue(new Callback<ReviewDTO>() {
            @Override
            public void onResponse(Call<ReviewDTO> call,
                                   Response<ReviewDTO> resp) {
                if (resp.isSuccessful() && resp.body() != null) {
                    adapter.addItem(resp.body());
                    rvReviews.scrollToPosition(0);
                    etReviewText.setText("");
                }
            }
            @Override public void onFailure(Call<ReviewDTO> call, Throwable t) { }
        });
    }
}
