package com.koopa.skye;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SkyeListAdapter extends ArrayAdapter<SkyeContents> {

    SkyeContents contents;

    public SkyeListAdapter(@NonNull Context context, ArrayList<SkyeContents> arrayList) {
        super(context, R.layout.skye_user_message_item, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (getItem(position) != null) {
            contents = getItem(position);
        }

        if (contents.messageSentByUser) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.skye_user_message_item, parent, false);
        }
        else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.skye_other_message_item, parent, false);
        }
        TextView message = convertView.findViewById(R.id.message);
        TextView time = convertView.findViewById(R.id.timestamp);

        message.setText(contents.message);
        time.setText(contents.time);

        return convertView;
    }
}
