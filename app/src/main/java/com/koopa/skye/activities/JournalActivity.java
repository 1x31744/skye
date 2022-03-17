package com.koopa.skye.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.koopa.skye.Fragments.Journal.JournalPageFeelings;
import com.koopa.skye.Fragments.Journal.JournalPageFeelingsReason;
import com.koopa.skye.Fragments.Journal.JournalPageImageSelect;
import com.koopa.skye.Fragments.Journal.JournalPageReason;
import com.koopa.skye.Fragments.Journal.JournalPageReasonOther;
import com.koopa.skye.Fragments.Journal.JournalPageReasonText;
import com.koopa.skye.Fragments.Journal.JournalPageScale;
import com.koopa.skye.Fragments.Journal.JournalPageTitle;
import com.koopa.skye.Fragments.Journal.JournalPageDate;
import com.koopa.skye.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class JournalActivity extends AppCompatActivity{

    public static String reasonTextStarter;

    //Entry Variables
    String entryTitle;
    String entryDate;
    String entryScale;
    String[] entryReasons = {};
    String entryFeeling;
    String entryFeelingReason;
    public static Bitmap entryImage;

    Bitmap emoji;
    FragmentManager fragmentManager;
    int pageNumber;
    String pageTitle;
    String currentFragment;

    String[] pageIndexString = {
            String.valueOf(JournalPageTitle.class),
            String.valueOf(JournalPageDate.class),
            String.valueOf(JournalPageScale.class),
            String.valueOf(JournalPageReason.class),
            String.valueOf(JournalPageFeelings.class),
            String.valueOf(JournalPageFeelingsReason.class),
            String.valueOf(JournalPageImageSelect.class),
    };
    Fragment[] pageIndex = {
            new JournalPageTitle(),
            new JournalPageDate(),
            new JournalPageScale(),
            new JournalPageReason(),
            new JournalPageFeelings(),
            new JournalPageFeelingsReason(),
            new JournalPageImageSelect()
    };
    //Remove Objects when user sets to not wanted

    int basePageLimit = 8;
    //minus this when pages are removed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        replaceFragment(new JournalPageTitle());

    }

    public Fragment currentFragment(){
        //Gets the current fragment
        return getSupportFragmentManager().findFragmentById(R.id.journal_page_container);
    }
    @Override
    public void onBackPressed() {
        backButton(findViewById(R.id.journal_back_button));
    }
    public void backButton(View view){
        pageNumber -= 2;
        currentFragment = String.valueOf((getSupportFragmentManager().findFragmentById(R.id.journal_page_container)).getClass());

        if (currentFragment() instanceof JournalPageTitle){
            super.onBackPressed();
        }
        else if(currentFragment() instanceof JournalPageReasonText ||
        currentFragment() instanceof JournalPageReasonOther){
            replaceFragment(new JournalPageReason());
        }
        else{
            for (int found = 0; found < pageIndexString.length; found++)
            {
                if(pageIndexString[found].equals(currentFragment))
                {
                    replaceFragment(pageIndex[found-1]);
                }
            }
        }
    }

    @SuppressLint("NonConstantResourceId")
    public void pageButton(View view) {
        switch (view.getId()) {
            case R.id.journal_title_button:
                entryTitle = String.valueOf(((EditText)findViewById(R.id.enter_title)).getText());
                replaceFragment(new JournalPageDate());
                break;

            case R.id.journal_date_button:
                if (TextUtils.isEmpty(String.valueOf(((EditText)findViewById(R.id.editTextDate)).getText()))) {
                    entryDate = DateFormat.getDateInstance(DateFormat.SHORT).format(new Date());
                }
                else{
                    entryDate = String.valueOf(((EditText)findViewById(R.id.editTextDate)).getText());
                }
                replaceFragment(new JournalPageScale());
                break;

            case R.id.journal_scale_button:
                entryScale = String.valueOf(((TextView)findViewById(R.id.seekbar_number)).getText());
                replaceFragment(new JournalPageReason());
                break;

            case R.id.journal_reason_button:
                replaceFragment(new JournalPageFeelings());
                break;

            case R.id.journal_feelings_text_button:
                entryFeelingReason = String.valueOf(((EditText)findViewById(R.id.feelings_edittext)).getText());
                replaceFragment(new JournalPageImageSelect());
        }
    }

    @SuppressLint("NonConstantResourceId")
    public void reasonButtons(View view){
        switch (view.getId()) {
            case R.id.other_button:
                basePageLimit++;
                replaceFragment(new JournalPageReasonOther());
                break;

            case R.id.other_button_exit:
            reasonTextStarter = "Something happened today with " +
                    String.valueOf(((EditText)findViewById(R.id.other_edit_text)).getText()).toLowerCase(Locale.ROOT) + ": ";
                replaceFragment(new JournalPageReasonText());
                basePageLimit++;
                break;

            case R.id.journal_reason_text_button:
                entryReasons = Arrays.copyOf(entryReasons, entryReasons.length + 1);
                entryReasons[entryReasons.length - 1] = String.valueOf(((EditText)findViewById(R.id.reason_edittext)).getText());
                Log.v("no", entryReasons[entryReasons.length - 1]);
                replaceFragment(new JournalPageReason());
                basePageLimit++;
                break;

            default:
                reasonTextStarter = "Something happened today with " +
                        String.valueOf(((Button) view).getText()).toLowerCase(Locale.ROOT) + ": ";
                replaceFragment(new JournalPageReasonText());
                basePageLimit++;
                break;
        }
    }

    @SuppressLint("ResourceType")
    public void feelingButtons(View view){
        if (view.getId() == R.id.other_feeling_button){

        }
        else {
            Drawable dr;
            emoji = ((BitmapDrawable)view.getBackground()).getBitmap();
            String id = view.getResources().getResourceName(view.getId());
            int feelingLength = 0;

            for (int found = 0; found < id.length(); found++){
                if (id.charAt(found) == '_') {
                    feelingLength = found;
                }
            }
            for (int found = 0; found < feelingLength; found++){
                 entryFeeling += id.charAt(found);
            }

            entryFeeling = entryFeeling.substring(22);
            replaceFragment(new JournalPageFeelingsReason());
        }
    }

    public static final int PICK_IMAGE = 1;

    public void imageButtons(View view){
        switch (view.getId()){
            case R.id.emoji_image:
                entryImage = emoji;
                break;

            case R.id.select_image:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                //Todo: Fix crashing here
                break;

            case R.id.no_image:
                break;
        }
        if (view.getId() != R.id.select_image) {
            replaceFragment(new JournalPageTitle());
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri pickedImage = data.getData();
            try {
                entryImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), pickedImage);
                //BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance;
                replaceFragment(new JournalPageTitle());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void replaceFragment(Fragment fragment){
        pageNumber++;
        if (pageNumber >= basePageLimit) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("entryTitle", entryTitle);
            intent.putExtra("entryDate", entryDate);
            intent.putExtra("entryScale", entryScale);
            intent.putExtra("entryReasons", entryReasons);
            intent.putExtra("entryFeeling", entryFeeling);
            intent.putExtra("entryFeelingReason", entryFeelingReason);
            if (entryImage != null){
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                entryImage.compress(Bitmap.CompressFormat.JPEG, 25, baos);
                byte[] bytes = baos.toByteArray();
                intent.putExtra("entryImage", bytes);

            }

            startActivity(intent);
            //Todo: Make a non hardcoded way to progress page
            //Todo: Allow Backtracking where not added
        }
        else {
            pageTitle = "Page " + pageNumber;
            ((TextView)findViewById(R.id.toolbar_title)).setText(pageTitle);
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.journal_page_container, fragment);
            fragmentTransaction.commit();
        }
    }
}