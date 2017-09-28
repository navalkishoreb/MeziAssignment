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
            fetchImage();
        } else {
            Log.e("Mezi", "Same image in same view holder " + photo.getUrlN());
        }
    }

    private void fetchImage() {
        Bitmap bitmap = ThreadPoolFactory.getPool().getImageCache().get(photo.getUrlN());
        if (bitmap == null) {
            fetchBitmap();
        } else {
            Log.d("Mezi", "image fetched from cache");
            setPhotoView(bitmap);
        }


    }

    private void fetchBitmap() {
        ThreadPoolFactory.getPool().queueTask(imageDownload.build(photo.getUrlN(), (result, link) -> {
            if (result != null && link != null && (photo == null || (photo.getUrlN() != null && photo.getUrlN().equals(link)))) {
                Log.i("Mezi", "Setting image. " + photo.getUrlN());
                setPhotoView(result);
            }

        }, (error, link) -> {
            Log.e("Mezi", "Error downloading image " + error);
            photoView.setImageResource(R.mipmap.ic_launcher);

        }));
    }

    private void setPhotoView(Bitmap bitmap) {
        photoView.setImageBitmap(bitmap);
        photoView.setOnClickListener((view) -> {
            Intent intent = new Intent(view.getContext(), ViewImage.class);
            intent.putExtra("image", bitmap);
            view.getContext().startActivity(intent);
        });
    }

    private void resetPhotoView() {
        photoView.setOnClickListener(null);
        photoView.setImageResource(R.mipmap.ic_launcher);
    }
}
