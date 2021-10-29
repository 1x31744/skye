package com.koopa.skye.Fragments.Main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.renderscript.ScriptGroup;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.koopa.skye.Contents;
import com.koopa.skye.ListAdapter;
import com.koopa.skye.MainActivity;
import com.koopa.skye.R;
import com.koopa.skye.TinyDB;
import com.koopa.skye.databinding.ActivityMainBinding;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.prefs.AbstractPreferences;

public class LibraryFragment extends Fragment {

    Contents contents;
    TinyDB tinydb;
    public static String[] entryTitle = {};
    public static String[] entryDate = {};
    public static String[] entryContents = {};
    String[] entryImage = {};

    ArrayList<String> entryTitleList;
    ArrayList<String> entryDateList;
    ArrayList<String> entryContentsList;
    ArrayList<String> entryImageList;
    ArrayList<Contents> arrayList;

    SwipeMenuListView listView;

    ListAdapter listAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_library, null);

        tinydb = new TinyDB(getActivity());
        listView = (root.findViewById(R.id.library_list));

        if (tinydb.getListContents("arraylist") != null) {
            arrayList = new ArrayList<Contents>(tinydb.getListContents("arraylist"));
        }
        else {
            arrayList = new ArrayList<>();
        }

        entryTitleList = new ArrayList<>(Arrays.asList(entryTitle));
        entryDateList = new ArrayList<>(Arrays.asList(entryDate));
        entryContentsList = new ArrayList<>(Arrays.asList(entryContents));
        entryImageList = new ArrayList<>(Arrays.asList(entryImage));


        addContents();

        listAdapter = new ListAdapter(getActivity(), arrayList);
        listView.setAdapter(listAdapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0x00, 0xb7,
                        0xeb)));
                // set item width
                openItem.setWidth(270);
                // set item title
                openItem.setTitle("Edit");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(270);
                //set item title
                deleteItem.setTitle("Delete");
                //set item title fontsize
                deleteItem.setTitleSize(18);
                //set item title font color
                deleteItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            @NonNull
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // edit
                        break;
                    case 1:
                        removeContents(position);
                        listAdapter.notifyDataSetChanged();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        return root;
    }

    void addContents(){

        if (MainActivity.libraryTitle != null) {
            contents = new Contents(MainActivity.libraryTitle, MainActivity.libraryDate, MainActivity.entryContents, MainActivity.sEncodedImage);
            arrayList.add(contents);
        }

        MainActivity.libraryTitle = null;
        MainActivity.libraryDate = null;
        MainActivity.entryContents = null;
        MainActivity.sEncodedImage = null;

        tinydb.putListContents("arraylist", arrayList);
    }

    void removeContents(int position){
        arrayList.remove(position);
        tinydb.putListContents("arraylist", arrayList);
    }
}