package com.dexter.basemvpwithoutdagger.activities.detail;

import android.os.AsyncTask;

import com.dexter.basemvpwithoutdagger.api.response.DetailResponse;
import com.dexter.basemvpwithoutdagger.api.service.OmdbApiInterface;
import com.dexter.basemvpwithoutdagger.database.db.CineWorldDb;
import com.dexter.basemvpwithoutdagger.utils.BaseRxJava2;
import com.dexter.basemvpwithoutdagger.utils.Constants;

import java.util.HashMap;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Khatr on 12/31/2017.
 */

public class DetailApiCall {
    private OmdbApiInterface omdbApiInterface;
    private CompositeDisposable compositeDisposable;
    private Disposable disposable;
    private BaseRxJava2<DetailResponse> baseRxJava2;

    public DetailApiCall(OmdbApiInterface omdbApiInterface) {
        this.omdbApiInterface = omdbApiInterface;
        compositeDisposable = new CompositeDisposable();
        baseRxJava2 = new BaseRxJava2<>();
    }

    void getApiData(CineWorldDb cineWorldDb, boolean shouldFetchFromDatabase, HashMap<String, String> map, final GetDetailCallback callback) {
        if (shouldFetchFromDatabase) {
            String s = map.get(Constants.id);
            Flowable<DetailResponse> detailBeanFlowable = cineWorldDb.detailDao().getSearchResult(s);
            getResponseFromCache(detailBeanFlowable, callback);
        } else {
            getResponseFromApi(cineWorldDb, map, callback);
        }
    }

    private void getResponseFromApi(CineWorldDb cineWorldDb, HashMap<String, String> map, GetDetailCallback callback) {
        Flowable<DetailResponse> details = omdbApiInterface.getDetails(map);
        disposable = baseRxJava2.subscribe(details, new BaseRxJava2.DataInterface<DetailResponse>() {
            @Override
            public void onNext(DetailResponse response) {
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

    private void getResponseFromCache(Flowable<DetailResponse> detailBeanFlowable, GetDetailCallback callback) {
        disposable = baseRxJava2.subscribe(detailBeanFlowable, new BaseRxJava2.DataInterface<DetailResponse>() {
            @Override
            public void onNext(DetailResponse response) {
                callback.onNext(response);
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

    public interface GetDetailCallback {
        void onNext(DetailResponse detailResponse);

        void onError(String error);
    }

    private static class InsertTask extends AsyncTask<String, String, String> {

        private CineWorldDb cineWorldDb;
        private DetailResponse detailResponse;

        InsertTask(CineWorldDb cineWorldDb, DetailResponse detailResponse) {
            this.cineWorldDb = cineWorldDb;
            this.detailResponse = detailResponse;
        }

        @Override
        protected String doInBackground(String... strings) {
            cineWorldDb.detailDao().insert(detailResponse);
            return null;
        }
    }
}
