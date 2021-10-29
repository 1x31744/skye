package com.koopa.skye.Fragments.Main;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.koopa.skye.LibraryContents;
import com.koopa.skye.LibraryListAdapter;
import com.koopa.skye.MainActivity;
import com.koopa.skye.R;
import com.koopa.skye.SkyeContents;
import com.koopa.skye.SkyeListAdapter;

import java.util.ArrayList;
import java.util.List;

public class SkyeFragment extends Fragment {

    public static String message;
    String time;
    public static SkyeContents contents;
    public static ArrayList<SkyeContents> arrayList;
    SkyeListAdapter skyeListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.fragment_skye,container, false);
        EditText messageBox = root.findViewById(R.id.message_box);
        arrayList = new ArrayList<>();
        ListView listView = root.findViewById(R.id.ai_list);

        skyeListAdapter = new SkyeListAdapter(getActivity(), arrayList);
        listView.setAdapter(skyeListAdapter);

        ((ImageButton)root.findViewById(R.id.ai_send_button)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                message = messageBox.getText().toString();
                if (message.length() > 0) {
                    messageBox.getText().clear();
                    contents = new SkyeContents(message, "time", true);
                    arrayList.add(contents);
                    contents = null;
                    skyeListAdapter.notifyDataSetChanged();
                }
                if (message.contains("suicide")) {
                    contents = new SkyeContents("i am an ai", "time", false);
                }
                if (contents != null) {
                arrayList.add(contents);
                }
            }
        });



        return root;
    }
}
