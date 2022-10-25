package com.example2.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AccountFragment extends Fragment {
    private RecyclerView recyclerView;
    private Account_Adpter accountAdpterr;
    private static List<User> userList;
    private  static String Admin_users_page ="http://10.0.2.2/mysql/Admin_User_Show1.php";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View root=  inflater.inflate(R.layout.fragment_account, container, false);

        recyclerView= root.findViewById(R.id.recycView1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        userList =new ArrayList<>();

        LoadAllAnnouncement();


        return root;
    }

    private void LoadAllAnnouncement() {

        JsonArrayRequest request =new JsonArrayRequest(Admin_users_page, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                for (int i=0; i<response.length(); i++){
                    try {

                        JSONObject object =response.getJSONObject(i);
                        String Username = object.getString("username").trim();
                        String Email = object.getString("email").trim();
                        String Fullname = object.getString("fullname").trim();
                        String Phone = object.getString("phone").trim();
                        String banned = object.getString("banned").trim();

                        if(banned.contains("T")){
                            continue;
                        }else{
                            User user = new User();
                            user.setUsernmae(Username);
                            user.setEmail(Email);
                            user.setFullname(Fullname);
                            user.setPhonenumber(Phone);
                            user.setBanned(banned);
                            userList.add(user);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                accountAdpterr = new Account_Adpter(getContext(),userList);
                recyclerView.setAdapter(accountAdpterr);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AccountFragment.this.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){

        };

        final RequestQueue queue = Volley.newRequestQueue(this.getContext());
        queue.add(request);
    }
}
