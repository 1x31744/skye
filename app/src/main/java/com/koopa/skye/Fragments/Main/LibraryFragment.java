package com.koopa.skye.Fragments.Main;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.koopa.skye.LibraryContents;
import com.koopa.skye.LibraryListAdapter;
import com.koopa.skye.activities.LibraryItemViewActivity;
import com.koopa.skye.activities.MainActivity;
import com.koopa.skye.R;
import com.koopa.skye.TinyDB;

import java.util.ArrayList;

public class LibraryFragment extends Fragment {

    LibraryContents contents;
    TinyDB tinydb;

    ArrayList<LibraryContents> arrayList;

    SwipeMenuListView listView;

    LibraryListAdapter libraryListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_library, null);

        tinydb = new TinyDB(getActivity());
        listView = (root.findViewById(R.id.library_list));

        if (tinydb.getListContents("arraylist") != null) {
            arrayList = new ArrayList<LibraryContents>(tinydb.getListContents("arraylist"));
        }
        else {
            arrayList = new ArrayList<>();
        }




        addContents();

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

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            @NonNull
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LibraryContents selectedItem = (LibraryContents) parent.getItemAtPosition(position);
                String title = selectedItem.getTitle();
                String date = selectedItem.getDate();
                String contents = selectedItem.getContents();
                String image = selectedItem.getImage();
                Intent intent = new Intent(getActivity(), LibraryItemViewActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("date", date);
                intent.putExtra("contents", contents);
                intent.putExtra("image", image);
                startActivity(intent);
            }
        });

        return root;
    }

    void addContents(){

        if (MainActivity.libraryTitle != null) {
            contents = new LibraryContents(MainActivity.libraryTitle, MainActivity.libraryDate, MainActivity.entryContents, MainActivity.sEncodedImage);
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