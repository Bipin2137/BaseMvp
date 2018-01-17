package com.dexter.basemvpwithoutdagger.database.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.dexter.basemvpwithoutdagger.api.response.DetailResponse;
import com.dexter.basemvpwithoutdagger.database.beans.SearchBean;
import com.dexter.basemvpwithoutdagger.database.converters.ListToStringConverter;
import com.dexter.basemvpwithoutdagger.database.dao.DetailDao;
import com.dexter.basemvpwithoutdagger.database.dao.SearchDao;

/**
 * Created by Khatr on 12/31/2017.
 */

@Database(entities = {SearchBean.class, DetailResponse.class}, version = 1, exportSchema = false)
@TypeConverters(ListToStringConverter.class)
public abstract class CineWorldDb extends RoomDatabase {
    public abstract SearchDao searchDao();

    public abstract DetailDao detailDao();
}
