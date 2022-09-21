package com.example2.playwithus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Main_Page_App extends AppCompatActivity {
    private MenuInflater menu;
    private BottomNavigationView bottomNavigationView;
    private HomeFragment homeFragment=new HomeFragment();
    private ChatFragment chatFragment=new ChatFragment();
    private SearchFragment searchFragment =new SearchFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_app);

        bottomNavigationView=findViewById(R.id.botton_navi);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if(item.getItemId()==R.id.home){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();

                }else if(item.getItemId()==R.id.Chat){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,chatFragment).commit();

                }else if(item.getItemId()==R.id.search){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,searchFragment).commit();
                }


                return true;

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu2 ,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.profile){
            startActivity(new Intent(Main_Page_App.this,ProfileActivity.class));
        }else if(item.getItemId()==R.id.setting){
            startActivity(new Intent(Main_Page_App.this,SettingActivity.class));
        }else if (item.getItemId()==R.id.add_announcment){
            startActivity(new Intent(Main_Page_App.this,add_announcement.class));
        }
        return true;
    }


}