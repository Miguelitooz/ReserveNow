package com.example.tfgAndroid.Fragments;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.example.tfgAndroid.R;
import com.example.tfgAndroid.api.ApiService;
import com.example.tfgAndroid.api.RetrofitClient;
import com.example.tfgAndroid.model.ReservationDTO;
import com.example.tfgAndroid.model.ReservationRequest;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateReservationFragment extends Fragment {

    private Spinner spinnerRestaurant, spinnerTime, spinnerGuests;
    private TextView tvSelectedDate, tvSelectedAllergies;
    private Button btnCreateReservation;

    private ApiService apiService;

    private Calendar selectedDate = Calendar.getInstance();

    private boolean[] selectedAllergiesFlags;
    private String[] allergiesArray = {"Gluten", "Lactosa", "Frutos secos", "Mariscos", "Huevos", "Soja", "Ninguna"};
    private List<String> selectedAllergies = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_reservation, container, false);

        spinnerRestaurant = view.findViewById(R.id.spinnerRestaurant);
        spinnerTime = view.findViewById(R.id.spinnerTime);
        spinnerGuests = view.findViewById(R.id.spinnerGuests);
        tvSelectedDate = view.findViewById(R.id.tvSelectedDate);
        tvSelectedAllergies = view.findViewById(R.id.tvSelectedAllergies);
        btnCreateReservation = view.findViewById(R.id.btnCreateReservation);

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        setupRestaurantSpinner();
        setupTimeSpinner();
        setupGuestsSpinner();

        tvSelectedDate.setOnClickListener(v -> showDatePicker());

        tvSelectedAllergies.setOnClickListener(v -> showAllergiesDialog());

        btnCreateReservation.setOnClickListener(v -> enviarReserva());

        return view;
    }

    private void setupRestaurantSpinner() {
        String[] restaurants = {"La parrilla", "El asador", "Rincon Italiano"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, restaurants);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRestaurant.setAdapter(adapter);
    }

    private void setupTimeSpinner() {
        List<String> times = new ArrayList<>();
        int startHour = 20;
        int startMinute = 30;
        int endHour = 23;

        for (int hour = startHour; hour <= endHour; hour++) {
            for (int minute : new int[]{0, 30}) {
                if (hour == startHour && minute < startMinute) continue;
                if (hour == endHour && minute > 0) break;
                times.add(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, times);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(adapter);
    }

    private void setupGuestsSpinner() {
        List<Integer> guests = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            guests.add(i);
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, guests);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGuests.setAdapter(adapter);
    }

    private void showDatePicker() {
        int year = selectedDate.get(Calendar.YEAR);
        int month = selectedDate.get(Calendar.MONTH);
        int day = selectedDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, y, m, d) -> {
                    selectedDate.set(Calendar.YEAR, y);
                    selectedDate.set(Calendar.MONTH, m);
                    selectedDate.set(Calendar.DAY_OF_MONTH, d);
                    updateDateLabel();
                }, year, month, day);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void updateDateLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        tvSelectedDate.setText(sdf.format(selectedDate.getTime()));
    }

    private void showAllergiesDialog() {
        selectedAllergiesFlags = new boolean[allergiesArray.length];

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(requireContext());
        builder.setTitle("Selecciona alergias");

        builder.setMultiChoiceItems(allergiesArray, selectedAllergiesFlags, (dialog, which, isChecked) -> {
            selectedAllergiesFlags[which] = isChecked;
        });

        builder.setPositiveButton("Aceptar", (dialog, which) -> {
            selectedAllergies.clear();
            for (int i = 0; i < allergiesArray.length; i++) {
                if (selectedAllergiesFlags[i]) {
                    selectedAllergies.add(allergiesArray[i]);
                }
            }
            if (selectedAllergies.isEmpty()) {
                tvSelectedAllergies.setText("Ninguna");
            } else {
                tvSelectedAllergies.setText(String.join(", ", selectedAllergies));
            }
        });

        builder.setNegativeButton("Cancelar", null);

        builder.show();
    }

    private void enviarReserva() {
        boolean valid = true;

        // Validar restaurante
        if (spinnerRestaurant.getSelectedItem() == null) {
            Toast.makeText(requireContext(), "Selecciona un restaurante", Toast.LENGTH_SHORT).show();
            spinnerRestaurant.requestFocus();
            valid = false;
        }

        // Validar fecha
        String fechaSeleccionada = tvSelectedDate.getText().toString();
        if (fechaSeleccionada.equals("Pulsa para seleccionar") || fechaSeleccionada.isEmpty()) {
            tvSelectedDate.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            Toast.makeText(requireContext(), "Selecciona una fecha", Toast.LENGTH_SHORT).show();
            tvSelectedDate.requestFocus();
            valid = false;
        } else {
            tvSelectedDate.setTextColor(getResources().getColor(android.R.color.black));
        }

        // Validar hora
        if (spinnerTime.getSelectedItem() == null) {
            Toast.makeText(requireContext(), "Selecciona una hora", Toast.LENGTH_SHORT).show();
            spinnerTime.requestFocus();
            valid = false;
        }

        // Validar número de comensales
        if (spinnerGuests.getSelectedItem() == null) {
            Toast.makeText(requireContext(), "Selecciona el número de comensales", Toast.LENGTH_SHORT).show();
            spinnerGuests.requestFocus();
            valid = false;
        }

        // Validar alergias
        if (selectedAllergies.isEmpty()) {
            Toast.makeText(requireContext(), "Debes seleccionar al menos una opcion en alergias", Toast.LENGTH_SHORT).show();
            tvSelectedAllergies.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            tvSelectedAllergies.requestFocus();
            valid = false;
        } else {
            tvSelectedAllergies.setTextColor(getResources().getColor(android.R.color.black));
        }

        if (!valid) return;

        // Si todo es válido, mostrar diálogo de confirmación
        showConfirmationDialog();
    }

    private void showConfirmationDialog() {
        String restaurant = spinnerRestaurant.getSelectedItem().toString();
        String timePart = spinnerTime.getSelectedItem().toString();
        int guests = (Integer) spinnerGuests.getSelectedItem();

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String datePart = sdfDate.format(selectedDate.getTime());
        String dateTime = datePart + "T" + timePart + ":00";

        String allergiesString = String.join(", ", selectedAllergies);

        String message = "¿Estás seguro de que quieres hacer la reserva?\n\n" +
                "Restaurante: " + restaurant + "\n" +
                "Fecha: " + datePart + "\n" +
                "Hora: " + timePart + "\n" +
                "Comensales: " + guests + "\n" +
                "Alergias: " + allergiesString;

        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Confirmar Reserva")
                .setMessage(message)
                .setPositiveButton("Sí", (dialog, which) -> {
                    // Aquí se envía la reserva al backend
                    sendReservationToApi(restaurant, dateTime, guests, allergiesString);
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void sendReservationToApi(String restaurant, String dateTime, int guests, String allergiesString) {
        ReservationRequest request = new ReservationRequest(restaurant, dateTime, guests, allergiesString);

        Call<ReservationDTO> call = apiService.createReservation(request);
        call.enqueue(new Callback<ReservationDTO>() {
            @Override
            public void onResponse(Call<ReservationDTO> call, Response<ReservationDTO> response) {
                if (response.isSuccessful()) {
                    showReservationNotification(restaurant, dateTime.substring(0, 10), dateTime.substring(11, 16), guests);
                    Toast.makeText(requireContext(), "Reserva creada con éxito", Toast.LENGTH_SHORT).show();
                    clearForm();
                } else {
                    String errorMessage = "Error al crear la reserva";
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            com.google.gson.JsonObject jsonObject = new com.google.gson.JsonParser().parse(errorBody).getAsJsonObject();
                            if (jsonObject.has("message")) {
                                errorMessage = jsonObject.get("message").getAsString();
                            } else {
                                errorMessage = errorBody;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ReservationDTO> call, Throwable t) {
                Toast.makeText(requireContext(), "Error de red al crear la reserva", Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void clearForm() {
        spinnerRestaurant.setSelection(0);
        tvSelectedDate.setText("Pulsa para seleccionar");
        tvSelectedDate.setTextColor(getResources().getColor(android.R.color.black));
        spinnerTime.setSelection(0);
        spinnerGuests.setSelection(0);
        selectedAllergies.clear();
        tvSelectedAllergies.setText("Pulsa para seleccionar");
        tvSelectedAllergies.setTextColor(getResources().getColor(android.R.color.black));
        selectedDate = Calendar.getInstance();
    }

    private void showReservationNotification(String restaurant, String date, String time, int guests) {
        String channelId = "reservation_channel";
        String channelName = "Reservas";
        NotificationManager notificationManager = (NotificationManager) requireContext().getSystemService(Context.NOTIFICATION_SERVICE);

        // Crear canal para Android 8+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("Notificaciones de reservas");
            notificationManager.createNotificationChannel(channel);
        }

        String message = "Tu reserva:\n" +
                "Restaurante: " + restaurant + "\n" +
                "Fecha: " + date + "\n" +
                "Hora: " + time + "\n" +
                "Comensales: " + guests;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), channelId)
                .setSmallIcon(R.drawable.ic_reserva)
                .setContentTitle("Reserva Completada")
                .setContentText("Tu reserva ha sido registrada.")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(1, builder.build());
    }





}
