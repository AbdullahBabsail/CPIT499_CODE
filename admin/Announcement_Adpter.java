package com.example2.admin;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Announcement_Adpter extends RecyclerView.Adapter<Announcement_Adpter.AnnouncementHolder>{
    private Context context ;
    private List<Announcement> announcementList;
    private Announcement announcement;
    private static String Banned_User="http://10.0.2.2/mysql/Admin_Banned_User.php";



    public Announcement_Adpter( List<Announcement> announcementList ,Context context) {
        this.context=context;
        this.announcementList = announcementList;
    }


    @NonNull
    @Override
    public AnnouncementHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View announcementLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.announcement_view_design ,parent,false);
        return new AnnouncementHolder(announcementLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementHolder holder, int position) {
        announcement =announcementList.get(position);

        if (announcement.getType().contains("Football")){
            holder.image_type.setImageResource(R.mipmap.images);
        }else if (announcement.getType().contains("Volleyball")){
            holder.image_type.setImageResource(R.mipmap.volleyball);
        }else if (announcement.getType().contains("Basketball")){
            holder.image_type.setImageResource(R.mipmap.images_bac);
        }else if (announcement.getType().contains("Baloot")){
            holder.image_type.setImageResource(R.mipmap.blloticon);
        }
        holder.text_Time.setText(announcement.getTime());
        holder.text_Date.setText(announcement.getDate());
        holder.text_Details.setText(announcement.getDetails());
        holder.text_owner.setText(announcement.getOwner());


    }

    @Override
    public int getItemCount() {
        return announcementList.size();
    }


    public class AnnouncementHolder extends RecyclerView.ViewHolder {
        private ImageView image_type , image_banned,image_GPS;
        private TextView text_Time ,text_Date ,text_Details , text_owner;
        private AlertDialog.Builder builder;

        public AnnouncementHolder(@NonNull View itemView) {
            super(itemView);

            text_Date=itemView.findViewById(R.id.Fullname_text);
            text_Details= itemView.findViewById(R.id.Username_text);
            text_Time =itemView.findViewById(R.id.Email_text);
            image_type =itemView.findViewById(R.id.type_image);
            image_banned =itemView.findViewById(R.id.banned_image);
            image_GPS =itemView.findViewById(R.id.GPS_image);
            text_owner = itemView.findViewById(R.id.Phonenumber_text);
            builder =new AlertDialog.Builder(context);


            image_GPS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i =getAdapterPosition();
                    announcement =announcementList.get(i);
                    Intent intent =new Intent(Intent.ACTION_VIEW);
                    Toast.makeText(context, announcement.getLocation(), Toast.LENGTH_SHORT).show();
                    intent.setData(Uri.parse("geo:"+announcement.getLocation()+"?z=19"));
                    context.startActivity(intent);


                }
            });

            image_banned.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i =getAdapterPosition();
                    announcement=announcementList.get(i);
                    builder.setTitle("Conformation").setMessage("Are You Sure You Want To Banned User "+announcement.getOwner())
                            .setCancelable(true)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    StringRequest request = new StringRequest(Request.Method.POST, Banned_User, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }){
                                        @Nullable
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map <String , String> mapData=new HashMap<>();
                                            mapData.put("Username",announcement.getOwner());
                                            return mapData;
                                        }
                                    };
                                    final RequestQueue queue = Volley.newRequestQueue(context);
                                    queue.add(request);

                                }
                            }) .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            }) .show();

                }
            });

        }
    }



}

