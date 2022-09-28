package com.example2.playwithus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class add_announcement extends AppCompatActivity  {

    private Spinner spinner_type;
    private Spinner spinner_time;
    //private String activity_type;
    //private String activity_time;
    private EditText detail ;
    public static EditText location;
    private TextView selectLocation;
    private Button announce;
    public  static String AnnounceURL="http://10.0.2.2/mysql/Add_announcement.php";
    private ProgressDialog porgressDialog;
    private String activity_details, activity_location ,activity_type, activity_date, activity_time,user_name;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_announcement);

        spinner_type=findViewById(R.id.spinner_type);
        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(this, R.array.act_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type.setAdapter(adapter);
        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                activity_type=adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(add_announcement.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
            }
        });

        spinner_time=findViewById(R.id.spinner_Time);
        ArrayAdapter<CharSequence> adapter2 =ArrayAdapter.createFromResource(this, R.array.act_time, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_time.setAdapter(adapter2);
        spinner_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                activity_time=adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(add_announcement.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
            }
        });

        detail=findViewById(R.id.edit_describe);
        calendar=Calendar.getInstance();
        dateButton=findViewById(R.id.datePickerButton);
        location=findViewById(R.id.edit_location);
        selectLocation=findViewById(R.id.select_location);
        announce=findViewById(R.id.Button_announce);
        porgressDialog= new ProgressDialog(this);

        initDatePicker();
        //datePickerDialog.dismiss();

        selectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(add_announcement.this,GPSActivity.class);
                startActivity(intent);
            }
        });

        announce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 activity_details= detail.getText().toString().trim();
                 activity_date = dateButton.getText().toString().trim();
                 activity_location = location.getText().toString().trim();
                 user_name =MainActivity.pass_user_name;

                 if(TextUtils.isEmpty(activity_date)&&TextUtils.isEmpty(activity_type) && TextUtils.isEmpty(activity_time) && TextUtils.isEmpty(activity_details) && TextUtils.isEmpty(activity_location)&& TextUtils.isEmpty(user_name)){
                    Toast.makeText(add_announcement.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                }else{

                    porgressDialog.setMessage("Uploading Your Announcement");
                    porgressDialog.setCancelable(true);
                    porgressDialog.show();

                    StringRequest stringRequest =new StringRequest(Request.Method.POST, AnnounceURL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            porgressDialog.dismiss();
                            Toast.makeText(add_announcement.this, response, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(add_announcement.this,Main_Page_App.class));

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            porgressDialog.dismiss();
                            Toast.makeText(add_announcement.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map <String , String> mapData=new HashMap<>();
                            mapData.put("Activity_Type",activity_type);
                            mapData.put("Activity_Time",activity_time);
                            mapData.put("Activity_Details",activity_details);
                            mapData.put("Activity_Date",activity_date);
                            mapData.put("Activity_Location",activity_location);
                            mapData.put("Activity_Owner",user_name);
                            return mapData;

                        }
                    };

                    final RequestQueue queue = Volley.newRequestQueue(add_announcement.this);
                    queue.add(stringRequest);

                }


            }
        });



    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }
    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);

            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;


        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());



    }
    private String makeDateString(int day, int month, int year)
    {
        return  year+"-"+ month + "-" + day;
    }
    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }



}
