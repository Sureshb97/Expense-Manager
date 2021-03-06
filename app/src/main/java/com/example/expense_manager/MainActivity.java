package com.example.expense_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
{
        BottomNavigationView bottomNavigationView;
        FrameLayout frameLayout;
        Context context;
//        Fragment active;
//        FragmentManager fragmentManager;
       Fragment fragment;
//        Fragment fragment1;
//        Fragment fragment2 ;
//        Fragment fragment3;

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            /*fragment1 = new HomeFragment(this);
            fragment2 = new AddFragment(this);
            fragment3 = new AboutFragment();*/
            //active=fragment1;

            bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_navigation);
            frameLayout=(FrameLayout)findViewById(R.id.frame);
            FragmentManager fragmentManager=getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                    .replace(R.id.frame,new HomeFragment(context))
                    .commit();
            /*ragmentManager.beginTransaction().add(R.id.frame, fragment3, "3").hide(fragment3).commit();
            fragmentManager.beginTransaction().add(R.id.frame, fragment2, "2").hide(fragment2).commit();
            fragmentManager.beginTransaction().add(R.id.frame,fragment1, "1").commit();*/

            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item)
                {
                    switch (item.getItemId())
                    {
                        case R.id.nav_home:
                            fragment=new HomeFragment(context);
                            /*fragmentManager.beginTransaction().hide(active).show(fragment1).commit();
                            active = fragment1;
                            return true;*/
                            break;

                        case R.id.nav_add:
                            fragment=new AddFragment(context);
                           /* fragmentManager.beginTransaction().hide(active).show(fragment2).commit();
                           active = fragment2;
                            return true;*/
                            break;

                        case R.id.nav_settings:
                            fragment=new AboutFragment();
                            /*fragmentManager.beginTransaction().hide(active).show(fragment3).commit();
                            active = fragment3;
                            return true;*/
                            break;
                    }

                    FragmentManager fragmentManager1=getSupportFragmentManager();

                    fragmentManager1.beginTransaction()
                            .replace(R.id.frame,fragment)
                            .commit();


                  return true;
                }
            });


        }

    @Override
    public void onBackPressed() {

        int seletedItemId = bottomNavigationView.getSelectedItemId();
        if (R.id.nav_home != seletedItemId) {
           bottomNavigationView.setSelectedItemId(R.id.nav_home);
        } else {
            super.onBackPressed();
        }
    }


}
