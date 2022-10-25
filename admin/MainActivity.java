package com.example2.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private TextView registerText;
    private EditText username,password;
    private String user_name="",user_pass="";
    private Button loginButton;
    private static String LoginURL="http://10.0.2.2/mysql/Admin_login.php";
    private ProgressDialog porgressDialog;
    public static String pass_user_name;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerText=findViewById(R.id.textViewReg);
        username=findViewById(R.id.editUsername);
        password=findViewById(R.id.editPassword);
        loginButton=findViewById(R.id.buttonLogin);
        porgressDialog= new ProgressDialog(this);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_name = username.getText().toString().trim();
                user_pass= password.getText().toString().trim();
                pass_user_name=user_name;

                if(TextUtils.isEmpty(user_pass) || TextUtils.isEmpty(user_name)){
                    Toast.makeText(MainActivity.this, "Please Fill in all the data", Toast.LENGTH_SHORT).show();
                }else{
                    porgressDialog.setMessage("logging In");
                    porgressDialog.setCancelable(true);
                    porgressDialog.show();

                    StringRequest request=new StringRequest(Request.Method.POST, LoginURL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String result = jsonObject.getString("success");
                                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                                        if (result.equals("LogIn_OK")) {
                                            porgressDialog.dismiss();
                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                JSONObject object = jsonArray.getJSONObject(i);
                                                String username = object.getString("username");
                                                String email = object.getString("email");
                                                String fullname = object.getString("fullname");
                                                String phone = object.getString("phone");
                                                //ProfileActivity.username =username;
                                                //ProfileActivity.email=email;
                                                //ProfileActivity.fullname=fullname;
                                                //ProfileActivity.phone=phone;
                                                //change_pass2.email=email;
                                            }
                                            Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(MainActivity.this,Main_Page.class));

                                        } else if (result.equals("LogIn_Error")) {
                                            porgressDialog.dismiss();
                                            Toast.makeText(MainActivity.this, "Something Went Wrong try again", Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (JSONException E) {
                                        E.getMessage();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            porgressDialog.dismiss();
                            Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map <String , String> mapData=new HashMap<>();
                            mapData.put("UserName",user_name);
                            mapData.put("PassWord",user_pass);
                            return mapData;

                        }
                    };

                    final RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    queue.add(request);
                }

            }
        });
    }
}
