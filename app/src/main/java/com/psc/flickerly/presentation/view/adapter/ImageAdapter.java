package com.psc.flickerly.presentation.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.psc.flickerly.R;
import com.psc.flickerly.domain.model.PhotoInfo;
import com.psc.flickerly.domain.utils.ImageUrlBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private List<PhotoInfo> list = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        PhotoInfo photoInfo = list.get(position);
        if (photoInfo != null) {
            holder.bindTo(photoInfo);
        } else {
            // Null defines a placeholder item - PagedListAdapter will automatically invalidate
            // this row when the actual object is loaded from the database
            holder.clear();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addData(final List<PhotoInfo> listToAppend) {
        final int previousItemCount = getItemCount();
        this.list.addAll(listToAppend);
        notifyItemRangeInserted(previousItemCount, listToAppend.size());
    }

    public void resetData() {
        list.clear();
        notifyDataSetChanged();
    }

    public void setData(final List<PhotoInfo> listToReplace) {
        this.list.clear();
        this.list.addAll(listToReplace);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;

        public ViewHolder(final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_item);
        }

        public void bindTo(final PhotoInfo photoInfo) {
            final String imageUrl = ImageUrlBuilder.buildUrl(photoInfo);
            Picasso.with(itemView.getContext())
                   .load(imageUrl)
                   .into(imageView);
        }

        public void clear() {
        }
    }

}
