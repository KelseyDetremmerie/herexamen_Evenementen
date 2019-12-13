package com.example.herexamenEvenementenKelseyDetremmerie;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteDAO {

    @Insert
    void insert(Event event);

    @Delete
    void delete(Event event);

    @Query("DELETE FROM favorite_table")
    void deleteAllFavorites();

    @Query("SELECT DISTINCT * FROM favorite_table")
        //TODO: order by
    LiveData<List<Event>> getAllFavorites(); //veranderingen van waarde = direct updaten

    @Query("SELECT * FROM favorite_table WHERE Titel = :titel")
    Event searchFavorite(String titel);
}
