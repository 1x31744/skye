package com.koopa.skye.Fragments.Journal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.koopa.skye.R;

import java.text.DateFormat;
import java.util.Date;

public class JournalPageDate extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.journal_page_date, null);

        ((EditText)root.findViewById(R.id.editTextDate)).setText(DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()),
                TextView.BufferType.EDITABLE);

        return root;
    }
}
