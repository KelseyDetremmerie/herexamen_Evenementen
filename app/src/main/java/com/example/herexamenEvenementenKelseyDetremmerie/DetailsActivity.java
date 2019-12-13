package com.example.herexamenEvenementenKelseyDetremmerie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.ExecutionException;

public class DetailsActivity extends AppCompatActivity {
    private boolean favoriteBool = true;
    private FavoriteRepository favoriteRepository = new FavoriteRepository(getApplication());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        final String titel = intent.getStringExtra(EventFragment.EVENT_TITEL);
        final String omschrijving = intent.getStringExtra(EventFragment.EVENT_OMSCHRIJVING);
        final String locatie = intent.getStringExtra(EventFragment.EVENT_LOCATIE);
        final String prijs = intent.getStringExtra(EventFragment.EVENT_PRIJS);
        final String datum = intent.getStringExtra(EventFragment.EVENT_DATUM);

        final TextView textViewTitel = findViewById(R.id.details_titel);
        TextView textViewOmschrijving = findViewById(R.id.details_omschrijving);
        TextView textViewLocatie = findViewById(R.id.details_locatie);
        TextView textViewPrijs = findViewById(R.id.details_prijs);
        TextView textViewDatum = findViewById(R.id.details_datum);

        textViewTitel.setText(titel);
        textViewOmschrijving.setText(omschrijving);
        textViewLocatie.setText(locatie);
        textViewPrijs.setText(prijs);
        textViewDatum.setText(datum);

        final FloatingActionButton fabFav = findViewById(R.id.details_button);
        try {
            Event event = favoriteRepository.searchFavorite(titel);
            if (event != null) {
                fabFav.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_white));
                favoriteBool = true;
            } else {
                fabFav.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_border_white));
                favoriteBool = false;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        fabFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean favorited = false;
                favorited = checkClicked(fabFav);

                //TextView textViewTitel = findViewById(R.id.details_titel);
                //String titel = textViewTitel.getText().toString();

                Intent intent1 = new Intent("DETAILS_INTENT");
                intent1.putExtra(EventFragment.EVENT_TITEL, titel);
                intent1.putExtra(EventFragment.EVENT_OMSCHRIJVING, omschrijving);
                intent1.putExtra(EventFragment.EVENT_LOCATIE, locatie);
                intent1.putExtra(EventFragment.EVENT_PRIJS, prijs);
                intent1.putExtra(EventFragment.EVENT_DATUM, datum);
                intent1.putExtra("Favorited", favorited);
                LocalBroadcastManager.getInstance(DetailsActivity.this).sendBroadcast(intent1);
            }
        });
    }

    private boolean checkClicked(FloatingActionButton fabFav) {
        if (favoriteBool) {
            fabFav.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_border_white));
            Toast.makeText(this, "Verwijdert uit favorieten", Toast.LENGTH_SHORT).show();
            favoriteBool = false;
            return false;
        } else {
            fabFav.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_white));
            Toast.makeText(this, "Toegevoegd aan favorieten", Toast.LENGTH_SHORT).show();
            favoriteBool = true;
            return true;
        }
    }
}