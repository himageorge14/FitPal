package com.fitpal.fitpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.fitpal.fitpal.model.UserHistory;
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
import static com.fitpal.fitpal.MainActivity.bmiS;

public class Reports extends AppCompatActivity {

    TextView avgFat,avgCarb,avgCal,avgProt,avgFiber,weightChange,cBMI,reportProgress;
    DatabaseReference databaseReference,databaseReferenceUserMeals,databaseUserHistory;
    Double rfat,rfib,rcal,rcarb,rpro,count;
    String bmiTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        avgFat=(TextView)findViewById(R.id.report_avg_fats);
        avgCarb=(TextView)findViewById(R.id.report_avg_carb);
        avgCal=(TextView)findViewById(R.id.report_avg_cal);
        avgProt=(TextView)findViewById(R.id.report_avg_prot);
        avgFiber=(TextView)findViewById(R.id.report_avg_fiber);
        cBMI=(TextView)findViewById(R.id.report_bmi_score);
        reportProgress=(TextView)findViewById(R.id.report_RemarksID);

        rfat= new Double(0);
        rfib= new Double(0);
        rcal= new Double(0);
        rcarb= new Double(0);
        rpro= new Double(0);
        count= new Double(0);

        rfib= Double.valueOf(GoalFromDB/11);
        rcal=GoalFromDB*10.2;
        rpro= Double.valueOf(GoalFromDB/4);
        rfat= Double.valueOf(GoalFromDB/2);
        rcarb= Double.valueOf(GoalFromDB/1);

        rfib= Double.valueOf(Math.round(rfib*1000)/1000);
        rcal= Double.valueOf(Math.round(rcal*100)/100);
        rpro= Double.valueOf(Math.round(rpro*100)/100);
        rfat= Double.valueOf(Math.round(rfat*1000)/1000);
        rcarb= Double.valueOf(Math.round(rcarb*100)/100);
//
//        Intent in=getIntent();
//
//        String fib=in.getStringExtra("rfib");
//        if(fib.equals("")){
//            Log.d("sss","no");
//        }
//        else{
//            Log.d("sss","yes");
//        }

//        rfib=Double.parseDouble(in.getStringExtra("rfib"));
//        rfat=Double.parseDouble(in.getStringExtra("rfat"));
//        rcal=Double.parseDouble(in.getStringExtra("rcal"));
//        rcarb=Double.parseDouble(in.getStringExtra("rcarb"));
//        rpro=Double.parseDouble(in.getStringExtra("rpro"));


//
//        databaseReferenceUserMeals=FirebaseDatabase.getInstance().getReference("UserMeals").child(String.valueOf(KeyUserMealsFromDB));
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




        databaseUserHistory=FirebaseDatabase.getInstance().getReference("UserHistory").child(String.valueOf(KeyUserMealsFromDB));

        databaseUserHistory.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    UserHistory utemp= dataSnapshot1.getValue(UserHistory.class);
                    String date= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                    bmiTemp=String.valueOf(utemp.BMI);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        count=12.0;


        avgFiber.setText(String.valueOf(String.format("%.2f", rfib/count)));
        avgFat.setText(String.valueOf(String.format("%.2f", rfat/count)));
        avgCal.setText(String.valueOf(String.format("%.2f", rcal/count)));
        avgCarb.setText(String.valueOf(String.format("%.2f", rcarb/count)));
        avgProt.setText(String.valueOf(String.format("%.2f", rpro/count)));
        cBMI.setText(String.valueOf(String.format("%.2f", bmiS)));
        reportProgress.setText("Your BMI score has increased.");


    }
}
