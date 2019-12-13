package com.example.herexamenEvenementenKelseyDetremmerie;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_table")
public class Event {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String Titel;
    private String Omschrijving;
    private String Locatie;
    private String Prijs;
    private String Datum;

    @Ignore
    public Event() {
        //nul-argumenten constructor nodig voor firebase
    }

    public Event(String Titel, String Omschrijving, String Locatie, String Prijs, String Datum) {
        this.Titel = Titel;
        this.Omschrijving = Omschrijving;
        this.Locatie = Locatie;
        this.Prijs = Prijs;
        this.Datum = Datum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitel() {
        return Titel;
    }

    public String getOmschrijving() {
        return Omschrijving;
    }

    public String getLocatie() {
        return Locatie;
    }

    public String getPrijs() {
        return Prijs;
    }

    public String getDatum() {
        return Datum;
    }

    public String getGemeente() {
        String[] woorden = Locatie.split(" ");
        String gemeente = woorden[woorden.length - 1];
        return gemeente;
    }
}
