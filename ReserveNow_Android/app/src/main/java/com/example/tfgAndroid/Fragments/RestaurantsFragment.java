package com.example.tfgAndroid.Fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton; // NUEVO: Import para ImageButton
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.tfgAndroid.R;

public class RestaurantsFragment extends Fragment {

    private LinearLayout menuContainer1, menuContainer2, menuContainer3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurants, container, false);

        // Contenedores menú
        menuContainer1 = view.findViewById(R.id.menuContainer1);
        menuContainer2 = view.findViewById(R.id.menuContainer2);
        menuContainer3 = view.findViewById(R.id.menuContainer3);

        // CardViews restaurantes
        CardView card1 = view.findViewById(R.id.restaurant1Container);
        CardView card2 = view.findViewById(R.id.restaurant2Container);
        CardView card3 = view.findViewById(R.id.restaurant3Container);

        // NUEVO: Encontrar los botones de GPS por su ID
        ImageButton gpsButton1 = view.findViewById(R.id.gpsButton1);
        ImageButton gpsButton2 = view.findViewById(R.id.gpsButton2);
        ImageButton gpsButton3 = view.findViewById(R.id.gpsButton3);


        // Cargar menús con categorías
        setupMenu(menuContainer1, "Platos", new String[][]{
                {"Pizza Margarita", "8.50€"}, {"Pasta Carbonara", "9.00€"},
                {"Risotto Funghi", "10.50€"}, {"Lasagna", "9.50€"}, {"Ravioli Bolognese", "8.00€"}
        }, "Bebidas", new String[][]{
                {"Vino Tinto", "4.00€"}, {"Cerveza Artesanal", "3.50€"},
                {"Refresco", "2.00€"}, {"Agua Mineral", "1.50€"}, {"Café Expreso", "2.00€"}
        });

        // Nota: El menú del restaurante 3 parece ser de comida japonesa, no de un asador.
        // Lo dejo como estaba en tu código original.
        setupMenu(menuContainer2, "Platos", new String[][]{
                {"Chuleta de cerdo", "12.00€"}, {"Entrecot de ternera", "15.00€"},
                {"Costillas BBQ", "14.00€"}, {"Ensalada Cesar", "7.50€"}, {"Patatas Fritas", "3.00€"}
        }, "Bebidas", new String[][]{
                {"Cóctel Mojito", "6.00€"}, {"Limonada Casera", "3.00€"},
                {"Refresco Cola", "2.50€"}, {"Agua con Gas", "2.00€"}, {"Té Helado", "3.00€"}
        });

        setupMenu(menuContainer3, "Platos", new String[][]{
                {"Nigiri Salmón", "4.50€"}, {"Uramaki Especial", "6.00€"},
                {"Sashimi Variado", "12.00€"}, {"Tempura Camarón", "8.00€"}, {"Miso Soup", "3.50€"}
        }, "Bebidas", new String[][]{
                {"Sake Japonés", "5.50€"}, {"Té Verde Matcha", "4.00€"},
                {"Agua Filtrada", "1.50€"}, {"Refresco de Jengibre", "3.50€"}, {"Cerveza Japonesa", "5.00€"}
        });

        // Click listeners para los CARDS (mostrar/ocultar menú)
        card1.setOnClickListener(v -> toggleMenuVisibility(menuContainer1));
        card2.setOnClickListener(v -> toggleMenuVisibility(menuContainer2));
        card3.setOnClickListener(v -> toggleMenuVisibility(menuContainer3));

        // Las direcciones son ejemplos reales de Sevilla. ¡Cámbialas por las correctas!
        gpsButton1.setOnClickListener(v -> openMap("C. Harinas, 16, Casco Antiguo, 41001 Sevilla"));
        gpsButton2.setOnClickListener(v -> openMap("C. San Jacinto, 89, Triana, 41010 Sevilla"));
        gpsButton3.setOnClickListener(v -> openMap("Av. de la Buhaira, 23, Nervión, 41018 Sevilla"));


        return view;
    }

    private void openMap(String address) {
        Toast.makeText(getContext(), "Abriendo mapa para: " + address, Toast.LENGTH_SHORT).show();

        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + Uri.encode(address));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

        try {
            startActivity(mapIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(), "No se ha encontrado ninguna aplicación de mapas.", Toast.LENGTH_LONG).show();
        }
    }

    private void setupMenu(LinearLayout container, String comidaTitulo, String[][] comidaItems, String bebidaTitulo, String[][] bebidaItems) {
        container.removeAllViews();
        agregarTituloSeccion(container, comidaTitulo);
        agregarItems(container, comidaItems);
        View sectionDivider = new View(getContext());
        LinearLayout.LayoutParams sepParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 4);
        sepParams.setMargins(0, 16, 0, 16);
        sectionDivider.setLayoutParams(sepParams);
        sectionDivider.setBackgroundColor(0xFFAAAAAA);
        container.addView(sectionDivider);
        agregarTituloSeccion(container, bebidaTitulo);
        agregarItems(container, bebidaItems);
    }

    private void agregarTituloSeccion(LinearLayout container, String titulo) {
        TextView titleView = new TextView(getContext());
        titleView.setText(titulo);
        titleView.setTextSize(22);
        titleView.setTypeface(null, Typeface.BOLD);
        titleView.setTextColor(0xFF333333);
        titleView.setPadding(0, 16, 0, 8);
        container.addView(titleView);
    }

    private void agregarItems(LinearLayout container, String[][] items) {
        for (String[] item : items) {
            LinearLayout itemLayout = new LinearLayout(getContext());
            itemLayout.setOrientation(LinearLayout.HORIZONTAL);
            itemLayout.setPadding(0, 8, 0, 8);
            TextView name = new TextView(getContext());
            name.setText(item[0]);
            name.setTextSize(18);
            name.setTextColor(0xFF444444);
            name.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
            TextView price = new TextView(getContext());
            price.setText(item[1]);
            price.setTextSize(18);
            price.setTypeface(null, Typeface.BOLD);
            price.setTextColor(0xFF008577);
            price.setGravity(android.view.Gravity.END);
            itemLayout.addView(name);
            itemLayout.addView(price);
            container.addView(itemLayout);
            View separator = new View(getContext());
            LinearLayout.LayoutParams sepParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
            separator.setLayoutParams(sepParams);
            separator.setBackgroundColor(0xFFCCCCCC);
            container.addView(separator);
        }
    }

    private void toggleMenuVisibility(LinearLayout menu) {
        menu.setVisibility(menu.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }
}