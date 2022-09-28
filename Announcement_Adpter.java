package com.example2.playwithus;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Announcement_Adpter extends RecyclerView.Adapter<Announcement_Adpter.AnnouncementHolder>{
    Context context ;
    List<Announcement> announcementList;
    Announcement announcement;
    private ChatFragment chatFragment=new ChatFragment();



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
            holder.image_type.setImageResource(R.mipmap.voolyiconpng);
        }else if (announcement.getType().contains("Basketball")){
            holder.image_type.setImageResource(R.mipmap.images_bac);
        }else if (announcement.getType().contains("Baloot")){
            holder.image_type.setImageResource(R.mipmap.blloticon);
        }
        holder.text_Time.setText(announcement.getTime());
        holder.text_Date.setText(announcement.getDate());
        holder.text_Details.setText(announcement.getDetails());

        holder.image_GPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Intent.ACTION_VIEW);
                Toast.makeText(context, announcement.getLocation().toString(), Toast.LENGTH_SHORT).show();
                intent.setData(Uri.parse("geo:"+announcement.getLocation().toString()+"?z=19"));
                 context.startActivity(intent);
            }
        });

        holder.image_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getSupportFragmentManager().beginTransaction().replace(R.id.container,chatFragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return announcementList.size();
    }

    public class AnnouncementHolder extends RecyclerView.ViewHolder {
        ImageView image_type , image_arrow ,image_GPS;
        TextView text_Time ,text_Date ,text_Details;
        public AnnouncementHolder(@NonNull View itemView) {
            super(itemView);

            text_Date=itemView.findViewById(R.id.date_text);
            text_Details= itemView.findViewById(R.id.details_text);
            text_Time =itemView.findViewById(R.id.time_text);
            image_type =itemView.findViewById(R.id.type_image);
            image_arrow =itemView.findViewById(R.id.arrow_image);
            image_GPS =itemView.findViewById(R.id.GPS_image);

        }
    }



}
