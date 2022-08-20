package com.example.q_mark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class homepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_homepage);


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Chat()).commit();
            navigationView.setCheckedItem(R.id.side_menu_chat);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Profile()).commit();
            navigationView.setCheckedItem(R.id.side_menu_profile);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Notification()).commit();
            navigationView.setCheckedItem(R.id.side_menu_notification);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Friends()).commit();
            navigationView.setCheckedItem(R.id.side_menu_friends);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Addfriend()).commit();
            navigationView.setCheckedItem(R.id.side_menu_addfriend);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Upload()).commit();
            navigationView.setCheckedItem(R.id.side_menu_upload);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Settings()).commit();
            navigationView.setCheckedItem(R.id.side_menu_settings);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Course()).commit();
            navigationView.setCheckedItem(R.id.side_menu_course);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Home()).commit();
            navigationView.setCheckedItem(R.id.side_menu_home);


        }


    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.side_menu_chat:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Chat()).commit();
                break;

            case R.id.side_menu_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Home()).commit();
                break;

            case R.id.side_menu_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Profile()).commit();
                break;
            case R.id.side_menu_friends:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Friends()).commit();
                break;

            case R.id.side_menu_notification:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Notification()).commit();
                break;

            case R.id.side_menu_course:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Course()).commit();
                break;

            case R.id.side_menu_addfriend:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Addfriend()).commit();
                break;

            case R.id.side_menu_upload:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Upload()).commit();
                break;

            case R.id.side_menu_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Settings()).commit();

                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}