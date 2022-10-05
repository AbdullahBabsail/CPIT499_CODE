package com.example2.playwithus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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

public class Show_User_Announcement extends AppCompatActivity {
    private Date act_date;
    private RecyclerView recyclerView;
    private Announcement_Adpter announcementAdpter;
    private static List<Announcement> announcementList2;
    private Date now = new Date();
    private TextView textView;
    public  static String SEARCH_ANNOUNCEMENT="http://10.0.2.2/mysql/Owner_Annoncement.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_announcement);

        recyclerView=findViewById(R.id.user_recyc);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        announcementList2 =new ArrayList<>();
        textView = findViewById(R.id.non_found2);


        LoadSearch();
    }

    private void LoadSearch() {

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

                                Announcement announcement = new Announcement();
                                announcement.setDate(Date);
                                announcement.setDetails(Details);
                                announcement.setLocation(Location);
                                announcement.setOwner(Owner);
                                announcement.setType(Type);
                                announcement.setTime(Time);

                                announcementList2.add(announcement);


                        }
                        if(announcementList2.size()==0){
                            textView.setText("Nothing Found");
                        }
                        announcementAdpter = new Announcement_Adpter(announcementList2,Show_User_Announcement.this);
                        recyclerView.setAdapter(announcementAdpter);

                    }else if(result.equals("bad")){
                        Toast.makeText(Show_User_Announcement.this, "Something Went Wrong Pleas Try Again", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Show_User_Announcement.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String , String> mapData=new HashMap<>();
                mapData.put("User_name",MainActivity.pass_user_name);
                return mapData;
            }
        };

        final RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

}
