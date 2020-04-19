package com.fitpal.fitpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fitpal.fitpal.model.UserMeal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.fitpal.fitpal.MainActivity.GoalFromDB;
import static com.fitpal.fitpal.MainActivity.KeyUserMealsFromDB;
import static com.fitpal.fitpal.VoiceInput.consumed;
import static com.fitpal.fitpal.VoiceInput.count;

public class BarcodeResults extends AppCompatActivity {

    DatabaseReference databaseReference,databaseReferenceUserMeals;

    private TextView name,brand,size,ingr,ntr,barcode,caloriesShow,proteinShow,carbsShow,fiberShow,calciumShow,fatShow,remark;
    private Button readAgain;
    private String pr,bar,br,si,ing,nut,calc,protein,fats,carbs,calories,fiber;
    private UserMeal um;
    private int count=1;
    private float consumed=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_barcode_results);

        name=findViewById(R.id.BFoodNameId);
        brand=findViewById(R.id.BBrandId);
        caloriesShow=findViewById(R.id.BCalId);
        calciumShow=findViewById(R.id.BCalciumId);
        fiberShow=findViewById(R.id.BFiberId);
        proteinShow=findViewById(R.id.BProteinId);
        carbsShow=findViewById(R.id.BCarbId);
        fatShow=findViewById(R.id.BFatId);
        //ingr=findViewById(R.id.BIngrId);
       // ntr=findViewById(R.id.BNtrScoreId);
        barcode=findViewById(R.id.BBarId);
        remark=findViewById(R.id.BRemarkId);

        um=new UserMeal();

        databaseReferenceUserMeals=FirebaseDatabase.getInstance().getReference("UserMeals").child(String.valueOf(KeyUserMealsFromDB));

        databaseReferenceUserMeals.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                consumed=0;
                count=1;

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    UserMeal utemp= dataSnapshot1.getValue(UserMeal.class);
                    String date= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());


                    if(utemp.Date.contains(date) && utemp!=null){

                        consumed+=Float.parseFloat(utemp.Calories);
                        count++;
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Intent t=getIntent();
        pr=t.getStringExtra("product");
        bar=t.getStringExtra("barcode");
        br=t.getStringExtra("brand");
        si=t.getStringExtra("size");
        ing=t.getStringExtra("ingr");
       // nut=t.getStringExtra("nutr");

        //calc=t.getStringExtra("calcium");
        calc="0";
        protein=t.getStringExtra("protein");
        fats=t.getStringExtra("fats");
  //      fiber=t.getStringExtra("fiber");
        fiber="0";
        carbs=t.getStringExtra("carbs");
        calories=t.getStringExtra("calories");

        String date= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        um.Date=date;
        um.Calories=calories;
        um.Fibre=fiber;
        um.Calcium=calc;
        um.Fat=fats;
        um.Carb=carbs;
        um.MealName=pr;
        um.Protein=protein;
        um.Phosphorous="0";
        um.Iron="0";


        name.setText(pr);
        brand.setText(br);
        caloriesShow.setText(calories);
        //ingr.setText(ing);
       // ntr.setText(nut);
        barcode.setText(bar);
        proteinShow.setText(protein);
        carbsShow.setText(carbs);
        calciumShow.setText(calc);
        fiberShow.setText(fiber);
        fatShow.setText(fats);





        databaseReferenceUserMeals.child(String.valueOf(date+"-"+count)).setValue(um);
        if(consumed+Float.parseFloat(calories)<GoalFromDB){
            remark.setText("You have "+(GoalFromDB-consumed-Float.parseFloat(calories))+ " calories left");
            Toast.makeText(getApplicationContext(),String.valueOf("You have "+(GoalFromDB-consumed-Float.parseFloat(calories))+ " calories left"),Toast.LENGTH_LONG).show();
        }
        else{
            remark.setText("Oh no, you have reached the calorie limit.");
            remark.setTextColor(getResources().getColor(R.color.red));
            Toast.makeText(getApplicationContext(),"Oh no, you have reached the calorie limit.",Toast.LENGTH_LONG).show();
        }





        readAgain=findViewById(R.id.BReadAgainID);
        readAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),BarcodeInput.class);
                startActivity(i);
                finish();
            }
        });
    }
}
