package com.dexter.basemvpwithoutdagger.api.service;

import com.dexter.basemvpwithoutdagger.api.response.DetailResponse;
import com.dexter.basemvpwithoutdagger.api.response.SearchResponse;

import java.util.HashMap;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Khatr on 12/31/2017.
 */

public interface OmdbApiInterface {

    @GET("/")
    Flowable<SearchResponse> getSearchResult(@QueryMap HashMap<String, String> map);

    @GET("/")
    Flowable<DetailResponse> getDetails(@QueryMap HashMap<String, String> map);
}
