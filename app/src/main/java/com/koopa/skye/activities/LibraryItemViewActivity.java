package com.koopa.skye.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.koopa.skye.R;

public class LibraryItemViewActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_item_view);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String date = intent.getStringExtra("date");
        String contents = intent.getStringExtra("contents");
        String image = intent.getStringExtra("image");

        ((TextView)findViewById(R.id.library_view_title)).setText(title);
        ((TextView)findViewById(R.id.library_view_date)).setText(date);
        ((TextView)findViewById(R.id.library_view_contents)).setText(contents);
        byte[] b = Base64.decode(image, Base64.DEFAULT);
        Bitmap bitmapImage = BitmapFactory.decodeByteArray(b, 0, b.length);
        ((ImageView)findViewById(R.id.library_view_image)).setImageBitmap(bitmapImage);

        ((ImageButton)findViewById(R.id.library_view_back_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LibraryItemViewActivity.super.onBackPressed();
            }
        });
    }
}
