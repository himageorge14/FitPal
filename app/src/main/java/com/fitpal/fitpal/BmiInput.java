package com.fitpal.fitpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fitpal.fitpal.model.UserHistory;
import com.fitpal.fitpal.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static com.fitpal.fitpal.Login.Email;
import static com.fitpal.fitpal.Login.Name;

public class BmiInput extends AppCompatActivity{

    EditText age,weight,height;
    TextView welcomeText;
    Spinner gender,goal;
    int ageNo;
    float heightNo,weightNo,bmiVal,goalNo;
    float bmiActual;
    String genderVal,emailBmi,goalStr;
    Button submit;
    DatabaseReference databaseUsers,databaseUserHistory;
    ArrayList<Users> usersArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_bmi_input);

        welcomeText=findViewById(R.id.BMIHiUserID);
        age=findViewById(R.id.AgeId);
        weight=findViewById(R.id.WeightId);
        height=findViewById(R.id.HeightId);
        gender=findViewById(R.id.GenderId);
        goal=findViewById(R.id.GoalId);
        submit=findViewById(R.id.bmiSubmitID);



        usersArrayList=new ArrayList<>();

        welcomeText.setText("Welcome "+Name+ ", please enter these details");

        databaseUsers=FirebaseDatabase.getInstance().getReference("Users");
        databaseUserHistory=FirebaseDatabase.getInstance().getReference("UserHistory");

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


        goal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                ageNo=Integer.parseInt(String.valueOf(age.getText()));
                weightNo=Float.parseFloat(String.valueOf(weight.getText()));
                heightNo=Float.parseFloat(String.valueOf(height.getText()));
                genderVal=String.valueOf(gender.getSelectedItem());

                //bmr calculation
                if(genderVal.equalsIgnoreCase("female")){
                    bmiVal=(float)(655.1+((4.35*2.20462*weightNo)+(4.7*39.3701*heightNo)-(4.7*ageNo)));
                }
                else{
                    bmiVal=(float)(66.47+(6.24*2.20462*weightNo)+(12.7*39.3701*heightNo)-(6.755*ageNo));
                }
                emailBmi=Email;

                //x 1.2
                //bmiVal=(float)(bmiVal*1.2);

                bmiActual=weightNo/(heightNo*heightNo);

                Log.d("bmiii",String.valueOf(bmiVal));

                if(bmiActual<18.5){
                    Toast.makeText(getApplicationContext(),"We suggest you the Weight gain plan",Toast.LENGTH_LONG).show();
                }
                else if(bmiActual>25.9){
                    Toast.makeText(getApplicationContext(),"We suggest you the Weight loss plan",Toast.LENGTH_LONG).show();
                }

                goalNo=(float)(bmiVal*1.2);

                return false;
            }
        });




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(age.getText().toString().equals("") || Integer.parseInt(age.getText().toString())<1 || Integer.parseInt(age.getText().toString())>99){
                    AlertDialog.Builder builder = new AlertDialog.Builder(BmiInput.this);
                    builder.setTitle("Please enter a valid age");

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    final AlertDialog dialog = builder.create();
                    dialog.show();

                    final Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
                    positiveButtonLL.gravity = Gravity.END;
                    positiveButton.setLayoutParams(positiveButtonLL);
                }
                else if(weight.getText().toString().equals("") || Float.parseFloat(weight.getText().toString())<10 || Float.parseFloat(weight.getText().toString())>130){
                    AlertDialog.Builder builder = new AlertDialog.Builder(BmiInput.this);
                    builder.setTitle("Please enter a valid weight");

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    final AlertDialog dialog = builder.create();
                    dialog.show();

                    final Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
                    positiveButtonLL.gravity = Gravity.END;
                    positiveButton.setLayoutParams(positiveButtonLL);
                }
                else if(height.getText().toString().equals("") || Float.parseFloat(height.getText().toString())<0.5 || Float.parseFloat(height.getText().toString())>2.5){
                    AlertDialog.Builder builder = new AlertDialog.Builder(BmiInput.this);
                    builder.setTitle("Please enter a valid height");

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    final AlertDialog dialog = builder.create();
                    dialog.show();

                    final Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
                    positiveButtonLL.gravity = Gravity.END;
                    positiveButton.setLayoutParams(positiveButtonLL);
                }

                else{

                    goalStr=String.valueOf(goal.getSelectedItem());

                    if(goalStr.equals("Weight Loss")){

                        goalNo-=500;

                    }
                    else if(goalStr.equals("Weight Gain")){
                        goalNo+=500;
                    }
                    else{
                        goalNo=goalNo;
                    }

                    Random r = new Random();
                    int i1 = r.nextInt(7000);


                    String id=String.valueOf(usersArrayList.size()+1);
                    Users u = new Users(ageNo,bmiActual,emailBmi,genderVal,goalNo,heightNo,i1,weightNo);
                    UserHistory uH=new UserHistory(bmiActual,heightNo,weightNo);

                    databaseUsers.child(id).setValue(u);

//                    String date= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
//
//                    databaseUserHistory.child(String.valueOf(i1)).child(date).setValue(uH);

                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                    finish();


                }


            }
        });


    }
}
