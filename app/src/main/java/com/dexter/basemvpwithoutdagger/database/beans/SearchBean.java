package com.dexter.basemvpwithoutdagger.database.beans;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.dexter.basemvpwithoutdagger.utils.Constants;

/**
 * Created by Khatr on 12/31/2017.
 */

@Entity(tableName = Constants.TABLE_NAME)
public class SearchBean {

    /**
     * Title : Guardians of the Galaxy
     * Year : 2014
     * imdbID : tt2015381
     * Type : movie
     * Poster : https://images-na.ssl-images-amazon.com/images/M/MV5BMTAwMjU5OTgxNjZeQTJeQWpwZ15BbWU4MDUxNDYxODEx._V1_SX300.jpg
     */

    private String Title;
    private String Year;
    @PrimaryKey
    @NonNull
    private String imdbID;
    private String Type;
    private String Poster;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String Year) {
        this.Year = Year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String Poster) {
        this.Poster = Poster;
    }
}

