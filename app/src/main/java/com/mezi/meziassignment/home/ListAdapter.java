package com.mezi.meziassignment.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mezi.meziassignment.R;
import com.mezi.meziassignment.models.Photo;

import java.util.List;

/**
 * Created by navalkishoreb on 9/27/2017.
 */

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public void clear() {
        this.photos.clear();
        notifyDataSetChanged();
    }

    enum ViewType {
        EMPTY,
        IMAGE

    }

    private List<Photo> photos;

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewType type = ViewType.values()[viewType];
        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch (type) {
            case IMAGE:
                View listItem = layoutInflater.inflate(R.layout.list_item, parent, false);
                return new ImageViewHolder(listItem);
            case EMPTY:
                View empty = layoutInflater.inflate(R.layout.list_empty, parent, false);
                return new EmptyViewHolder(empty);
            default:
                throw new UnsupportedOperationException(type.name() + "  not defined");
        }

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d("Mezi", "Bind view " + position);
        if (holder instanceof ImageViewHolder) {
            ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
            imageViewHolder.setPhoto(photos.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (photos == null || photos.isEmpty()) {
            return ViewType.EMPTY.ordinal();
        }
        return ViewType.IMAGE.ordinal();
    }

    @Override
    public int getItemCount() {
        if (photos != null) {
            return photos.size();
        }
        return 1;
    }
}
