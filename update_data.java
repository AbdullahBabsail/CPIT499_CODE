package com.example2.playwithus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class update_data extends AppCompatActivity {
  private EditText PhoneNum ,Username ,Fullname,Email;
  private Button conformData;
  private String newPhoneNum ,newUsername ,newFullname,newEmail;
  public static String oldusername ,oldemail, oldfullname ,oldphone ;
  private ProgressDialog porgressDialog;
  public  static String UpdateURL="http://10.0.2.2/mysql/Update_data.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        Username=findViewById(R.id.edited_username);
        Fullname=findViewById(R.id.edited_fullname);
        Email=findViewById(R.id.edited_email);
        PhoneNum=findViewById(R.id.edited_phonenumber);

        Username.setText(oldusername);
        Fullname.setText(oldfullname);
        Email.setText(oldemail);
        PhoneNum.setText(oldphone);

        porgressDialog= new ProgressDialog(this);

        conformData=findViewById(R.id.conform_edit_profile);

        conformData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newUsername = Username.getText().toString().trim();
                newEmail = Email.getText().toString().trim();
                newFullname = Fullname.getText().toString().trim();
                newPhoneNum = PhoneNum.getText().toString().trim();

                if(TextUtils.isEmpty(newUsername) || TextUtils.isEmpty(newEmail) || TextUtils.isEmpty(newFullname) ||TextUtils.isEmpty(newPhoneNum)){
                    Toast.makeText(update_data.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }else{
                    porgressDialog.setTitle("Loading....");
                    porgressDialog.show();

                    StringRequest stringRequest =new StringRequest(Request.Method.POST, UpdateURL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            porgressDialog.dismiss();
                            if(response.contains("Existing User Data")) {
                                Toast.makeText(update_data.this, response, Toast.LENGTH_SHORT).show();
                            }else if(response.contains("Something Went Wrong Try Again")){
                                Toast.makeText(update_data.this, response, Toast.LENGTH_SHORT).show();
                            }else if (response.contains("UnAthorized User")){
                                Toast.makeText(update_data.this, response, Toast.LENGTH_SHORT).show();
                            }else{
                                oldemail=newEmail;
                                oldfullname=newFullname;
                                oldusername=newUsername;
                                oldphone=newPhoneNum;
                                ProfileActivity.fullname=newFullname;
                                ProfileActivity.email=newEmail;
                                ProfileActivity.username=newUsername;
                                ProfileActivity.phone=newPhoneNum;

                                Toast.makeText(update_data.this, response, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(update_data.this,ProfileActivity.class));
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            porgressDialog.dismiss();
                            Toast.makeText(update_data.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }){

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String ,String> updateMap =new HashMap<>();
                            updateMap.put("Username", newUsername);
                            updateMap.put("Fullname", newFullname);
                            updateMap.put("Email"   , newEmail);
                            updateMap.put("Phonenum", newPhoneNum);
                            updateMap.put("OldEmail", oldemail);
                            updateMap.put("oldUsername",oldusername);

                            return updateMap;
                        }
                    };

                    RequestQueue queue = Volley.newRequestQueue(update_data.this);
                    queue.add(stringRequest);


                }

            }
        });




    }
}