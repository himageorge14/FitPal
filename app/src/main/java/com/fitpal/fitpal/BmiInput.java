package com.fitpal.fitpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.fitpal.fitpal.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

import static com.fitpal.fitpal.Login.Email;
import static com.fitpal.fitpal.Login.Name;

public class BmiInput extends AppCompatActivity {

    EditText age,weight,height;
    TextView welcomeText;
    Spinner gender;
    int ageNo;
    float heightNo,weightNo,bmiVal,goalNo;
    String genderVal,emailBmi;
    Button submit;
    DatabaseReference databaseUsers;
    ArrayList<Users> usersArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_input);

        welcomeText=findViewById(R.id.BMIHiUserID);
        age=findViewById(R.id.AgeId);
        weight=findViewById(R.id.WeightId);
        height=findViewById(R.id.HeightId);
        gender=findViewById(R.id.GenderId);
        submit=findViewById(R.id.bmiSubmitID);

        usersArrayList=new ArrayList<>();

        welcomeText.setText("Welcome "+Name+ ", please enter these details");

        databaseUsers=FirebaseDatabase.getInstance().getReference("Users");

        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    Users utemp = dataSnapshot1.getValue(Users.class);
                    usersArrayList.add(utemp);
                }}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ageNo=Integer.parseInt(String.valueOf(age.getText()));
                weightNo=Float.parseFloat(String.valueOf(weight.getText()));
                heightNo=Float.parseFloat(String.valueOf(height.getText()));
                genderVal=String.valueOf(gender.getSelectedItem());
                bmiVal=weightNo/(heightNo*heightNo);
                emailBmi=Email;

                Random r = new Random();
                int i1 = r.nextInt(7000);

                if(bmiVal>24){
                    goalNo=1400;
                }
                else{
                    goalNo=1600;
                }

//                Log.d("emailllbmii",emailBmi);
//                Log.d("genderrr",genderVal);
//                Log.d("heighttt",String.valueOf(heightNo));
//                Log.d("weighttt",String.valueOf(weightNo));
//                Log.d("ageeee",String.valueOf(ageNo));
//                Log.d("bmiiiiii",String.valueOf(bmiVal));
//                Log.d("randdddooom",String.valueOf(i1));


                String id=String.valueOf(usersArrayList.size()+1);
                Users u = new Users(ageNo,bmiVal,emailBmi,genderVal,goalNo,heightNo,i1,weightNo);
                databaseUsers.child(id).setValue(u);

                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();


            }
        });


    }
}
