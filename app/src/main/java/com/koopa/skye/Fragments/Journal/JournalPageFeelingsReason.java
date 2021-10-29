package com.koopa.skye.Fragments.Journal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.koopa.skye.R;

public class JournalPageFeelingsReason extends Fragment {

    public boolean onBackPressed;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.journal_page_feelings_reason, null);

        ((EditText)root.findViewById(R.id.feelings_edittext)).setText("because: ");


        return root;
    }
}
