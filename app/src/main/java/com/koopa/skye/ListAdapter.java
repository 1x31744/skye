package com.koopa.skye;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.koopa.skye.Fragments.Main.LibraryFragment;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Contents> {

    Contents contents;

    public ListAdapter(@NonNull Context context, ArrayList<Contents> arrayList) {
        super(context, R.layout.library_item, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (getItem(position) != null) {
            contents = getItem(position);
        }

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.library_item, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.entry_picture);
        TextView entryTitle = convertView.findViewById(R.id.entry_title);
        TextView entryDate = convertView.findViewById(R.id.entry_date);
        TextView entryContents = convertView.findViewById(R.id.entry_contents);

        byte[] b = Base64.decode(contents.image, Base64.DEFAULT);
        Bitmap bitmapImage = BitmapFactory.decodeByteArray(b, 0, b.length);

        imageView.setImageBitmap(bitmapImage);

        entryTitle.setText(contents.title);
        entryDate.setText(contents.date);
        entryContents.setText(contents.contents);

        return convertView;
    }
}
