package com.fitpal.fitpal.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fitpal.fitpal.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder> {

    private List<Restaurant> resList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, address, cost;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.restaurant_name_id);
            address = (TextView) view.findViewById(R.id.restaurant_address_id);
            cost = (TextView) view.findViewById(R.id.restaurant_avgcost_id);
        }
    }


    public RestaurantAdapter(List<Restaurant> resList) {

        this.resList = resList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_result_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Restaurant res = resList.get(position);
        holder.name.setText(res.getName());
        holder.address.setText(res.getAddress());
        holder.cost.setText(res.getCostForTwo());
    }

    @Override
    public int getItemCount() {
        return resList.size();
    }
}
