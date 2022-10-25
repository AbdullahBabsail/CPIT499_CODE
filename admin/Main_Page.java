package com.example2.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Main_Page extends AppCompatActivity {
    private MenuInflater menu;
    private BottomNavigationView bottomNavigationView;
    private AccountFragment accountFragment =new AccountFragment();
    private BannedFragment chatFragment=new BannedFragment();
    private AccouncementFragment accouncementFragment =new AccouncementFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        bottomNavigationView=findViewById(R.id.botton_navi);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, accountFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if(item.getItemId()==R.id.home){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, accountFragment).commit();

                }else if(item.getItemId()==R.id.announcement){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,accouncementFragment).commit();

                }else if(item.getItemId()==R.id.chat){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,chatFragment ).commit();
                }
                return true;
            }
        });

    }
}
