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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private EditText emailReg ,usernameReg ,passwrodReg ,repasswordReg;
    private Button registerButton;
    private String user_name="",user_email="",user_pass="",user_repass="";
    private ProgressDialog porgressDialog;
    private static final String RegisterURL = "http://10.0.2.2/mysql/Registration.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailReg=findViewById(R.id.editEmailReg);
        usernameReg=findViewById(R.id.editUsernameReg);
        passwrodReg=findViewById(R.id.editPasswordReg);
        repasswordReg=findViewById(R.id.editRetypePasswordReg);
        registerButton=findViewById(R.id.buttonReg);
        porgressDialog= new ProgressDialog(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registration();
            }
        });

    }

    public void Registration(){
        user_name = usernameReg.getText().toString().trim();
        user_email = emailReg.getText().toString().trim();
        user_pass = passwrodReg.getText().toString().trim();
        user_repass = repasswordReg.getText().toString().trim();

        if(TextUtils.isEmpty(user_pass) || TextUtils.isEmpty(user_email) || TextUtils.isEmpty(user_name )||TextUtils.isEmpty(user_repass)){
            Toast.makeText(Register.this, "Please Fill in all the data", Toast.LENGTH_SHORT).show();

        }else if(!user_pass.equals(user_repass)){
            Toast.makeText(Register.this, "Passwords Not Match", Toast.LENGTH_SHORT).show();
        }else{
            //registerButton.setEnabled(false);
            porgressDialog.setMessage("Setting up your account");
            porgressDialog.setCancelable(true);
            porgressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, RegisterURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    porgressDialog.dismiss();

                    if(response.contains("Existing User")){
                        Toast.makeText(Register.this, response+" Try again", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(Register.this, response, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Register.this,Main_Page_App.class));
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    porgressDialog.dismiss();
                    System.out.println(error.getMessage());
                    Toast.makeText(Register.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map <String , String> mapData=new HashMap<>();
                    mapData.put("UserName",user_name);
                    mapData.put("Password",user_pass);
                    mapData.put("Email",user_email);
                    return mapData;
                }
            };

// dont forget about the sherrdPrefrancec and to send user data to the required pages **********************************
            final RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(stringRequest);


        }

    }
}