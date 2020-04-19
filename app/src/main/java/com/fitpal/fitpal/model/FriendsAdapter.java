package com.fitpal.fitpal.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fitpal.fitpal.FriendDetails;
import com.fitpal.fitpal.MyGroup;
import com.fitpal.fitpal.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import static com.fitpal.fitpal.MainActivity.KeyUserMealsFromDB;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.MyViewHolder> {
    private Context context;
    private List<Users> resList;
    DatabaseReference db;
    public static Users tempUser;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,key;
        public ImageButton delete;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.friend_name_id);
            key=(TextView)view.findViewById(R.id.keyIdGroupDisp);
            delete=(ImageButton)view.findViewById(R.id.deleteFriend);
        }
    }


    public FriendsAdapter(List<Users> resList,Context context) {

        this.resList = resList;
        this.context = context;
    }

    @Override
    public FriendsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friends_result_row, parent, false);

        return new FriendsAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(FriendsAdapter.MyViewHolder holder, int position) {
        final Users res = resList.get(position);
        String m=res.Email.substring(0,res.Email.indexOf('@'));
        holder.name.setText(m);
        holder.key.setText(String.valueOf(res.KeyUserMeals));
        final String p=String.valueOf(res.KeyUserMeals);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db = FirebaseDatabase.getInstance().getReference("UserFriends")
                        .child(String.valueOf(KeyUserMealsFromDB)).child(p);
                db.removeValue();
                context.startActivity(new Intent(context,MyGroup.class));
                ((Activity)context).finish();

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i=new Intent(context,FriendDetails.class);
                tempUser=res;
                context.startActivity(new Intent(context,FriendDetails.class));
                //((Activity)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return resList.size();
    }
}
