package com.koopa.skye.Fragments.LibraryEdit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.koopa.skye.R;
import com.koopa.skye.activities.LIbraryItemEdit;

public class EditDate extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.library_item_edit_date,container, false);

        ((EditText)root.findViewById(R.id.edit_date)).setText(LIbraryItemEdit.date);

        return root;
    }

}
