package com.eliottdup.gettalents.ui.picture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.eliottdup.gettalents.R;

public class PictureActivity extends AppCompatActivity {
    public final static String KEY_IMAGE_URI = "imageUri";

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_picture);

        imageView = findViewById(R.id.imageView);

        String imageUri = getIntent().getStringExtra(KEY_IMAGE_URI);

        Glide.with(this).load(imageUri).into(imageView);
    }
}