package com.example.herexamenEvenementenKelseyDetremmerie;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class FavoritesFragment extends Fragment {
    private FavoriteViewModel favoriteViewModel;
    private FavoritesAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorieten, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Favorieten");

        setHasOptionsMenu(true);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_fav);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setHasFixedSize(true);

        adapter = new FavoritesAdapter();
        recyclerView.setAdapter(adapter);

        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        favoriteViewModel.getAllFavorites().observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> favorites) {
                adapter.submitList(favorites);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                favoriteViewModel.delete(adapter.getFavoriteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Verwijdert uit favorieten", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListenerFavorite(new FavoritesAdapter.OnItemClickListenerFavorite() {
            @Override
            public void onItemClickFavorite(Event event) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(EventFragment.EVENT_TITEL, event.getTitel());
                intent.putExtra(EventFragment.EVENT_OMSCHRIJVING, event.getOmschrijving());
                intent.putExtra(EventFragment.EVENT_LOCATIE, event.getLocatie());
                intent.putExtra(EventFragment.EVENT_PRIJS, event.getPrijs());
                intent.putExtra(EventFragment.EVENT_DATUM, event.getDatum());
                startActivity(intent);
            }
        });

        return view;
    }

    public void setFragmentArguments() {
        if (getArguments() != null) {
            String titel = getArguments().getString(EventFragment.EVENT_TITEL);
            String omschrijving = getArguments().getString(EventFragment.EVENT_OMSCHRIJVING);
            String locatie = getArguments().getString(EventFragment.EVENT_LOCATIE);
            String prijs = getArguments().getString(EventFragment.EVENT_PRIJS);
            String datum = getArguments().getString(EventFragment.EVENT_DATUM);
            Event event = new Event(titel, omschrijving, locatie, prijs, datum);
            favoriteViewModel.insert(event);
        }
    }

    public void deleteFavorite() throws ExecutionException, InterruptedException {
        Event event = favoriteViewModel.searchFavorite(getArguments().getString(EventFragment.EVENT_TITEL));
        favoriteViewModel.delete(event);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.favorites_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_favs:
                favoriteViewModel.deleteAll();
                Toast.makeText(getActivity(), "Alle favorieten zijn verwijderd", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
