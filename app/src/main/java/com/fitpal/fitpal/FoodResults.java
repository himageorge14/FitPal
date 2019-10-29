package com.fitpal.fitpal;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fitpal.fitpal.model.FoodItem;
import com.fitpal.fitpal.model.MySingletone;
import com.fitpal.fitpal.model.UserMeal;
import com.fitpal.fitpal.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.zip.DeflaterOutputStream;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static com.fitpal.fitpal.MainActivity.GoalFromDB;
import static com.fitpal.fitpal.MainActivity.KeyUserMealsFromDB;

public class FoodResults extends AppCompatActivity {


    private TextView name,carbs,energy,fat,protein,mineral,calcium,fibre,calories,remarks;
    private Button save,readAgain;
    String foodToSearch,date,page,cal;
    DatabaseReference databaseReference,databaseReferenceUserMeals;
    FoodItem print=new FoodItem();
    UserMeal um=new UserMeal();
    float consumed=0;
    int count=1;

    static String x_app_id, x_app_key, x_remote_user_id;
    private RequestQueue mQueue;

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
        fibre=findViewById(R.id.FibreId);
        calories=findViewById(R.id.CaloriesId);

        remarks=findViewById(R.id.RemarksID);

        readAgain=findViewById(R.id.ReadAgainID);

        Intent in=getIntent();


        foodToSearch=in.getStringExtra("foodresult");
        page=in.getStringExtra("page");

        Log.d("outtttt",foodToSearch);

        databaseReference = FirebaseDatabase.getInstance().getReference("Foods");
        databaseReferenceUserMeals=FirebaseDatabase.getInstance().getReference("UserMeals").child(String.valueOf(KeyUserMealsFromDB));


        x_app_id = "d2972203";
        x_app_key = "0f4683e999413bcdb29a139c8f9107a3";
        x_remote_user_id = "0f4683e999413bcdb29a139c8f9107a3";

        //volley code
        String url = "https://trackapi.nutritionix.com/v2/natural/nutrients";
        mQueue = MySingletone.getInstance(getApplicationContext()).getRequestQueue();
        JSONObject jsonObject1=new JSONObject();

        try {
            jsonObject1.put("query", foodToSearch);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,url,jsonObject1, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {


                   // Log.d("resultssss",String.valueOf(response));

                    JSONArray jsonArrayFoods=response.getJSONArray("foods");
                   // Log.d("foodssss",String.valueOf(jsonArrayFoods));

                    JSONObject productFood=null;

                    for(int i=0;i<jsonArrayFoods.length();i++){

                        productFood=jsonArrayFoods.getJSONObject(i);

                        Log.d("objjj",String.valueOf(productFood.get("nf_p").toString()));
                        Log.d("obj2",String.valueOf(productFood.get("nf_total_fat").toString()));

                        print.Name=productFood.getString("food_name").toString();
                        print.Carbos=productFood.get("nf_total_carbohydrate").toString();
                        print.Energy=productFood.get("nf_p").toString();
                        print.Fat=productFood.get("nf_total_fat").toString();
                        print.Protein=productFood.get("nf_protein").toString();
                        print.Minerals=productFood.get("nf_potassium").toString();
                        print.Calcium=productFood.get("nf_saturated_fat").toString();
                        print.Fibre=productFood.get("nf_dietary_fiber").toString();
                        cal=productFood.get("nf_calories").toString();

                        print.Phosphorous="0";
                        print.Iron="0";

                        name.setText(String.valueOf(productFood.get("food_name").toString()));
                        carbs.setText(String.valueOf(productFood.get("nf_total_carbohydrate").toString()));
                        energy.setText(String.valueOf(productFood.get("nf_p").toString()));
                        fat.setText(String.valueOf(productFood.get("nf_total_fat").toString()));
                        protein.setText(String.valueOf(productFood.get("nf_protein").toString()));
                        mineral.setText(String.valueOf(productFood.get("nf_potassium").toString()));
                        calcium.setText(String.valueOf(productFood.get("nf_saturated_fat").toString()));
                        fibre.setText(String.valueOf(productFood.get("nf_dietary_fiber").toString()));
                        calories.setText(String.valueOf(productFood.get("nf_calories").toString()));

                        date= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());



                        um.Calcium=String.valueOf(productFood.get("nf_saturated_fat").toString());
                        um.Carb=String.valueOf(productFood.get("nf_total_carbohydrate").toString());
                        um.Date=date;
                        um.Fat=String.valueOf(productFood.get("nf_total_fat").toString());
                        um.Fibre=String.valueOf(productFood.get("nf_dietary_fiber").toString());
                        um.Iron=print.Iron;
                        um.MealName=String.valueOf(productFood.get("food_name").toString());
                        um.Phosphorous=print.Phosphorous;
                        um.Protein=String.valueOf(productFood.get("nf_protein").toString());
                        um.Calories=String.valueOf(productFood.get("nf_calories").toString());


                    }


                } catch (Exception e) {

                }
            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Called","Adapter is called");
                Log.d("Volley Error",error.toString());
                error.printStackTrace();

            }
        })  {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> hmap=new HashMap<>();
                hmap.put("x-app-id", x_app_id);
                hmap.put("x-app-key", x_app_key);
                hmap.put("x-remote-user-id",x_remote_user_id);
                hmap.put("Content-Type", "application/json");
                return hmap;

            }
        };

        mQueue.add(jsonObjectRequest);

//                name.setText(print.Name);
//              carbs.setText(print.Carbos);
//              energy.setText(print.Energy);
//               fat.setText(print.Fat);
//               protein.setText(print.Protein);
//               mineral.setText(print.Minerals);
//               calcium.setText(print.Calcium);
//                fibre.setText(print.Fibre);
//                date= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
//
//                um=new UserMeal();
//
//                um.Calcium=print.Calcium;
//                um.Carb=print.Carbos;
//                um.Date=date;
//                um.Fat=print.Fat;
//                um.Fibre=print.Fibre;
//                um.Iron=print.Iron;
//                um.MealName=print.Name;
//                um.Phosphorous=print.Phosphorous;
//                um.Protein=print.Protein;
//                um.Calories=cal;



        Log.d("sttt",String.valueOf(um.Calories));
        date= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());


        databaseReferenceUserMeals.addValueEventListener(new ValueEventListener() {
            UserMeal utemp=new UserMeal();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    count=1;
                    consumed=0;
                     utemp= dataSnapshot1.getValue(UserMeal.class);


                    if(utemp.Date.contains(date)){

                        consumed+=Float.parseFloat(utemp.Calories);
                        count++;
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Log.d("ddd",String.valueOf(date+" "+count));
        databaseReferenceUserMeals.child(String.valueOf(date+"-"+count)).setValue(um);

        //Log.d("counsumedddddTotal",String.valueOf(consumed+um.Calories));

        //Float c=Float.parseFloat(um.Calories)+consumed;
        if(consumed<GoalFromDB){
            remarks.setText("You have "+(GoalFromDB-consumed)+ " calories left");
        }
        else{
            remarks.setText("Oh no, you have reached the calorie limit");
        }


        readAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(page.equalsIgnoreCase("Voice")){
                    Intent i=new Intent(getApplicationContext(),VoiceInput.class);
                    startActivity(i);
                    finish();
                }
            }
        });






    }

}
