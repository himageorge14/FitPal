package com.fitpal.fitpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fitpal.fitpal.model.UserMeal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import static com.fitpal.fitpal.VoiceInput.consumed;
import static com.fitpal.fitpal.VoiceInput.count;

import static com.fitpal.fitpal.MainActivity.KeyUserMealsFromDB;

public class ManualInput extends AppCompatActivity {

    EditText manualName;
    Button manualSubmit;
    String inputname;
    DatabaseReference databaseReference,databaseReferenceUserMeals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_input);

        manualName=(EditText)findViewById(R.id.manualInputFoodNameID);
        manualSubmit=(Button)findViewById(R.id.manualSubmitID);



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



        manualSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                inputname=manualName.getText().toString();

                String ob=" ";
                if(manualName.getText().equals(ob)){
                    Log.d("mfcheck","null");
                }
                if(inputname.isEmpty()){
                    Log.d("mfcheck","null2");
                }
                Log.d("inname",inputname);

                Intent i=new Intent(getApplicationContext(),ManualResults.class);
                i.putExtra("foodname",inputname);
                i.putExtra("count",count);
                i.putExtra("consumned",consumed);

                startActivity(i);
                finish();
            }
        });





    }
}
