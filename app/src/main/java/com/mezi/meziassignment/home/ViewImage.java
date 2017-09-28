package com.mezi.meziassignment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.mezi.meziassignment.R;

/**
 * Created by navalkishoreb on 9/27/2017.
 */

public class ViewImage extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_only);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(getIntent().getParcelableExtra("image"));
    }
}
