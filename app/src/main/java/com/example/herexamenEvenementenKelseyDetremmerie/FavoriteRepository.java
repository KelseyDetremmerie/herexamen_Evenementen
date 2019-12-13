package com.example.herexamenEvenementenKelseyDetremmerie;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class FavoriteRepository {
    private FavoriteDAO favoriteDAO;
    private LiveData<List<Event>> allFavorites;

    public FavoriteRepository(Application application) {
        FavoriteDatabase database = FavoriteDatabase.getInstance(application);
        favoriteDAO = database.favoriteDAO();
        allFavorites = favoriteDAO.getAllFavorites();
    }

    public void insert(Event event) {
        new InsertFavoriteAsyncTask(favoriteDAO).execute(event);
    }

    public void delete(Event event) {
        new DeleteFavoriteAsyncTask(favoriteDAO).execute(event);
    }

    public void deleteAllFavorites() {
        new DeleteAllFavoritesAsyncTask(favoriteDAO).execute();
    }

    public LiveData<List<Event>> getAllFavorites() {
        return allFavorites;
    }

    public Event searchFavorite(String titel) throws ExecutionException, InterruptedException {
        Event event = new SearchFavoriteAsyncTask(favoriteDAO, titel).execute().get();
        return event;
    }

    private static class InsertFavoriteAsyncTask extends AsyncTask<Event, Void, Void> {
        private FavoriteDAO favoriteDAO;

        private InsertFavoriteAsyncTask(FavoriteDAO favoriteDAO) {
            this.favoriteDAO = favoriteDAO;
        }

        @Override
        protected Void doInBackground(Event... events) {
            favoriteDAO.insert(events[0]);
            return null;
        }
    }

    private static class DeleteFavoriteAsyncTask extends AsyncTask<Event, Void, Void> {
        private FavoriteDAO favoriteDAO;

        private DeleteFavoriteAsyncTask(FavoriteDAO favoriteDAO) {
            this.favoriteDAO = favoriteDAO;
        }

        @Override
        protected Void doInBackground(Event... events) {
            favoriteDAO.delete(events[0]);
            return null;
        }
    }

    private static class DeleteAllFavoritesAsyncTask extends AsyncTask<Void, Void, Void> {
        private FavoriteDAO favoriteDAO;

        private DeleteAllFavoritesAsyncTask(FavoriteDAO favoriteDAO) {
            this.favoriteDAO = favoriteDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            favoriteDAO.deleteAllFavorites();
            return null;
        }
    }

    private static class SearchFavoriteAsyncTask extends AsyncTask<Void, Void, Event> {
        private FavoriteDAO favoriteDAO;
        private String titel;

        private SearchFavoriteAsyncTask(FavoriteDAO favoriteDAO, String titel) {
            this.favoriteDAO = favoriteDAO;
            this.titel = titel;
        }

        @Override
        protected Event doInBackground(Void... voids) {
            return favoriteDAO.searchFavorite(titel);
        }
    }
}
