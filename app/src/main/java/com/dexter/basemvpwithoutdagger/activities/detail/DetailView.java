package com.dexter.basemvpwithoutdagger.activities.detail;

import com.dexter.basemvpwithoutdagger.api.response.DetailResponse;

/**
 * Created by Khatr on 12/31/2017.
 */

public interface DetailView {

    void showProgressDialog();

    void removeProgressDialog();

    void onFailure(String error);

    void onSuccess(DetailResponse detailResponse);
}
