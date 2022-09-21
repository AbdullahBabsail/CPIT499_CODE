package com.example2.playwithus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
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

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    public static String username ,email, fullname ,phone ;
    private String oldpassword ,newpassword ,conformpassword;
    private TextView usernameText ,fullnametext ,emailtext , phonetext;
    Button editButton,passButton,showButton;
    private EditText oldpass , newpass , conformpass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        usernameText=findViewById(R.id.textView_username);
        fullnametext=findViewById(R.id.fullname_text);
        emailtext=findViewById(R.id.email_text);
        phonetext=findViewById(R.id.phone_text);
        editButton=findViewById(R.id.updateinfo_button);
        passButton=findViewById(R.id.changePass_button);
        showButton=findViewById(R.id.showmyads_button);


        usernameText.setText(username);
        emailtext.setText(email);
        if(fullname.equals("null") && phone.equals("null")){
            phonetext.setText("");
            fullnametext.setText(username);
        }else{
            phonetext.setText(phone);
            fullnametext.setText(fullname);
        }

        passButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,change_pass2.class));

            }


        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_data.oldusername=username;
                update_data.oldemail=email;
                update_data.oldfullname=fullname;
                update_data.oldphone=phone;
                startActivity(new Intent(ProfileActivity.this,update_data.class));


            }
        });

    }
}