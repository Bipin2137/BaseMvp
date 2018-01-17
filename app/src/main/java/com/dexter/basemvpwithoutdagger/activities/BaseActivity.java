package com.dexter.basemvpwithoutdagger.activities;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.dexter.basemvpwithoutdagger.R;
import com.dexter.basemvpwithoutdagger.interfaces.BaseView;
import com.dexter.basemvpwithoutdagger.utils.Constants;
import com.dexter.basemvpwithoutdagger.utils.NetworkUtils;

import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import timber.log.Timber;

/**
 * Created by Admin on 29-12-2017.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());

        //binding Butterknife here so as to avoid binding it in every view
        ButterKnife.bind(this);

        //adding tag to timber here
        Timber.tag(getTag());

        onActivityCreated();

        if (!isNetworkConnected()) {
            showMessage(Constants.error, getString(R.string.no_internet_available));
        }
    }

    /**
     * Pass the layout ID here
     *
     * @return the layout resource ID
     */
    public abstract
    @LayoutRes
    int getLayout();

    /**
     * Add the class name here
     */
    public abstract String getTag();

    public abstract void onActivityCreated();

    @Override
    public void showMessage(String type, String message) {
        switch (type) {
            case Constants.error:
                Toasty.error(this, message, Toast.LENGTH_LONG, true).show();
                break;
            case Constants.success:
                Toasty.success(this, message, Toast.LENGTH_LONG, true).show();
                break;
            case Constants.info:
                Toasty.info(this, message, Toast.LENGTH_LONG, true).show();
                break;
            case Constants.warning:
                Toasty.warning(this, message, Toast.LENGTH_LONG, true).show();
                break;
            case Constants.normal:
                Toasty.normal(this, message, Toast.LENGTH_LONG).show();
                break;
        }
    }

    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkAvailable(this);
    }
}
