package com.koopa.skye.Fragments.Main;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.koopa.skye.LibraryContents;
import com.koopa.skye.LibraryListAdapter;
import com.koopa.skye.activities.LIbraryItemEdit;
import com.koopa.skye.activities.LibraryItemViewActivity;
import com.koopa.skye.activities.MainActivity;
import com.koopa.skye.R;
import com.koopa.skye.TinyDB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class LibraryFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    LibraryContents contents;
    TinyDB tinydb;

    ArrayList<LibraryContents> arrayList;

    SwipeMenuListView listView;

    LibraryListAdapter libraryListAdapter;

    String[] sortingOptions = {"Custom", "Date (eldest first)", "Date (eldest last)", "Alphabetically"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_library, null);

        Spinner spin = (Spinner) root.findViewById(R.id.spinner);
        ArrayAdapter sortArrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, sortingOptions);
        sortArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(sortArrayAdapter);
        spin.setOnItemSelectedListener(this);

        /*Context: ListView contains the ArrayLists, Arraylists are the actual entries
        Arraylists are made up of contents which contains variables defined in the
        LibraryContents class*/

        ///Gets database and sets the listview
        tinydb = new TinyDB(getActivity());
        listView = (root.findViewById(R.id.library_list));

        //Restores array list from database
        if (tinydb.getListContents("arraylist") != null) {
            arrayList = new ArrayList<LibraryContents>(tinydb.getListContents("arraylist"));
        }
        //If nothing in database, set to new ArrayList
        else {
            arrayList = new ArrayList<>();
        }

        //Checks if the boolean libraryEdit is true to see which function to call
        if (MainActivity.libraryEdit){
            addEdits();
        }
        /*If not true just goes to default adding content which is later decided in addContents if
        there is any to add*/
        else {
            addContents();
        }

        //Sets up swiping listview
        libraryListAdapter = new LibraryListAdapter(getActivity(), arrayList);
        listView.setAdapter(libraryListAdapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                // create "open" item
                SwipeMenuItem editTitleItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                editTitleItem.setBackground(new ColorDrawable(Color.rgb(0x00, 0xb7,
                        0xeb)));
                editTitleItem.setWidth(270);
                editTitleItem.setTitle("Edit");
                editTitleItem.setTitleSize(18);
                editTitleItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(editTitleItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                deleteItem.setWidth(270);
                deleteItem.setTitle("Delete");
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        listView.setMenuCreator(creator);

        //Click listener for the swipe view
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            @NonNull
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        LibraryContents selectedItem = libraryListAdapter.getItem(position);
                        activityChange(LIbraryItemEdit.class, position, selectedItem);
                        break;

                    case 1:
                        removeContents(position);
                        libraryListAdapter.notifyDataSetChanged();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        //Opens large view when entry is clicked on
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LibraryContents selectedItem = (LibraryContents) parent.getItemAtPosition(position);
                activityChange(LibraryItemViewActivity.class, position, selectedItem);
            }
        });

        return root;
    }

    private void addEdits() {


        //Checks if isn't null so not adding a null entry
        if (MainActivity.libraryTitle != null) {
            ///Adds entry details stored in the MainActivity
            contents = new LibraryContents(MainActivity.libraryTitle, MainActivity.libraryDate, MainActivity.entryContents, MainActivity.sEncodedImage);
            //Makes the edits the arraylist numbered by .editposition
            arrayList.set(MainActivity.editPosition, contents);
        }

        //Sets everything to null for next edits
        MainActivity.libraryTitle = null;
        MainActivity.libraryDate = null;
        MainActivity.entryContents = null;
        MainActivity.sEncodedImage = null;
        MainActivity.libraryEdit = false;

        //Updates database
        tinydb.putListContents("arraylist", arrayList);

    }

    void addContents(){

        //Checks if null, so it's not adding a null entry
        if (MainActivity.libraryTitle != null) {
            //Sets the contents to the variables in MainActivity
            contents = new LibraryContents(MainActivity.libraryTitle, MainActivity.libraryDate, MainActivity.entryContents, MainActivity.sEncodedImage);
            //Adds it as an arraylist with the contents
            arrayList.add(contents);
        }

        //Sets variables to null so no other entry is added next time opening fragment
        MainActivity.libraryTitle = null;
        MainActivity.libraryDate = null;
        MainActivity.entryContents = null;
        MainActivity.sEncodedImage = null;

        //adds the arraylist to the database
        tinydb.putListContents("arraylist", arrayList);
    }

    void removeContents(int position){
        //removes array list and updates
        arrayList.remove(position);
        tinydb.putListContents("arraylist", arrayList);
    }

    void activityChange(Class clas, int position, LibraryContents selectedItem){
        //gets data from arraylist and sends it to activity changing to
        String title = selectedItem.getTitle();
        String date = selectedItem.getDate();
        String contents = selectedItem.getContents();
        String image = selectedItem.getImage();
        Intent intent = new Intent(getActivity(), clas);
        intent.putExtra("title", title);
        intent.putExtra("date", date);
        intent.putExtra("contents", contents);
        intent.putExtra("image", image);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    //Sorts the arraylists
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        switch (position){
            case 0:
                //Custom Selected
                break;
            case 1:
                //Date (eldest first)
                break;
            case 2:
                //Date (eldest last)
                break;
            case 3:
                //Alphabetically
                int maxPosition = libraryListAdapter.getCount();
                ArrayList<String> titles = new ArrayList<>();
                ArrayList<String> dates = new ArrayList<>();
                ArrayList<String> contents = new ArrayList<>();
                ArrayList<String> images = new ArrayList<>();
                for (int i = 0; i < maxPosition; i++) {

                    LibraryContents item = libraryListAdapter.getItem(i);

                    titles.add(item.getTitle());
                    dates.add(item.getDate());
                    contents.add(item.getContents());
                    images.add(item.getImage());
                }

                String[] newTitles = titles.toArray(new String[titles.size()]);
                String[] newDates = dates.toArray(new String[dates.size()]);
                String[] newContents = contents.toArray(new String[contents.size()]);
                String[] newImages = images.toArray(new String[images.size()]);

                String tempTitles;
                String tempDates;
                String tempContents;
                String tempImages;
                for (int j = 0; j < newTitles.length; j++) {
                    for (int i = j + 1; i < newTitles.length; i++) {
                        // comparing strings
                        if (newTitles[i].compareTo(newTitles[j]) < 0) {
                            tempTitles = newTitles[j];
                            newTitles[j] = newTitles[i];
                            newTitles[i] = tempTitles;

                            tempDates = newDates[j];
                            newDates[j] = newDates[i];
                            newDates[i] = tempDates;

                            tempContents = newContents[j];
                            newContents[j] = newContents[i];
                            newContents[i] = tempContents;

                            tempImages = newImages[j];
                            newImages[j] = newImages[i];
                            newImages[i] = tempImages;
                        }
                    }
                }

                for (int i = 0; i < maxPosition; i++) {

                    LibraryContents newItem = new LibraryContents(newTitles[i], newDates[i], newContents[i], newImages[i]);
                    arrayList.set(i, newItem);
                }
                libraryListAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}