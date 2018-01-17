package com.dexter.basemvpwithoutdagger.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dexter.basemvpwithoutdagger.R;
import com.dexter.basemvpwithoutdagger.database.beans.SearchBean;
import com.dexter.basemvpwithoutdagger.utils.ImageUtils;
import com.dexter.basemvpwithoutdagger.utils.TextUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Khatr on 12/31/2017.
 */

public class SearchAdapter extends BaseAdapter<SearchAdapter.ViewHolder> {

    private Context context;
    private List<SearchBean> searchBeanList;
    private Callback callback;

    public SearchAdapter(Context context, List<SearchBean> searchBeanList) {
        this.context = context;
        this.searchBeanList = searchBeanList;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public ViewHolder onViewHolderCreated(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_search_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onViewHolderBound(ViewHolder holder, int position) {
        SearchBean searchBean = searchBeanList.get(position);
        String image = searchBean.getPoster();
        String title = searchBean.getTitle();
        String type = searchBean.getType();
        String year = searchBean.getYear();
        holder.setImage(image);
        holder.setTitle(title);
        holder.setType(type);
        holder.setYear(year);
        holder.setOnClickListener(view -> {
            if (callback != null) {
                callback.onItemClick(searchBean);
            }
        });
    }

    @Override
    public int getListItemCount() {
        return searchBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivImage)
        ImageView ivImage;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvYear)
        TextView tvYear;
        @BindView(R.id.tvType)
        TextView tvType;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setImage(String url) {
            ImageUtils.setImage(context, ivImage, url);
        }

        private void setTitle(String title) {
            TextUtils.setString(tvTitle, title);
        }

        private void setYear(String year) {
            TextUtils.setSpannableString(context, R.color.colorGoldDark, tvYear, TextUtils.getStringFromStringResId(context, R.string.year), year);
        }

        private void setType(String type) {
            TextUtils.setSpannableString(context, R.color.colorGoldDark, tvType, TextUtils.getStringFromStringResId(context, R.string.type), type);
        }

        private void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }
    }

    public interface Callback {
        void onItemClick(SearchBean searchBean);
    }
}
