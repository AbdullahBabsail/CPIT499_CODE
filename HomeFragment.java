package com.example2.playwithus;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    public  static String HOME_ANNOUNCEMENT="http://10.0.2.2/mysql/Home_Announcement.php";
    private RecyclerView recyclerView;
    private Announcement_Adpter announcementAdpter;
    private static List<Announcement> announcementList;
    private Date now = new Date();
    private Date act_date;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_home, container, false);


        recyclerView= root.findViewById(R.id.recycView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        announcementList =new ArrayList<>();


        LoadAllAnnouncement();



        return root;

    }

    private void LoadAllAnnouncement() {
        JsonArrayRequest request =new JsonArrayRequest(HOME_ANNOUNCEMENT, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                for (int i=0; i<response.length(); i++){
                    try {
                        JSONObject object =response.getJSONObject(i);
                        String Type = object.getString("Type").trim();
                        String Time = object.getString("Time").trim();
                        String Date = object.getString("Date").trim();
                        String Details = object.getString("Details").trim();
                        String Location = object.getString("Location").trim();
                        String Owner = object.getString("Owner").trim();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        act_date=sdf.parse(Date);
                        //sdf.format(now);
                        //sdf.format(act_date);




                        if(Owner.contains(MainActivity.pass_user_name) || sdf.format(act_date).compareTo(sdf.format(now))<=0){
                            continue;
                        }else {

                            Announcement announcement = new Announcement();
                            announcement.setDate(Date);
                            announcement.setDetails(Details);
                            announcement.setLocation(Location);
                            announcement.setOwner(Owner);
                            announcement.setType(Type);
                            announcement.setTime(Time);

                            announcementList.add(announcement);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                announcementAdpter = new Announcement_Adpter(announcementList,getContext());
                recyclerView.setAdapter(announcementAdpter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeFragment.this.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        queue.add(request);

    }
}
