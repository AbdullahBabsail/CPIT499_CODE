package com.example2.playwithus;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SearchFragment extends Fragment {
    private ListView listView;
    private String[]SearchOptions={"Football 5 - 7","Football 5:30 - 7:30","Football 7 - 9","Football 7:30 - 9:30","Football 9 - 11","Football 9:30 - 11:30","Football 11 - 1",
                                   "Football 11:30 - 1:30","Football 1 - 3","Football 1:30 - 3:30","Volleyball 5 - 7","Volleyball 5:30 - 7:30","Volleyball 7 - 9",
                                   "Volleyball 7:30 - 9:30","Volleyball 9 - 11","Volleyball 9:30 - 11:30","Volleyball 11 - 1","Volleyball 11:30 - 1:30","Volleyball 1-3",
                                   "Volleyball 1:30 - 3:30","Basketball 5 - 7","Basketball 5:30 - 7:30","Basketball 7 - 9","Basketball 7:30 - 9:30","Basketball 9 - 11","Basketball 9:30 - 11:30",
                                   "Basketball 11 - 1","Basketball 11:30 - 1:30","Basketball 1 - 3","Basketball 1:30 - 3:30","Baloot 5 - 7","Baloot 5:30 - 7:30","Baloot 7 - 9",
                                    "Baloot 7:30 - 9:30","Baloot 9 - 11", "Baloot 9:30 - 11:30","Baloot 11 - 1","Baloot 11:30 - 1:30","Baloot 1 - 3","Baloot 1:30 - 3:30"};
    private ArrayAdapter<String> arrayAdapter;
    private MenuItem menuItem;
    private SearchView searchView;
    private String actType ,actTime , data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
       View root= inflater.inflate(R.layout.fragment_search, container, false);

        listView=root.findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,SearchOptions);//predefind layout android.R.layout.simple_list_item_1
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                data =adapterView.getItemAtPosition(i).toString();
                String[] allData = data.split(" ");
                actType=allData[0];
                actTime=allData[1]+" "+allData[2]+" "+allData[3];
                //Toast.makeText(getContext(), actType+" "+ actTime, Toast.LENGTH_SHORT).show();
               // Toast.makeText(getContext(),adapterView.getItemAtPosition(i).toString() , Toast.LENGTH_SHORT).show();

                LoadSearch(actType,actTime);
            }
        });

        return root;
    }

    private void LoadSearch(String type , String time) {
        Intent intent=new Intent(getContext(),After_Search_Announcement.class);
        intent.putExtra("type",type);
        intent.putExtra("time",time);
        startActivity(intent);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu3,menu);
        menuItem = menu.findItem(R.id.searchBar);
        searchView= (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            //when the user type and press enter
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            //everytime the user enter a cher
            public boolean onQueryTextChange(String s) {
                arrayAdapter.getFilter().filter(s);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}
