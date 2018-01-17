package com.dexter.basemvpwithoutdagger.activities.detail;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.dexter.basemvpwithoutdagger.R;
import com.dexter.basemvpwithoutdagger.activities.BaseActivity;
import com.dexter.basemvpwithoutdagger.adapter.RatingAdapter;
import com.dexter.basemvpwithoutdagger.api.response.DetailResponse;
import com.dexter.basemvpwithoutdagger.utils.Constants;
import com.dexter.basemvpwithoutdagger.utils.ImageUtils;
import com.dexter.basemvpwithoutdagger.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Khatr on 12/31/2017.
 */

public class DetailActivity extends BaseActivity implements DetailView {

    private static final String TAG = "DetailActivity";

    @BindView(R.id.ratingRecyclerView)
    RecyclerView ratingRecyclerView;

    @BindView(R.id.ivPoster)
    ImageView ivPoster;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tvType)
    TextView tvType;

    @BindView(R.id.tvGenre)
    TextView tvGenre;

    @BindView(R.id.tvRated)
    TextView tvRated;

    @BindView(R.id.tvReleased)
    TextView tvReleased;

    @BindView(R.id.tvRuntime)
    TextView tvRuntime;

    @BindView(R.id.tvImdbRating)
    TextView tvImdbRating;

    @BindView(R.id.tvPlot)
    TextView tvPlot;

    @BindView(R.id.tvActors)
    TextView tvActors;

    @BindView(R.id.tvWriter)
    TextView tvWriter;

    @BindView(R.id.tvAwards)
    TextView tvAwards;

    @BindView(R.id.tvRatings)
    TextView tvRatings;

    private String imdbID;
    private List<DetailResponse.RatingsBean> ratings = new ArrayList<>();
    private RatingAdapter adapter;
    private AlertDialog dialog;

    public static Intent getStartIntent(Context context, String imdbID, String title) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(Constants.imdbID, imdbID);
        intent.putExtra(Constants.title, title);
        return intent;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_detail;
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public void onActivityCreated() {

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            imdbID = extras.getString(Constants.imdbID);
            String title = extras.getString(Constants.title);

            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setTitle(title);
                supportActionBar.setDisplayHomeAsUpEnabled(true);
            }
        }

        iniUi();
    }

    private void iniUi() {

        adapter = new RatingAdapter(ratings);
        ratingRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ratingRecyclerView.setNestedScrollingEnabled(true);
        ratingRecyclerView.setAdapter(adapter);

        DetailPresenter detailPresenter = new DetailPresenter();
        detailPresenter.setContext(DetailActivity.this);
        detailPresenter.setDetailView(this);
        detailPresenter.setShouldFetchFromCache(!isNetworkConnected());
        detailPresenter.setImdbId(imdbID);
        detailPresenter.getDetailResponse();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void removeProgressDialog() {

    }

    @Override
    public void onFailure(String error) {
        showMessage(Constants.error, error);
    }

    @Override
    public void onSuccess(DetailResponse detailResponse) {

        String poster = ImageUtils.getOriginalPoster(detailResponse.getPoster());
        ImageUtils.setRectangleImage(this, ivPoster, poster);
        TextUtils.setString(tvTitle, detailResponse.getTitle());

        String ratings = TextUtils.getStringFromStringResId(this, R.string.ratings);
        TextUtils.setString(tvRatings, ratings);
        TextUtils.setSpannableString(this, R.color.colorGoldDark, tvType, TextUtils.getStringFromStringResId(this, R.string.type), detailResponse.getType());
        TextUtils.setSpannableString(this, R.color.colorGoldDark, tvGenre, TextUtils.getStringFromStringResId(this, R.string.genre), detailResponse.getGenre());
        TextUtils.setSpannableString(this, R.color.colorGoldDark, tvRated, TextUtils.getStringFromStringResId(this, R.string.rated), detailResponse.getRated());
        TextUtils.setSpannableString(this, R.color.colorGoldDark, tvReleased, TextUtils.getStringFromStringResId(this, R.string.released), detailResponse.getReleased());
        TextUtils.setSpannableString(this, R.color.colorGoldDark, tvRuntime, TextUtils.getStringFromStringResId(this, R.string.runtime), detailResponse.getRuntime());
        TextUtils.setSpannableString(this, R.color.colorGoldDark, tvImdbRating, TextUtils.getStringFromStringResId(this, R.string.imdbRating), detailResponse.getImdbRating());
        TextUtils.setSpannableString(this, R.color.colorGoldDark, tvPlot, TextUtils.getStringFromStringResId(this, R.string.plot), detailResponse.getPlot());
        TextUtils.setSpannableString(this, R.color.colorGoldDark, tvActors, TextUtils.getStringFromStringResId(this, R.string.actors), detailResponse.getActors());
        TextUtils.setSpannableString(this, R.color.colorGoldDark, tvWriter, TextUtils.getStringFromStringResId(this, R.string.writers), detailResponse.getWriter());
        TextUtils.setSpannableString(this, R.color.colorGoldDark, tvWriter, TextUtils.getStringFromStringResId(this, R.string.awards), detailResponse.getAwards());

        this.ratings.addAll(detailResponse.getRatings());
        adapter.notifyDataSetChanged();
    }
}
