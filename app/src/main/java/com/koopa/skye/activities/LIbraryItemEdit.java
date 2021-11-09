package com.koopa.skye.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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
    int position = 0;

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
        position = intent.getIntExtra("position", 0);
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

    public void finishEditing (View view){

        if (currentFragment() == EditTitle.class) {
            title = String.valueOf(((EditText)findViewById(R.id.edit_title)).getText());
        }
        else if (currentFragment() == EditDate.class) {
            date = String.valueOf(((EditText)findViewById(R.id.edit_date)).getText());
        }
        else if (currentFragment() == EditContents.class) {
            contents = String.valueOf(((EditText)findViewById(R.id.edit_contents)).getText());
        }


        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("editedTitle", title);
        intent.putExtra("editedDate", date);
        intent.putExtra("editedContents", contents);
        intent.putExtra("editedImage", image);
        intent.putExtra("justEdited", true);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    public Class currentFragment(){
        return getSupportFragmentManager().findFragmentById(R.id.library_edit_pageContainer).getClass();
    }

    void changeFragment(Fragment fragment){
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.library_edit_pageContainer, fragment);
        fragmentTransaction.commit();
    }
}