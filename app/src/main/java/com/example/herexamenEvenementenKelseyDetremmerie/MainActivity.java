package com.example.herexamenEvenementenKelseyDetremmerie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.navigation.NavigationView;

import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private FavoritesFragment favoritesFragment = new FavoritesFragment();
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                sendDataToFragment(intent);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                favoritesFragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new EventFragment()).commit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) { //wanneer app in achtergrond draait / scherm draait, dan wordt het fragment niet opnieuw ingeladen
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new EventFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("DETAILS_INTENT"));
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            if(fragment instanceof EventFragment) {
                super.onBackPressed();
                System.exit(0);
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new EventFragment()).commit();
                break;
            case R.id.nav_fav:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        favoritesFragment).commit();
                break;
            case R.id.nav_info:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new InfoFragment()).commit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void sendDataToFragment(Intent intent) throws ExecutionException, InterruptedException {
        boolean button = intent.getBooleanExtra("Favorited", true);
        String title = intent.getStringExtra(EventFragment.EVENT_TITEL);
        String omschrijving = intent.getStringExtra(EventFragment.EVENT_OMSCHRIJVING);
        String locatie = intent.getStringExtra(EventFragment.EVENT_LOCATIE);
        String prijs = intent.getStringExtra(EventFragment.EVENT_PRIJS);
        String datum = intent.getStringExtra(EventFragment.EVENT_DATUM);
        Bundle bundle = new Bundle();
        bundle.putString(EventFragment.EVENT_TITEL, title);
        bundle.putString(EventFragment.EVENT_OMSCHRIJVING, omschrijving);
        bundle.putString(EventFragment.EVENT_LOCATIE, locatie);
        bundle.putString(EventFragment.EVENT_PRIJS, prijs);
        bundle.putString(EventFragment.EVENT_DATUM, datum);

        if (button) {
            favoritesFragment.setArguments(bundle);
            favoritesFragment.setFragmentArguments();
        } else {
            favoritesFragment.deleteFavorite();
        }
    }
}
