package com.example.newtasktest.activity;


import static com.example.newtasktest.util.appConstants.Email;
import static com.example.newtasktest.util.appConstants.Flag;
import static com.example.newtasktest.util.appConstants.My_preference;
import static com.example.newtasktest.util.appConstants.UpdateFlag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newtasktest.database.DBhandler;
import com.example.newtasktest.fragment.AddFragment;
import com.example.newtasktest.fragment.HomeFragment;
import com.example.newtasktest.R;
import com.example.newtasktest.fragment.LogoutFragment;
import com.example.newtasktest.fragment.ShowDetailsFragment;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    //Global declaration of variables
    DrawerLayout drawer;
    NavigationView nav_view;
    TextView txt_toolbar;
    ImageView addIcon,righticon;
    Toolbar toolbar;
    String email;
    int flag;
    DBhandler dBhandler;
    SharedPreferences sh;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.drawerlayout);
        nav_view = findViewById(R.id.navigationview);
        toolbar = findViewById(R.id.toolbar);
        txt_toolbar = findViewById(R.id.txt_toolbar);
        addIcon = findViewById(R.id.addicon);
        righticon= findViewById(R.id.righticon);
        txt_toolbar.setText("Home");
        dBhandler= new DBhandler(this);

        HomeFragment homeFragment = new HomeFragment();
        method(homeFragment);

        righticon.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                MenuBuilder menuBuilder =new MenuBuilder(MainActivity.this);
                MenuInflater inflater = new MenuInflater(MainActivity.this);
                inflater.inflate(R.menu.right_menu, menuBuilder);
                MenuPopupHelper optionsMenu = new MenuPopupHelper(MainActivity.this, menuBuilder, v);
                optionsMenu.setForceShowIcon(true);

                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.show: // Handle option1 Click
                                if(dBhandler.checkData()) {
                                    ShowDetailsFragment show = new ShowDetailsFragment();
                                    method(show);
                                    addIcon.setVisibility(View.INVISIBLE);
                                    txt_toolbar.setText("User Details");
                                }else
                                    Toast.makeText(MainActivity.this, "Database is empty", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.addd: // Handle option2 Click
                                sh= getSharedPreferences(My_preference, MODE_PRIVATE);
                                SharedPreferences.Editor editor= sh.edit();
                                editor.putInt(UpdateFlag,0);
                                editor.apply();
                                AddFragment addFragment= new AddFragment();
                                method(addFragment);
                                addIcon.setVisibility(View.INVISIBLE);
                                txt_toolbar.setText("Add User");
                                return true;
                            default:
                                return false;
                        }
                    }

                    @Override
                    public void onMenuModeChange(MenuBuilder menu) {}
                });

                optionsMenu.show();


            }
        });


        //add icon on click listener
        addIcon.setOnClickListener(v -> {
            sh= getSharedPreferences(My_preference, MODE_PRIVATE);
            SharedPreferences.Editor editor= sh.edit();
            editor.putInt(UpdateFlag,0);
            editor.apply();
            method(new AddFragment());
            addIcon.setVisibility(View.INVISIBLE);
            txt_toolbar.setText("Add User");
        });

        //shared preferences get info
        sh = getSharedPreferences(My_preference, MODE_PRIVATE);
        email = sh.getString(Email, "");
        flag = sh.getInt(Flag, 0);



        //navigation menu separator
        NavigationMenuView navMenuView = (NavigationMenuView) nav_view.getChildAt(0);

        //tool bar icon
        toolbar.setNavigationIcon(R.drawable.baseline_menu_24);
        navMenuView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));

        //toolbar navigation icon on click
        toolbar.setNavigationOnClickListener(v -> {
            drawer.openDrawer(GravityCompat.START);
            nav_view.setNavigationItemSelectedListener(item -> {
                int itemId = item.getItemId();
                switch (itemId) {
                    case (R.id.home):
                        addIcon.setVisibility(View.VISIBLE);
                        drawer.closeDrawers();
                        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                            for (int i = 1; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
                                getSupportFragmentManager().popBackStack();
                            }
                        }
                        txt_toolbar.setText("Home");
                        break;

                    case (R.id.add):
                        sh= getSharedPreferences(My_preference, MODE_PRIVATE);
                        SharedPreferences.Editor editor= sh.edit();
                        editor.putInt(UpdateFlag,0);
                        editor.apply();
                        AddFragment fragment = new AddFragment();
                        method(fragment);
                        addIcon.setVisibility(View.INVISIBLE);
                        txt_toolbar.setText("Add User");
                        drawer.closeDrawers();
                        break;

                    case (R.id.logout):
                        addIcon.setVisibility(View.INVISIBLE);
                        LoginScreen login = new LoginScreen();
                        LogoutFragment logoutFragment = new LogoutFragment();
                        method(logoutFragment);
                        txt_toolbar.setText("Log out");
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Do you want to exit ?");

                        builder.setTitle("Alert !");

                        builder.setCancelable(false);


                        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                            replaceActivity(login);
                            finish();
                            SharedPreferences pref = getApplicationContext().getSharedPreferences(My_preference, MODE_PRIVATE);
                            SharedPreferences.Editor editor1 = pref.edit();
                            editor1.clear();
                            editor1.apply();

                        });

                        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {

                            dialog.cancel();
                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        break;
                }
                return false;
            });
        });

    }

    //method to replace and close drawer
    public void method(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).addToBackStack(null).commit();
        drawer.closeDrawers();
    }

    public void replaceActivity(Activity activity) {
        Intent intent = new Intent(MainActivity.this, activity.getClass());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 1) {
            for (int i = 1; i < count; i++) {
                getSupportFragmentManager().popBackStack();
            }
            txt_toolbar.setText("Home");
            addIcon.setVisibility(View.VISIBLE);
        } else if (count == 1) {
            finish();
        }
    }


}