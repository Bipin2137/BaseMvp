package com.dexter.basemvpwithoutdagger.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.dexter.basemvpwithoutdagger.database.beans.SearchBean;
import com.dexter.basemvpwithoutdagger.utils.Constants;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Khatr on 12/31/2017.
 */

@Dao
public interface SearchDao {

    @Query("SELECT * FROM " + Constants.TABLE_NAME + " Where Title LIKE:search")
    Flowable<List<SearchBean>> getSearchResult(String search);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<SearchBean> searchBeanList);

    @Query("DELETE FROM " + Constants.TABLE_NAME)
    void deleteAll();
}
