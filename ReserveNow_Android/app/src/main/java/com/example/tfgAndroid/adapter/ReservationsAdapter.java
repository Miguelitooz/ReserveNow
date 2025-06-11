package com.example.tfgAndroid.adapter;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfgAndroid.R;
import com.example.tfgAndroid.api.ApiService;
import com.example.tfgAndroid.api.RetrofitClient;
import com.example.tfgAndroid.model.ReservationDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationsAdapter extends RecyclerView.Adapter<ReservationsAdapter.ViewHolder> {

    private List<ReservationDTO> reservations;
    private ApiService apiService;

    public ReservationsAdapter(List<ReservationDTO> reservations) {
        this.reservations = reservations;
        this.apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtRestaurant, txtUsername, txtDate, txtAllergies;
        Spinner spinnerHour, spinnerGuests;
        Button btnEdit, btnDelete;

        boolean isEditing = false;
        String selectedHour = null;

        final String[] allergyOptions = {"Gluten", "Lácteos", "Frutos secos", "Huevos", "Pescado", "Mariscos"};

        public ViewHolder(View itemView) {
            super(itemView);

            txtRestaurant = itemView.findViewById(R.id.txtRestaurant);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtAllergies = itemView.findViewById(R.id.tvSelectedAllergies);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            spinnerHour = itemView.findViewById(R.id.spinnerHour);
            spinnerGuests = itemView.findViewById(R.id.spinnerGuests);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);


            // Spinner horas
            String[] timeOptions = {"08:30", "09:00", "09:30", "10:00", "10:30", "11:00"};

            ArrayAdapter<String> adapterHour = new ArrayAdapter<String>(itemView.getContext(), android.R.layout.simple_spinner_item, timeOptions) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    TextView v = (TextView) super.getView(position, convertView, parent);
                    v.setTextColor(Color.BLACK); // color del texto del spinner seleccionado
                    return v;
                }

                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    TextView v = (TextView) super.getDropDownView(position, convertView, parent);
                    v.setTextColor(Color.parseColor("#8B6D5C")); // color del texto en el dropdown
                    return v;
                }
            };

            adapterHour.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerHour.setAdapter(adapterHour);
            spinnerHour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedHour = timeOptions[position];
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });


            // Spinner comensales
            Integer[] guestsOptions = {1,2,3,4,5,6,7,8};
            ArrayAdapter<Integer> adapterGuests = new ArrayAdapter<Integer>(itemView.getContext(), android.R.layout.simple_spinner_item, guestsOptions) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    TextView tv = (TextView) super.getView(position, convertView, parent);
                    tv.setTextColor(Color.BLACK);  // Vista cerrada del spinner en color negro
                    return tv;
                }

                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    TextView tv = (TextView) super.getDropDownView(position, convertView, parent);
                    tv.setTextColor(Color.BLACK);  // Elementos desplegables en color negro
                    return tv;
                }
            };
            adapterGuests.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerGuests.setAdapter(adapterGuests);


            // Fecha solo editable en edición
            txtDate.setOnClickListener(v -> {
                if (!isEditing) return;
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(itemView.getContext(), (view, year, month, dayOfMonth) -> {
                    String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
                    txtDate.setText(selectedDate);
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            });

            // Editar alergias solo en edición
            txtAllergies.setOnClickListener(v -> {
                if (!isEditing) return;

                String currentText = txtAllergies.getText().toString();
                boolean[] checkedItems = new boolean[allergyOptions.length];
                ArrayList<String> selectedItems = new ArrayList<>();

                if (!currentText.equals("Pulsa para seleccionar")) {
                    String[] existing = currentText.split(", ");
                    for (int i = 0; i < allergyOptions.length; i++) {
                        for (String s : existing) {
                            if (s.equalsIgnoreCase(allergyOptions[i])) {
                                checkedItems[i] = true;
                                selectedItems.add(allergyOptions[i]);
                                break;
                            }
                        }
                    }
                }

                new androidx.appcompat.app.AlertDialog.Builder(itemView.getContext())
                        .setTitle("Selecciona alergias")
                        .setMultiChoiceItems(allergyOptions, checkedItems, (dialog, which, isChecked) -> {
                            String item = allergyOptions[which];
                            if (isChecked && !selectedItems.contains(item)) {
                                selectedItems.add(item);
                            } else if (!isChecked) {
                                selectedItems.remove(item);
                            }
                        })
                        .setPositiveButton("Aceptar", (dialog, which) -> {
                            if (selectedItems.isEmpty()) {
                                txtAllergies.setText("Ninguna");
                            } else {
                                txtAllergies.setText(TextUtils.join(", ", selectedItems));
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .setNeutralButton("Limpiar", (dialog, which) -> txtAllergies.setText("Ninguna"))
                        .show();
            });

            setEditingEnabled(false);

            btnEdit.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos == RecyclerView.NO_POSITION) return;

                ReservationDTO r = reservations.get(pos);

                if (!isEditing) {
                    isEditing = true;
                    btnEdit.setText("Guardar");
                    btnEdit.setTextColor(Color.parseColor("#4CAF50")); // Verde suave para modo edición
                    setEditingEnabled(true);
                } else {
                    if (selectedHour == null) {
                        Toast.makeText(itemView.getContext(), "Selecciona una hora", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String localDateTime = txtDate.getText().toString() + "T" + selectedHour + ":00";
                    String utcDateTime = convertToUTC(localDateTime);

                    r.setReservationDate(utcDateTime);
                    r.setNumGuests((Integer) spinnerGuests.getSelectedItem());
                    r.setFoodAllergies(txtAllergies.getText().toString());

                    isEditing = false;
                    btnEdit.setText("Editar");
                    btnEdit.setTextColor(Color.parseColor("#FFFFFF")); // Blanco normal
                    setEditingEnabled(false);

                    apiService.updateReservation(r.getReservationId(), r).enqueue(new Callback<ReservationDTO>() {
                        @Override
                        public void onResponse(Call<ReservationDTO> call, Response<ReservationDTO> response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(itemView.getContext(), "Error al actualizar", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ReservationDTO> call, Throwable t) {
                            Toast.makeText(itemView.getContext(), "Fallo de red", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });


            btnDelete.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos == RecyclerView.NO_POSITION) return;

                ReservationDTO r = reservations.get(pos);

                apiService.cancelReservation(r.getReservationId()).enqueue(new Callback<Void>() {
                    @Override public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            reservations.remove(pos);
                            notifyItemRemoved(pos);
                        } else {
                            Toast.makeText(itemView.getContext(), "Error al eliminar", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(itemView.getContext(), "Fallo de red", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }

        private void setEditingEnabled(boolean enabled) {
            txtDate.setEnabled(enabled);
            spinnerHour.setEnabled(enabled);
            spinnerGuests.setEnabled(enabled);
            txtAllergies.setClickable(enabled);

            // Cambia el color del texto de txtDate y txtAllergies
            int textColor = enabled ? Color.parseColor("#0D47A1") /* azul oscuro */ : Color.BLACK;
            txtDate.setTextColor(textColor);
            txtAllergies.setTextColor(textColor);
            txtDate.setInputType(enabled ? InputType.TYPE_CLASS_DATETIME : InputType.TYPE_NULL);

            // Actualiza el color del texto en la vista seleccionada del spinner de horas
            View selectedViewHour = spinnerHour.getSelectedView();
            if (selectedViewHour instanceof TextView) {
                ((TextView) selectedViewHour).setTextColor(textColor);
            }

            // Actualiza el color del texto en la vista seleccionada del spinner de comensales
            View selectedViewGuests = spinnerGuests.getSelectedView();
            if (selectedViewGuests instanceof TextView) {
                ((TextView) selectedViewGuests).setTextColor(textColor);
            }
        }

        private String convertToUTC(String localDateTime) {
            try {
                SimpleDateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                localFormat.setTimeZone(TimeZone.getDefault());
                Date date = localFormat.parse(localDateTime);

                SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
                utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                return utcFormat.format(date);
            } catch (Exception e) {
                e.printStackTrace();
                return localDateTime;
            }
        }
    }

    @NonNull
    @Override
    public ReservationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationsAdapter.ViewHolder holder, int position) {
        ReservationDTO r = reservations.get(position);
        holder.txtRestaurant.setText(r.getRestaurantName());
        holder.txtUsername.setText(r.getUsername());


        // Convertir UTC a fecha local
        try {
            SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
            utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = utcFormat.parse(r.getReservationDate());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            dateFormat.setTimeZone(TimeZone.getDefault());
            holder.txtDate.setText(dateFormat.format(date));

            SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            hourFormat.setTimeZone(TimeZone.getDefault());
            String localHour = hourFormat.format(date);

            ArrayAdapter<String> hourAdapter = (ArrayAdapter<String>) holder.spinnerHour.getAdapter();
            int posHour = hourAdapter.getPosition(localHour);
            if (posHour >= 0) {
                holder.spinnerHour.setSelection(posHour);
                holder.selectedHour = localHour;
            }
        } catch (Exception e) {
            e.printStackTrace();
            holder.txtDate.setText(r.getReservationDate().split("T")[0]);
        }

        ArrayAdapter<Integer> guestsAdapter = (ArrayAdapter<Integer>) holder.spinnerGuests.getAdapter();
        int posGuests = guestsAdapter.getPosition(r.getNumGuests());
        if (posGuests >= 0) {
            holder.spinnerGuests.setSelection(posGuests);
        }

        String allergies = r.getFoodAllergies();
        holder.txtAllergies.setText(allergies == null || allergies.isEmpty() ? "Pulsa para seleccionar" : allergies);

        holder.isEditing = false;
        holder.btnEdit.setText("Editar");
        holder.setEditingEnabled(false);
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }
}
