package com.koopa.skye;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.android.material.navigation.NavigationView;
import com.google.common.primitives.Bytes;
import com.koopa.skye.Fragments.Main.HomeFragment;
import com.koopa.skye.Fragments.Main.JournalFragment;
import com.koopa.skye.Fragments.Main.LibraryFragment;
import com.koopa.skye.Fragments.Main.SettingsFragment;
import com.koopa.skye.Fragments.Main.SkyeFragment;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private DrawerLayout drawerLayout;
    public static String libraryTitle;
    public static String libraryDate;
    public static String entryContents;
    public static String sEncodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        libraryTitle = intent.getStringExtra("entryTitle");
        libraryDate = intent.getStringExtra("entryDate");
        String libraryScale = intent.getStringExtra("entryScale");
        String[] libraryReasons = intent.getStringArrayExtra("entryReasons");
        String libraryFeeling = intent.getStringExtra("entryFeeling");
        String libraryFeelingReasons = intent.getStringExtra("entryFeelingReason");

        StringBuffer sb = new StringBuffer();
        if (libraryReasons != null) {
            for (int i = 0; i < libraryReasons.length; i++) {
                sb.append(libraryReasons[i]);
                sb.append("\n");
            }
        }
        String entryReasons = sb.toString();

        if (libraryScale != null) {
            if (libraryReasons == null) {
                entryContents = "I would rate today from a 1-10 a " + libraryScale + ", " + "today i felt " + libraryFeeling + " " +
                        libraryFeelingReasons + ".";
            } else {
                entryContents = "I would rate today from a 1-10 a " + libraryScale + "\n" + entryReasons +
                        "Today i felt " + libraryFeeling + " " + libraryFeelingReasons + ".";
            }
        }

        Bundle extras = intent.getExtras();

        if (extras != null) {
            byte[] b = extras.getByteArray("entryImage");
            sEncodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //start with home
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }


    }

    //Selecting Navigation Bar
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                getSupportActionBar().setTitle("Home");
                break;

            case R.id.nav_AI:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SkyeFragment()).commit();
                getSupportActionBar().setTitle("SkyE");
                break;

            case R.id.nav_journal:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new JournalFragment()).commit();
                getSupportActionBar().setTitle("Journal");
                break;

            case R.id.nav_library:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LibraryFragment()).commit();
                getSupportActionBar().setTitle("Library");
                break;

            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SettingsFragment()).commit();
                getSupportActionBar().setTitle("Settings");
                break;
            }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }
    }

    //Journal fragment button press
    public void journalPressed(View view){
        switch (view.getId()) {
            case R.id.button:
                Intent journalIntent = new Intent(this, JournalActivity.class);
                startActivity(journalIntent);
        }
    }
}