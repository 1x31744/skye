package com.koopa.skye.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.koopa.skye.Fragments.LibraryEdit.EditContents;
import com.koopa.skye.Fragments.LibraryEdit.EditDate;
import com.koopa.skye.Fragments.LibraryEdit.EditMain;
import com.koopa.skye.Fragments.LibraryEdit.EditTitle;
import com.koopa.skye.R;

public class LIbraryItemEdit extends AppCompatActivity {

    FragmentManager fragmentManager;
    public static String title;
    public static String date;
    public static String contents;
    public static String image;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_item_edit);

        changeFragment(new EditMain());

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        date = intent.getStringExtra("date");
        contents = intent.getStringExtra("contents");
        image = intent.getStringExtra("image");
    }

    public void changeEditFragment(View view){
        switch (view.getId()){
            case R.id.edit_title_button:
                changeFragment(new EditTitle());
                break;
            case R.id.edit_date_button:
                changeFragment(new EditDate());
                break;
            case R.id.edit_contents_button:
                changeFragment(new EditContents());
                break;
            case R.id.edit_image_button:
                break;
        }
    }

    public void finish(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("editedTitle", title);
        intent.putExtra("editedDate", date);
        intent.putExtra("editedContents", contents);
        intent.putExtra("editedImage", image);
        intent.putExtra("justEdited", true);
        startActivity(intent);
    }

    void changeFragment(Fragment fragment){
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.library_edit_pageContainer, fragment);
        fragmentTransaction.commit();
    }
}