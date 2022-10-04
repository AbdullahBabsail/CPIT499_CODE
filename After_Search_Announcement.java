package com.example2.playwithus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class After_Search_Announcement extends AppCompatActivity {
    private ProgressDialog porgressDialog;
    private Date act_date;
    private RecyclerView recyclerView;
    private Announcement_Adpter announcementAdpter;
    private static List<Announcement> announcementList2;
    private Date now = new Date();
    private String type ,time;
    public  static String SEARCH_ANNOUNCEMENT="http://10.0.2.2/mysql/Search_Announcement.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_search_announcement);

        porgressDialog= new ProgressDialog(this);
        recyclerView=findViewById(R.id.search_recyc);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        announcementList2 =new ArrayList<>();

        LoadSearch();
    }

    private void LoadSearch() {
        //porgressDialog.setMessage("loading");
        //porgressDialog.setCancelable(true);
       // porgressDialog.show();
         Intent i=getIntent();
         type =i.getStringExtra("type");
         time =i.getStringExtra("time");
        //Toast.makeText(this, time, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, type, Toast.LENGTH_SHORT).show();
        /*
        JsonArrayRequest request =new JsonArrayRequest(SEARCH_ANNOUNCEMENT, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i=0; i<response.length(); i++){
                    try {
                        Toast.makeText(After_Search_Announcement.this, "ON RESPOND", Toast.LENGTH_SHORT).show();
                        JSONObject object =response.getJSONObject(i);
                        String Type = object.getString("Type").trim();
                        String Time = object.getString("Time").trim();
                        String Date = object.getString("Date").trim();
                        String Details = object.getString("Details").trim();
                        String Location = object.getString("Location").trim();
                        String Owner = object.getString("Owner").trim();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        act_date=sdf.parse(Date);


                        if(Owner.contains(MainActivity.pass_user_name) || sdf.format(act_date).compareTo(sdf.format(now))<=0){
                            continue;
                        }else{
                            Announcement announcement = new Announcement();
                            announcement.setDate(Date);
                            announcement.setDetails(Details);
                            announcement.setLocation(Location);
                            announcement.setOwner(Owner);
                            announcement.setType(Type);
                            announcement.setTime(Time);

                            announcementList2.add(announcement);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                announcementAdpter = new Announcement_Adpter(announcementList2,After_Search_Announcement.this);
                recyclerView.setAdapter(announcementAdpter);


                StringRequest request=new StringRequest(Request.Method.POST , SEARCH_ANNOUNCEMENT ,new
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(After_Search_Announcement.this, error.getMessage(), Toast.LENGTH_LONG).show();
                Toast.makeText(After_Search_Announcement.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Toast.makeText(After_Search_Announcement.this, "ON PARA", Toast.LENGTH_SHORT).show();
                Map <String , String> mapData=new HashMap<>();
                mapData.put("Activity_Type",type);
                mapData.put("Activity_Time",time);
                return mapData;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(After_Search_Announcement.this);
        queue.add(request);
        */

        StringRequest request =new StringRequest(Request.Method.POST,SEARCH_ANNOUNCEMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                JSONObject jsonObject = new JSONObject(response);
                String result = jsonObject.getString("success");
                    JSONArray jsonArray= jsonObject.getJSONArray("data");
                    if(result.equals("good")) {
                        for (int i=0; i<jsonArray.length(); i++){

                            JSONObject object =jsonArray.getJSONObject(i);
                            String Type = object.getString("Type").trim();
                            String Time = object.getString("Time").trim();
                            String Date = object.getString("Date").trim();
                            String Details = object.getString("Details").trim();
                            String Location = object.getString("Location").trim();
                            String Owner = object.getString("Owner").trim();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            act_date=sdf.parse(Date);

                            if(Owner.contains(MainActivity.pass_user_name) || sdf.format(act_date).compareTo(sdf.format(now))<=0){
                                continue;
                            }else{
                                Announcement announcement = new Announcement();
                                announcement.setDate(Date);
                                announcement.setDetails(Details);
                                announcement.setLocation(Location);
                                announcement.setOwner(Owner);
                                announcement.setType(Type);
                                announcement.setTime(Time);

                                announcementList2.add(announcement);
                            }

                        }
                        announcementAdpter = new Announcement_Adpter(announcementList2,After_Search_Announcement.this);
                        recyclerView.setAdapter(announcementAdpter);

                    }else if(result.equals("bad")){
                        Toast.makeText(After_Search_Announcement.this, "Something Went Wrong Pleas Try Again", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(After_Search_Announcement.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String , String> mapData=new HashMap<>();
                mapData.put("Activity_Type",type);
                mapData.put("Activity_Time",time);
                return mapData;
            }
        };

        final RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
