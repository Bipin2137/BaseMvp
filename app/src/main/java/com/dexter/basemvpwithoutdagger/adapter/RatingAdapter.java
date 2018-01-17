package com.dexter.basemvpwithoutdagger.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dexter.basemvpwithoutdagger.R;
import com.dexter.basemvpwithoutdagger.api.response.DetailResponse;
import com.dexter.basemvpwithoutdagger.utils.TextUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Khatr on 12/31/2017.
 */

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.ViewHolder> {

    private List<DetailResponse.RatingsBean> ratings;

    public RatingAdapter(List<DetailResponse.RatingsBean> ratings) {
        this.ratings = ratings;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rating, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DetailResponse.RatingsBean ratingsBean = ratings.get(position);
        holder.setRatedBy(ratingsBean.getSource());
        holder.setRating(ratingsBean.getValue());
    }

    @Override
    public int getItemCount() {
        return ratings.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvRatedBy)
        TextView tvRatedBy;

        @BindView(R.id.tvRating)
        TextView tvRating;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setRatedBy(String ratedBy) {
            TextUtils.setString(tvRatedBy, ratedBy);
        }

        private void setRating(String rating) {
            TextUtils.setString(tvRating, rating);
        }
    }
}
