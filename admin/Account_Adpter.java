package com.example2.admin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class Account_Adpter extends RecyclerView.Adapter<Account_Adpter.ViewHolder> {
    private Context context ;
    private List<User> userList;
    private User user;
    private static String Banned_User="http://10.0.2.2/mysql/Admin_Banned_User.php";


    public Account_Adpter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View userLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_view_design ,parent,false);
        return new ViewHolder(userLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        user = userList.get(position);

        holder.username.setText(user.getUsernmae());
        holder.fullname.setText(user.getFullname());
        holder.email.setText(user.getEmail());
        holder.phonenumber.setText(user.getPhonenumber());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView username , fullname ,email,phonenumber;
        private ImageView banned;
        private AlertDialog.Builder builder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.Username_text);
            fullname= itemView.findViewById(R.id.Fullname_text);
            email=itemView.findViewById(R.id.Email_text);
            phonenumber = itemView.findViewById(R.id.Phonenumber_text);
            banned=itemView.findViewById(R.id.banned_image);
            builder =new AlertDialog.Builder(context);


            banned.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i =getAdapterPosition();
                    user=userList.get(i);
                    builder.setTitle("Conformation").setMessage("Are You Sure You Want To Banned User "+user.getUsernmae())
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
                                            mapData.put("Username",user.getUsernmae());
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
