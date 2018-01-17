package com.dexter.basemvpwithoutdagger.activities.search;

import com.dexter.basemvpwithoutdagger.api.response.SearchResponse;
import com.dexter.basemvpwithoutdagger.database.beans.SearchBean;

import java.util.List;

/**
 * Created by Khatr on 12/31/2017.
 */

public interface SearchView {

    void showProgressDialog();

    void removeProgressDialog();

    void onFailure(String error);

    void onSuccess(SearchResponse searchResponse);

    void onCacheSuccess(List<SearchBean> searchBeanList);
}
