package com.koopa.skye.Fragments.Main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.koopa.skye.R;
import com.koopa.skye.SkyeContents;
import com.koopa.skye.SkyeListAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SkyeFragment extends Fragment {

    String message = "";
    String time;
    public static SkyeContents contents;
    public static ArrayList<SkyeContents> arrayList;
    SkyeListAdapter skyeListAdapter;
    int maxRandom = 2;
    int minRandom = 1;

    String index = "asking how they are";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.fragment_skye,container, false);
        EditText messageBox = root.findViewById(R.id.message_box);
        arrayList = new ArrayList<>();
        ListView listView = root.findViewById(R.id.ai_list);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm z");
        skyeListAdapter = new SkyeListAdapter(getActivity(), arrayList);
        listView.setAdapter(skyeListAdapter);

        int randomNumber = (int)(Math.random()*(maxRandom-minRandom+1)+minRandom);
        time = sdf.format(new Date());
        switch (randomNumber) {
            case 1:
                index = "asking how they are";
                contents = new SkyeContents("how are you today?", time, false);
                arrayList.add(contents);
                break;
            case 2:
                index = "asking how is your day going so far";
                contents = new SkyeContents("how is your day going so far?", time, false);
                arrayList.add(contents);
                break;

        }
        skyeListAdapter.notifyDataSetChanged();

        //response
        root.findViewById(R.id.ai_send_button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                time = sdf.format(new Date());

                message = messageBox.getText().toString();
                if (message.length() > 0) {
                    messageBox.getText().clear();
                    contents = new SkyeContents(message, time, true);
                    arrayList.add(contents);
                    contents = null;
                    skyeListAdapter.notifyDataSetChanged();
                }


                String lowerMessage = message.toLowerCase(Locale.ROOT);

                //contains framework
                if (lowerMessage.contains("suicide")) {
                    contents = new SkyeContents("I suggest you call a helpline for yourself or" +
                            " whoever is having suicidal thoughts", time, false);
                }

                switch (index) {
                    case "asking how they are":
                        if (lowerMessage.contains("good")){

                        }
                        else if (lowerMessage.contains("bad")){

                        }
                        else{
                            contents = new SkyeContents("I couldn't understand you, please" +
                                    "use a different word for how you were feeling or reset the " +
                                    "conversation", time, false);
                        }
                        break;

                    case "asking how is your day going so far":
                        break;
                }

                if (contents != null) {
                    arrayList.add(contents);
                }

            }
        });
        return root;
    }
}
