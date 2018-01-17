package com.dexter.basemvpwithoutdagger.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dexter.basemvpwithoutdagger.R;

/**
 * Created by Admin on 03-07-2017.
 */

public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int NO_ICON = 500;
    //declare variables to hold values for empty icons
    public static int EMPTY_MOVIE = R.mipmap.ic_launcher;

    private String message;
    private int icon = -1;

    public abstract VH onViewHolderCreated(ViewGroup parent, int viewType);

    public abstract void onViewHolderBound(VH holder, int position);

    public abstract int getListItemCount();

    private static final int EMPTY_VIEW_TYPE = 1;
    ;

    public void setEmptyIcon(int icon) {
        this.icon = icon;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == EMPTY_VIEW_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_view, parent, false);
            return new EmptyViewHolder(view);
        } else {
            return onViewHolderCreated(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) != EMPTY_VIEW_TYPE) {
            onViewHolderBound((VH) holder, position);
        } else {
            EmptyViewHolder emptyViewHolder = (EmptyViewHolder) holder;
            emptyViewHolder.showEmptyView();

            if (message != null && !message.isEmpty()) {
                emptyViewHolder.setEmptyMessage(message);
            }

            if (icon != -1) {
                if (icon == NO_ICON) {
                    emptyViewHolder.removeImage();
                } else {
                    //set the icon
                    emptyViewHolder.setEmptyImage(icon);
                }

            }

        }
    }

    @Override
    public int getItemCount() {
        return (getListItemCount() == 0 ? 1 : getListItemCount());
    }

    @Override
    public int getItemViewType(int position) {
        if ((position == getItemCount() - 1) && getListItemCount() == 0)
            return EMPTY_VIEW_TYPE;
        return super.getItemViewType(position);
    }

    public void setMessage(String message) {
        this.message = message;
        notifyDataSetChanged();
    }

    private class EmptyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivEmptyImage;
        private View empty;
        private TextView tvMessage;

        private EmptyViewHolder(View itemView) {
            super(itemView);
            empty = itemView.findViewById(R.id.emptylist);
            tvMessage = (TextView) itemView.findViewById(R.id.tvMessage);
            ivEmptyImage = (ImageView) itemView.findViewById(R.id.ivEmptyImage);
        }

        void showEmptyView() {
            empty.setVisibility(View.VISIBLE);

        }

        void setEmptyMessage(String message) {
            tvMessage.setText(message);
        }

        void setEmptyImage(int emptyImage) {
            ivEmptyImage.setImageResource(emptyImage);
        }

        void removeImage() {
            ivEmptyImage.setImageDrawable(null);
        }
    }
}
