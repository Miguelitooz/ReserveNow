package com.example.tfgAndroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.tfgAndroid.Fragments.CreateReservationFragment;
import com.example.tfgAndroid.Fragments.ProfileFragment;
import com.example.tfgAndroid.Fragments.RestaurantsFragment;
import com.example.tfgAndroid.Fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainMenuActivity extends AppCompatActivity {

    private static final String ACTION_LOGOUT = "com.example.tfgAndroid.ACTION_LOGOUT";

    private ProfileFragment profileFragment;
    private CreateReservationFragment createReservationFragment;
    private RestaurantsFragment restaurantsFragment;
    private SettingsFragment settingsFragment;

    // Índice para saber qué fragment está activo
    private int currentFragmentIndex = 0;

    private BroadcastReceiver logoutReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION_LOGOUT.equals(intent.getAction())) {
                Intent loginIntent = new Intent(MainMenuActivity.this, LoginActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        profileFragment = new ProfileFragment();
        createReservationFragment = new CreateReservationFragment();
        restaurantsFragment = new RestaurantsFragment();
        settingsFragment = new SettingsFragment();

        setCurrentFragment(profileFragment, 0);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Fragment current = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

                if (id == R.id.nav_profile) {
                    if (current instanceof ProfileFragment) {
                        ((ProfileFragment) current).recargarReservas();
                    } else {
                        setCurrentFragment(profileFragment, 0);
                    }
                    return true;
                } else if (id == R.id.nav_reserve) {
                    setCurrentFragment(createReservationFragment, 1);
                    return true;
                } else if (id == R.id.nav_restaurants) {
                    setCurrentFragment(restaurantsFragment, 2);
                    return true;
                } else if (id == R.id.nav_settings) {
                    setCurrentFragment(settingsFragment, 3);
                    return true;
                } else {
                    return false;
                }
            }
        });

        // Registrar el receiver correctamente
        ContextCompat.registerReceiver(
                this,
                logoutReceiver,
                new IntentFilter(ACTION_LOGOUT),
                ContextCompat.RECEIVER_NOT_EXPORTED
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Desregistrar receiver para evitar leaks
        unregisterReceiver(logoutReceiver);
    }

    private void setCurrentFragment(Fragment fragment, int newIndex) {
        boolean isGoingRight = newIndex > currentFragmentIndex;
        currentFragmentIndex = newIndex;

        if (isGoingRight) {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(
                            R.anim.slide_in_right,  // nuevo fragment entra desde derecha
                            R.anim.slide_out_left   // fragment actual sale hacia izquierda
                    )
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(
                            R.anim.slide_in_left,   // nuevo fragment entra desde izquierda
                            R.anim.slide_out_right  // fragment actual sale hacia derecha
                    )
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
