package com.fitpal.fitpal;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;


import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class FoodResults extends AppCompatActivity {


    private TextView name,carbs,energy,fat,protein,mineral,calcium,iron,fibre,moisture,phosphorous;
    String foodToSearch;
    DatabaseReference databaseReference;
    FoodItem print;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_results);

        name=findViewById(R.id.FoodNameId);
        carbs=findViewById(R.id.CarbId);
        energy=findViewById(R.id.EnergyId);
        fat=findViewById(R.id.FatId);
        protein=findViewById(R.id.ProteinId);
        mineral=findViewById(R.id.MineralsId);
        calcium=findViewById(R.id.CalciumId);
        iron=findViewById(R.id.IronId);
        fibre=findViewById(R.id.FibreId);
        moisture=findViewById(R.id.MoistureId);
        phosphorous=findViewById(R.id.PhosphorousId);

        Intent in=getIntent();

        foodToSearch=in.getStringExtra("foodresult");
        Log.d("outtttt",foodToSearch);

        databaseReference = FirebaseDatabase.getInstance().getReference("Foods");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    FoodItem f1 = dataSnapshot1.getValue(FoodItem.class);
                    Log.d("foodddddd",String.valueOf(f1));
                    Log.d("searchhhh",foodToSearch);

                    String n = f1.Name;
                    if (n!= null) {

                        Log.d("beforeeee",n);

                    }
                    else{
                        Log.d("beforeeee","kuch nahi");
                    }


                    if(foodToSearch.equalsIgnoreCase(n)) {
                        print=f1;
                        Log.d("naaaaam",n);
                        break;
                    }
                }
                name.setText(print.Name);
                carbs.setText(print.Carbos);
                energy.setText(print.Energy);
                fat.setText(print.Fat);
                protein.setText(print.Protein);
                mineral.setText(print.Minerals);
                calcium.setText(print.Calcium);
                iron.setText(print.Iron);
                fibre.setText(print.Fibre);
                moisture.setText(print.Moisture);
                phosphorous.setText(print.Phosphorous);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
