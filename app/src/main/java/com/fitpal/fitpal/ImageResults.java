package com.fitpal.fitpal;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fitpal.fitpal.model.FoodItem;
import com.fitpal.fitpal.model.MySingletone;
import com.fitpal.fitpal.model.UserMeal;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.fitpal.fitpal.MainActivity.GoalFromDB;
import static com.fitpal.fitpal.MainActivity.KeyUserMealsFromDB;
import static com.fitpal.fitpal.VoiceInput.consumed;
import static com.fitpal.fitpal.VoiceInput.count;

public class ImageResults extends AppCompatActivity {


    private TextView name,carbs,energy,fat,protein,mineral,calcium,fibre,calories,remarks;
    private Button readAgain;
    String foodToSearch,date,page,cal;
    DatabaseReference databaseReference,databaseReferenceUserMeals;
    FoodItem print;
    UserMeal um;
    //    float consumed;
//    int count;
    float tempCalToSubtract = 0;

    static String x_app_id, x_app_key, x_remote_user_id;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_results);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent in=getIntent();


        foodToSearch=in.getStringExtra("dishImageName");
        //foodToSearch="idli sambar";

        name=findViewById(R.id.IFoodNameId);
        carbs=findViewById(R.id.ICarbId);
        energy=findViewById(R.id.IEnergyId);
        fat=findViewById(R.id.IFatId);
        protein=findViewById(R.id.IProteinId);
        mineral=findViewById(R.id.IMineralsId);
        calcium=findViewById(R.id.ICalciumId);
        fibre=findViewById(R.id.IFibreId);
        calories=findViewById(R.id.ICaloriesId);

        remarks=findViewById(R.id.IRemarksID);

        readAgain=findViewById(R.id.IReadAgainID);


       // page=in.getStringExtra("page");
//        consumed=in.getFloatExtra("consumned",0);
//        count=in.getIntExtra("count",1);
//
//        Log.d("con",String.valueOf(consumed));
//        Log.d("counttt",String.valueOf(count));




        //foodToSearch="bread";

        Log.d("outtttt",foodToSearch);

        //databaseReference = FirebaseDatabase.getInstance().getReference("Foods");
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

        UserMeal t1=new UserMeal();


        print=new FoodItem();
//        print.Name="";
//        print.Carbos="0";
//        print.Iron="0";
//        print.Phosphorous="0";
//        print.Fibre="0";
//        print.Calcium="0";
//        print.Minerals="0";
//        print.Protein="0";
//        print.Fat="0";
//        print.Energy="0";
//        print.Moisture="0";
        um=new UserMeal();



        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,url,jsonObject1, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {


                    Log.d("resultssss",String.valueOf(response));

                    JSONArray jsonArrayFoods=response.getJSONArray("foods");
                    // Log.d("foodssss",String.valueOf(jsonArrayFoods));

                    JSONObject productFood=null;

                    for(int i=0;i<jsonArrayFoods.length();i++){

                        productFood=jsonArrayFoods.getJSONObject(i);

                        Log.d("objjj",String.valueOf(productFood.get("nf_p").toString()));
                        Log.d("obj2",String.valueOf(productFood.get("nf_total_fat").toString()));

                        print.Name=print.Name+productFood.getString("food_name");

                        Log.d("nnna",print.Name);


                        print.Energy=productFood.get("nf_p").toString();
                        print.Fat=productFood.get("nf_total_fat").toString();
                        print.Protein=productFood.get("nf_protein").toString();
                        print.Minerals=productFood.get("nf_potassium").toString();
                        print.Calcium=productFood.get("nf_saturated_fat").toString();
                        print.Fibre=productFood.get("nf_dietary_fiber").toString();
                        cal=productFood.get("nf_calories").toString();


                        name.setText(foodToSearch);
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
                        um.MealName=foodToSearch;
                        um.Phosphorous=print.Phosphorous;
                        um.Protein=String.valueOf(productFood.get("nf_protein").toString());
                        um.Calories=String.valueOf(productFood.get("nf_calories").toString());
                        Log.d("ucal",um.Calories);






                    }

                    databaseReferenceUserMeals.child(String.valueOf(date+"-"+count)).setValue(um);
                    if(consumed+Float.parseFloat(calories.getText().toString())<GoalFromDB){
                        remarks.setText("You have "+(GoalFromDB-consumed-Float.parseFloat(calories.getText().toString()))+ " calories left");
                        Toast.makeText(getApplicationContext(),String.valueOf("You have "+(GoalFromDB-consumed-Float.parseFloat(calories.getText().toString()))+ " calories left"),Toast.LENGTH_LONG).show();
                    }
                    else{
                        remarks.setText("Oh no, you have reached the calorie limit.");
                        remarks.setTextColor(getResources().getColor(R.color.red));
                        Toast.makeText(getApplicationContext(),"Oh no, you have reached the calorie limit.",Toast.LENGTH_LONG).show();
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



        readAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),ImageHome.class);
                startActivity(i);
                finish();

            }
        });

    }

}
