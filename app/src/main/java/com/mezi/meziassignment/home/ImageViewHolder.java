package com.mezi.meziassignment.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.mezi.meziassignment.R;
import com.mezi.meziassignment.base.Task;
import com.mezi.meziassignment.base.ThreadPoolFactory;
import com.mezi.meziassignment.models.Photo;
import com.mezi.meziassignment.tasks.ImageDownload;

/**
 * Created by navalkishoreb on 9/27/2017.
 */

class ImageViewHolder extends RecyclerView.ViewHolder {

    private ImageView photoView;
    private Task<Bitmap> imageDownload;
    private Photo photo;

    public ImageViewHolder(View itemView) {
        super(itemView);
        photoView = (ImageView) itemView.findViewById(R.id.photo);
        imageDownload = new ImageDownload();

    }

    void setPhoto(Photo newPhoto) {
        if (!newPhoto.equals(photo)) {
            photo = newPhoto;
            resetPhotoView();
            downloadImage();
        } else {
            Log.e("Mezi", "Same image in same view holder " + photo.getId());
        }
    }

    private void downloadImage() {
        ThreadPoolFactory.getPool().queueTask(imageDownload.build(photo.getUrlN(), result -> {
            if (result != null) {
                Log.e("Mezi", "Setting image " + photo.getId());
                photoView.setImageBitmap(result);
                photoView.setOnClickListener((view) -> {
                    Intent intent = new Intent(view.getContext(), ViewImage.class);
                    intent.putExtra("image", result);
                    view.getContext().startActivity(intent);
                });
            }

        }, error -> {
            Log.e("Mezi", "Error downloading image " + error);
            photoView.setImageResource(R.mipmap.ic_launcher);

        }));
    }

    private void resetPhotoView() {
        photoView.setOnClickListener(null);
        photoView.setImageResource(R.mipmap.ic_launcher);
    }
}
