package com.example.herexamenEvenementenKelseyDetremmerie;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class FavoriteViewModel extends AndroidViewModel {

    private FavoriteRepository repository;
    private LiveData<List<Event>> allFavorites;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        repository = new FavoriteRepository(application);
        allFavorites = repository.getAllFavorites();
    }

    public void insert(Event event) {
        repository.insert(event);
    }

    public void delete(Event event) {
        repository.delete(event);
    }

    public void deleteAll() {
        repository.deleteAllFavorites();
    }

    public LiveData<List<Event>> getAllFavorites() {
        return allFavorites;
    }

    public Event searchFavorite(String titel) throws ExecutionException, InterruptedException {
        return repository.searchFavorite(titel);
    }
}
