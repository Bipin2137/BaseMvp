package com.dexter.basemvpwithoutdagger.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.dexter.basemvpwithoutdagger.api.response.DetailResponse;
import com.dexter.basemvpwithoutdagger.utils.Constants;

import io.reactivex.Flowable;

/**
 * Created by Khatr on 12/31/2017.
 */

@Dao
public interface DetailDao {

    @Query("SELECT * FROM " + Constants.TABLE_DETAIL + " Where imdbID = :imdbID")
    Flowable<DetailResponse> getSearchResult(String imdbID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DetailResponse detailResponse);

    @Query("DELETE FROM " + Constants.TABLE_NAME)
    void deleteAll();
}
