package com.example.herexamenEvenementenKelseyDetremmerie;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Event.class, version = 3)
public abstract class FavoriteDatabase extends RoomDatabase {

    private static FavoriteDatabase instance;
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

    public static synchronized FavoriteDatabase getInstance(Context context) {
        //zorgt ervoor dat er maar 1 instantie wordt gecreëerd
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), FavoriteDatabase.class, "favorite_database")
                    .fallbackToDestructiveMigration().addCallback(roomCallback).build();
        }
        return instance;
    }

    public abstract FavoriteDAO favoriteDAO();
}
