package com.example2.playwithus;

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

public class change_pass2 extends AppCompatActivity {
    private String oldpassword2, newpassword2 , conformpassword2;
    public static String email;
    private EditText oldpass2 , newpass2, conformpass2;
    private Button password_update;
    public  static String ChangePassURL ="http://10.0.2.2/mysql/ChangePass.php";
    private ProgressDialog porgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass2);

        oldpass2 = findViewById(R.id.oldPassword_edit2);
        newpass2 =findViewById(R.id.newPassword_edit2);
        conformpass2 =findViewById(R.id.conformPassword_edit2);


        porgressDialog= new ProgressDialog(this);


        password_update=findViewById(R.id.update_password_button2);
        password_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldpassword2 = oldpass2.getText().toString().trim();
                newpassword2 = newpass2.getText().toString().trim();
                conformpassword2 = conformpass2.getText().toString().trim();

                if(TextUtils.isEmpty(oldpassword2) || TextUtils.isEmpty(newpassword2) || TextUtils.isEmpty(conformpassword2)){
                    Toast.makeText(change_pass2.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }else{
                    porgressDialog.setMessage("loading");
                    porgressDialog.setCancelable(true);
                    porgressDialog.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, ChangePassURL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            porgressDialog.dismiss();
                            if(response.contains("Please conform the old password")){
                                Toast.makeText(change_pass2.this, response, Toast.LENGTH_SHORT).show();
                            }else if(response.contains("Please Match the new Passwords")){
                                Toast.makeText(change_pass2.this, response, Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(change_pass2.this, response, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(change_pass2.this,MainActivity.class));
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            porgressDialog.dismiss();
                            Toast.makeText(change_pass2.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }){

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map <String,String> param= new HashMap<>();
                            param.put("oldpassword", oldpassword2);
                            param.put("newpassword", newpassword2);
                            param.put("conformpassword", conformpassword2);
                            param.put("email",email);
                            return param;
                        }
                    };
                    RequestQueue queue = Volley.newRequestQueue(change_pass2.this);
                    queue.add(stringRequest);

                }

            }
        });
    }
}