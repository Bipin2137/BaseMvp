package com.dexter.basemvpwithoutdagger.database.converters;

import android.arch.persistence.room.TypeConverter;

import com.dexter.basemvpwithoutdagger.api.response.DetailResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Khatr on 12/31/2017.
 */

public class ListToStringConverter {
    @TypeConverter
    public static List<DetailResponse.RatingsBean> fromString(String value) {
        Type listType = new TypeToken<List<DetailResponse.RatingsBean>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(List<DetailResponse.RatingsBean> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
