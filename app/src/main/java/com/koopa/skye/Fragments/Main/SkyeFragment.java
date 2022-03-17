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

    //Index tells the AI what is is actually doing with the user
    String index = "asking how they are";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.fragment_skye,container, false);
        EditText messageBox = root.findViewById(R.id.message_box);
        //Sets the arraylist of the messages
        arrayList = new ArrayList<>();
        //Sets the listview for the arraylists to be held in
        ListView listView = root.findViewById(R.id.ai_list);
        //Sets the format for date and time for message appearance
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm z");
        //makes and sets the list adapter for the listview
        skyeListAdapter = new SkyeListAdapter(getActivity(), arrayList);
        listView.setAdapter(skyeListAdapter);

        //Starts a conversation with the user based on random number picker
        int randomNumber = (int)(Math.random()*(maxRandom-minRandom+1)+minRandom);

        //Gets the time for message
        time = sdf.format(new Date());

        //actually starts the converation picked by the random number
        switch (randomNumber) {
            case 1:
                //sets AI index
                index = "asking how they are";
                //sets the message in contents
                contents = new SkyeContents("how are you today?", time, false);
                //adds the array list with contents
                arrayList.add(contents);
                break;
            case 2:
                //sets AI index
                index = "asking how is your day going so far";
                //sets the message in contents
                contents = new SkyeContents("how is your day going so far?", time, false);
                //adds the array list
                arrayList.add(contents);
                break;

        }
        skyeListAdapter.notifyDataSetChanged();

        //User's message and AI response, done on click of button.
        root.findViewById(R.id.ai_send_button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Users message sending

                //Gets time
                time = sdf.format(new Date());
                //gets message to put into the arraylist from message box
                message = messageBox.getText().toString();
                //Makes sure you can't send an empty message
                if (message.length() > 0) {
                    //clears it after sending message
                    messageBox.getText().clear();
                    //sets the contents of arraylist
                    contents = new SkyeContents(message, time, true);
                    //adds arraylist with contents
                    arrayList.add(contents);
                    //nulls contents for next use
                    contents = null;
                    //Updates the listview
                    skyeListAdapter.notifyDataSetChanged();
                }

                //AI message sending

                //converts to lowercase so the AI can understand
                String lowerMessage = message.toLowerCase(Locale.ROOT);

                //AI response framework framework

                //Responses that are the same no matter what
                if (lowerMessage.contains("suicide")) {
                    contents = new SkyeContents("I suggest you call a helpline for yourself or" +
                            " whoever is having suicidal thoughts", time, false);
                }

                //Responses based off of index
                switch (index) {
                    case "asking how they are":
                        if (lowerMessage.contains("good")){ //instead of just good, make list of positive words

                        }
                        else if (lowerMessage.contains("bad")){

                        }
                        //if its an unrecognised response
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
