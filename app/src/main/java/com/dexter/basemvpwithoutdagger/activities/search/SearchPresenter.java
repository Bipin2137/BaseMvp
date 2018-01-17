package com.dexter.basemvpwithoutdagger.activities.search;

import android.content.Context;

import com.dexter.basemvpwithoutdagger.BuildConfig;
import com.dexter.basemvpwithoutdagger.CineWorldApp;
import com.dexter.basemvpwithoutdagger.api.BaseApi;
import com.dexter.basemvpwithoutdagger.api.response.SearchResponse;
import com.dexter.basemvpwithoutdagger.api.service.OmdbApiInterface;
import com.dexter.basemvpwithoutdagger.database.beans.SearchBean;
import com.dexter.basemvpwithoutdagger.database.db.CineWorldDb;
import com.dexter.basemvpwithoutdagger.utils.Constants;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Khatr on 12/31/2017.
 */

public class SearchPresenter {

    private SearchView searchView;
    private String searchTerm;
    private int page;
    private boolean shouldFetchFromDatabase;
    private Context context;

    void setContext(Context context) {
        this.context = context;
    }

    void setSearchView(SearchView searchView) {
        this.searchView = searchView;
    }

    void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    void setPage(int page) {
        this.page = page;
    }

    void setShouldFetchFromDatabase(boolean shouldFetchFromDatabase) {
        this.shouldFetchFromDatabase = shouldFetchFromDatabase;
    }

    void getSearchResponse() {
        searchView.showProgressDialog();

        CineWorldDb cineWorldDb = CineWorldApp.initRoomDatabase();

        OmdbApiInterface omdbApiInterface = BaseApi.getClient(context).create(OmdbApiInterface.class);

        SearchApiCall searchApiCall = new SearchApiCall(omdbApiInterface);

        HashMap<String, String> map = new HashMap<>();
        map.put(Constants.apiKey, BuildConfig.API_KEY);
        map.put(Constants.searchTerm, searchTerm);
        map.put(Constants.page, String.valueOf(page));

        searchApiCall.getApiData(cineWorldDb, shouldFetchFromDatabase, map, new SearchApiCall.GetSearchBeanCallback() {
            @Override
            public void onNext(SearchResponse searchResponse) {
                searchView.removeProgressDialog();
                searchView.onSuccess(searchResponse);
            }

            @Override
            public void onCacheNext(List<SearchBean> searchBeanList) {
                searchView.removeProgressDialog();
                searchView.onCacheSuccess(searchBeanList);
            }

            @Override
            public void onError(String error) {
                searchView.removeProgressDialog();
                searchView.onFailure(error);
            }
        });
    }
}
