package com.dexter.basemvpwithoutdagger.activities.search;

import android.os.AsyncTask;

import com.dexter.basemvpwithoutdagger.api.response.SearchResponse;
import com.dexter.basemvpwithoutdagger.api.service.OmdbApiInterface;
import com.dexter.basemvpwithoutdagger.database.beans.SearchBean;
import com.dexter.basemvpwithoutdagger.database.db.CineWorldDb;
import com.dexter.basemvpwithoutdagger.utils.BaseRxJava2;
import com.dexter.basemvpwithoutdagger.utils.Constants;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Khatr on 12/31/2017.
 */

public class SearchApiCall {

    private OmdbApiInterface omdbApiInterface;
    private CompositeDisposable compositeDisposable;
    private Disposable disposable;
    private BaseRxJava2<SearchResponse> baseRxJava2;
    private BaseRxJava2<List<SearchBean>> cacheBaseRxJava2;

    public SearchApiCall(OmdbApiInterface omdbApiInterface) {
        this.omdbApiInterface = omdbApiInterface;
        compositeDisposable = new CompositeDisposable();
        baseRxJava2 = new BaseRxJava2<>();
        cacheBaseRxJava2 = new BaseRxJava2<>();
    }

    void getApiData(CineWorldDb cineWorldDb, boolean shouldFetchFromDatabase, HashMap<String, String> map, final GetSearchBeanCallback callback) {
        if (shouldFetchFromDatabase) {
            String s = "%" + map.get(Constants.searchTerm) + "%";
            Flowable<List<SearchBean>> searchResponseFlowable = cineWorldDb.searchDao().getSearchResult(s);
            getResponseFromCache(cineWorldDb, map, searchResponseFlowable, callback);
        } else {
            getResponseFromApi(cineWorldDb, map, callback);
        }
    }

    private void getResponseFromApi(CineWorldDb cineWorldDb, HashMap<String, String> map, GetSearchBeanCallback callback) {
        Flowable<SearchResponse> searchResult = omdbApiInterface.getSearchResult(map);
        disposable = baseRxJava2.subscribe(searchResult, new BaseRxJava2.DataInterface<SearchResponse>() {
            @Override
            public void onNext(SearchResponse response) {
                if (response.getResponse().equalsIgnoreCase("true")) {
                    new InsertTask(cineWorldDb, response).execute();
                    callback.onNext(response);
                } else {
                    callback.onError("Result Not Found");
                }
            }

            @Override
            public void onError(String error) {
                callback.onError("Result Not Found");
            }

            @Override
            public void onComplete() {
                disposable.dispose();
                compositeDisposable.clear();
            }
        });
        compositeDisposable.add(disposable);
    }

    public interface GetSearchBeanCallback {
        void onNext(SearchResponse searchResponse);

        void onCacheNext(List<SearchBean> searchBeanList);

        void onError(String error);
    }

    private static class InsertTask extends AsyncTask<String, String, String> {

        private CineWorldDb cineWorldDb;
        private SearchResponse searchResponse;

        InsertTask(CineWorldDb cineWorldDb, SearchResponse searchResponse) {
            this.cineWorldDb = cineWorldDb;
            this.searchResponse = searchResponse;
        }

        @Override
        protected String doInBackground(String... strings) {
            cineWorldDb.searchDao().insert(searchResponse.getSearch());
            return null;
        }
    }

    private void getResponseFromCache(CineWorldDb cineWorldDb, HashMap<String, String> map, Flowable<List<SearchBean>> searchResponseFlowable, GetSearchBeanCallback callback) {
        disposable = cacheBaseRxJava2.subscribe(searchResponseFlowable, new BaseRxJava2.DataInterface<List<SearchBean>>() {

            @Override
            public void onNext(List<SearchBean> response) {
                if (response != null && !response.isEmpty()) {
                    callback.onCacheNext(response);
                } else {
                    getResponseFromApi(cineWorldDb, map, callback);
                }
            }

            @Override
            public void onError(String error) {
                callback.onError("Result Not Found");
            }

            @Override
            public void onComplete() {
                disposable.dispose();
                compositeDisposable.clear();
            }
        });
        compositeDisposable.add(disposable);
    }
}
