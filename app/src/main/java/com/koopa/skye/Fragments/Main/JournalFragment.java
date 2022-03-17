package com.koopa.skye.Fragments.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.koopa.skye.R;

public class JournalFragment extends Fragment{

    Random random = new Random();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_journal, null);

        //Sets the title to a random one
        ((TextView)root.findViewById(R.id.journal_title)).setText(RandomTitle());
        //sets id of the button
        Button button = ((Button)root.findViewById(R.id.button));

        return root;
    }

    public String RandomTitle(){
        //Sets the titles that can be used
        String[] journalTitles = {"Start Your Story.", "Begin Your Journey.", "This is where it all starts.",
                "You Got this!", "Lets Go!", "You Got this!", "I Believe In You"};

        //picks which one to use
        int randomIndex = random.nextInt(journalTitles.length);
        //Makes a string with the title
        String journalTitle = journalTitles[randomIndex];
        //Returns the title
        return journalTitle;
    }
}
