package com.fitpal.fitpal.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.fitpal.fitpal.MainActivity.KeyUserMealsFromDB;

public class FriendDetailMeals extends RecyclerView.Adapter<FriendDetailMeals.MyViewHolder> {

    private Context context;
    private List<UserMeal> mealList;
    DatabaseReference db;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,date;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.mealNameId);
            date=(TextView)view.findViewById(R.id.mealDateId);
        }
    }


    public FriendDetailMeals(List<UserMeal> resList,Context context) {

        this.mealList = resList;
        this.context = context;
    }

    @Override
    public FriendDetailMeals.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meal_result_row, parent, false);

        return new FriendDetailMeals.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final UserMeal res = mealList.get(position);
        Log.d("p1",String.valueOf(mealList));
        Log.d("pn",String.valueOf(res));


        holder.name.setText(res.MealName);
        holder.date.setText(res.Date);

    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }
}
