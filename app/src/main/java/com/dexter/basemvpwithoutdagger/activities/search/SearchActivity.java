package com.dexter.basemvpwithoutdagger.activities.search;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.dexter.basemvpwithoutdagger.R;
import com.dexter.basemvpwithoutdagger.activities.BaseActivity;
import com.dexter.basemvpwithoutdagger.activities.detail.DetailActivity;
import com.dexter.basemvpwithoutdagger.adapter.SearchAdapter;
import com.dexter.basemvpwithoutdagger.api.response.SearchResponse;
import com.dexter.basemvpwithoutdagger.database.beans.SearchBean;
import com.dexter.basemvpwithoutdagger.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Khatr on 12/31/2017.
 */

public class SearchActivity extends BaseActivity implements SearchView {

    private static final String TAG = "SearchActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.launchTime)
    LinearLayout launchTime;

    private SearchAdapter searchAdapter;
    private List<SearchBean> searchBeanArrayList = new ArrayList<>();
    private String totalSearchResult;
    private SearchPresenter searchPresenter;
    private int size;
    private int page = 1;
    private AlertDialog dialog;
    private android.support.v7.widget.SearchView searchView;

    @Override
    public int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public void onActivityCreated() {
        initUi();
    }

    private void initUi() {
        setSupportActionBar(toolbar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        searchAdapter = new SearchAdapter(this, searchBeanArrayList);

        searchAdapter.setCallback(searchBean -> {
            Intent startIntent = DetailActivity.getStartIntent(this, searchBean.getImdbID(), searchBean.getTitle());
            startActivity(startIntent);
        });
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void removeProgressDialog() {

    }

    @Override
    public void onFailure(String error) {
        searchBeanArrayList.clear();
        recyclerView.setAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();
        showMessage(Constants.error, error);
    }

    @Override
    public void onSuccess(SearchResponse searchResponse) {
        totalSearchResult = searchResponse.getTotalResults();

        int listSize = searchBeanArrayList.size();

        searchBeanArrayList.addAll(searchResponse.getSearch());
        size = searchBeanArrayList.size();
        recyclerView.setAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();

        if (page > 1) {
            recyclerView.smoothScrollToPosition(listSize - 1);
        }
    }

    @Override
    public void onCacheSuccess(List<SearchBean> searchBeanList) {
        searchBeanArrayList.addAll(searchBeanList);
        size = searchBeanArrayList.size();
        recyclerView.setAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        searchView = (android.support.v7.widget.SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                if (!TextUtils.isEmpty(query)) {
                    searchPresenter = new SearchPresenter();
                    searchPresenter.setContext(SearchActivity.this);
                    searchPresenter.setSearchView(SearchActivity.this);
                    searchPresenter.setSearchTerm(query);
                    searchPresenter.setPage(page);
                    searchPresenter.setShouldFetchFromDatabase(!isNetworkConnected());
                    searchPresenter.getSearchResponse();
                    launchTime.setVisibility(View.GONE);
                    searchBeanArrayList.clear();
                    searchAdapter.notifyDataSetChanged();
                    page = 1;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return true;
    }
}
