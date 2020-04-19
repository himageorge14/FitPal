package com.fitpal.fitpal;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.fitpal.fitpal.model.UserMeal;
import com.fitpal.fitpal.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import static com.fitpal.fitpal.Login.Email;

public class MainActivity extends AppCompatActivity {

    Button voice,barcode,signout,manual,location,group,image,report,recommed,userdets;
    DatabaseReference databaseUsers,databaseReferenceUserMeals;
    static float GoalFromDB,bmiS;
    public static long KeyUserMealsFromDB;
    Users us=new Users();
    Double rfat=0.0,rfib=0.0,rcal=0.0,rcarb=0.0,rpro=0.0,count=0.0;
    String bmiTemp;
    Users utemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        utemp=new Users();

        voice=findViewById(R.id.voiceRedirectButtonId);
        barcode=findViewById(R.id.barcodeRedirectButtonId);
        manual=findViewById(R.id.manualRedirectButtonId);
        signout=findViewById(R.id.signoutId);
        location=findViewById(R.id.locationRedirectButtonId);
        group=findViewById(R.id.groupRedirectButtonId);
        image=findViewById(R.id.imageRedirectButtonId);
        report=findViewById(R.id.reportRedirectButtonId);
        recommed=findViewById(R.id.recommendRedirectButtonId);
        userdets=findViewById(R.id.UserDetsButtonId);

        databaseUsers=FirebaseDatabase.getInstance().getReference("Users");

        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    utemp = dataSnapshot1.getValue(Users.class);
                    if(utemp.Email.equalsIgnoreCase(Email)){

                        us=utemp;
                        break;

                    }
                }

                GoalFromDB=us.Goal;
                KeyUserMealsFromDB=us.KeyUserMeals;
                bmiS=us.BMI;

//                Log.d("goallllldbbb",String.valueOf(GoalFromDB));
//                Log.d("Keyyyyuserr",String.valueOf(KeyUserMealsFromDB));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//
//
//        databaseReferenceUserMeals=FirebaseDatabase.getInstance().getReference("UserMeals").child(String.valueOf(KeyUserMealsFromDB));
//
//        Log.d("posss","bahar");
//
//        databaseReferenceUserMeals.addValueEventListener(new ValueEventListener() {
//
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
//
//                    UserMeal utemp= dataSnapshot1.getValue(UserMeal.class);
//                    String date= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
//
//                    String fd=String.valueOf(utemp.Date).substring(3,5);
//                    String cd=String.valueOf(date).substring(3,5);
//
//
//                    if(fd.equals(cd) && utemp!=null){
//
//                        rfat=rfat+Double.parseDouble(utemp.Fat);
//                        rfib=rfib+Double.parseDouble(utemp.Fibre);
//                        rcal=rcal+Double.parseDouble(utemp.Calories);
//                        rcarb=rcarb+Double.parseDouble(utemp.Carb);
//                        rpro=rpro+Double.parseDouble(utemp.Protein);
//                        count++;
//
//                        Log.d("posss","andar");
//
//                    }
//
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        if(rfib!=0){
//            Log.d("rfibche",String.valueOf(rfib));
//        }else{
//            Log.d("rfibche","naahi");
//        }
//
//


        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iVoice=new Intent(getApplicationContext(),VoiceInput.class);
                startActivity(iVoice);
            }
        });
        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iBar=new Intent(getApplicationContext(),BarcodeInput.class);
                startActivity(iBar);
            }
        });
        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iMan=new Intent(getApplicationContext(),ManualInput.class);
                startActivity(iMan);
            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iMan=new Intent(getApplicationContext(),LocationTrackingResults.class);
                iMan.putExtra("prevpage","main");
                startActivity(iMan);
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iMan=new Intent(getApplicationContext(),Reports.class);
//
//                iMan.putExtra("rfib",rfib);
//                iMan.putExtra("rcal",rcal);
//                iMan.putExtra("rpro",rpro);
//                iMan.putExtra("rfat",rfat);
//                iMan.putExtra("rcarb",rcarb);
                startActivity(iMan);
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iMan=new Intent(getApplicationContext(),ImageHome.class);
                startActivity(iMan);
            }
        });
        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iMan=new Intent(getApplicationContext(),GroupHome.class);
                startActivity(iMan);
            }
        });
        recommed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iMan=new Intent(getApplicationContext(),RecommendDishes.class);
                startActivity(iMan);
            }
        });
        userdets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),UserDetailsPage.class);
                startActivity(i);
            }
        });


        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                try {
                    mAuth.signOut();
                    Log.d("signouttt","outtttt");
                    Intent i=new Intent(MainActivity.this,Login.class);
                    startActivity(i);
                    finish();

                }catch (Exception e) {
                    Log.e("signOut", "onClick: Exception "+e.getMessage(),e );
                }
            }
        });


    }
}
