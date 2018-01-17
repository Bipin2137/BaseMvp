package com.dexter.basemvpwithoutdagger.activities.detail;

import android.content.Context;

import com.dexter.basemvpwithoutdagger.BuildConfig;
import com.dexter.basemvpwithoutdagger.CineWorldApp;
import com.dexter.basemvpwithoutdagger.api.BaseApi;
import com.dexter.basemvpwithoutdagger.api.response.DetailResponse;
import com.dexter.basemvpwithoutdagger.api.service.OmdbApiInterface;
import com.dexter.basemvpwithoutdagger.database.db.CineWorldDb;
import com.dexter.basemvpwithoutdagger.utils.Constants;

import java.util.HashMap;

/**
 * Created by Khatr on 12/31/2017.
 */

public class DetailPresenter {

    private DetailView detailView;
    private String imdbId;
    private boolean shouldFetchFromCache;
    private Context context;

    void setContext(Context context) {
        this.context = context;
    }

    void setDetailView(DetailView detailView) {
        this.detailView = detailView;
    }

    void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    void setShouldFetchFromCache(boolean shouldFetchFromCache) {
        this.shouldFetchFromCache = shouldFetchFromCache;
    }

    void getDetailResponse() {

        OmdbApiInterface omdbApiInterface = BaseApi.getClient(context).create(OmdbApiInterface.class);

        DetailApiCall detailApiCall = new DetailApiCall(omdbApiInterface);

        CineWorldDb cineWorldDb = CineWorldApp.initRoomDatabase();

        detailView.showProgressDialog();

        HashMap<String, String> map = new HashMap<>();
        map.put(Constants.apiKey, BuildConfig.API_KEY);
        map.put(Constants.id, imdbId);
        map.put(Constants.plot, Constants.full);

        detailApiCall.getApiData(cineWorldDb, shouldFetchFromCache, map, new DetailApiCall.GetDetailCallback() {
            @Override
            public void onNext(DetailResponse detailResponse) {
                detailView.removeProgressDialog();
                detailView.onSuccess(detailResponse);
            }

            @Override
            public void onError(String error) {
                detailView.removeProgressDialog();
                detailView.onFailure(error);
            }
        });
    }
}
