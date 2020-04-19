package com.fitpal.fitpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.fitpal.fitpal.model.FriendDetailMeals;
import com.fitpal.fitpal.model.FriendsAdapter;
import com.fitpal.fitpal.model.UserHistory;
import com.fitpal.fitpal.model.UserMeal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.fitpal.fitpal.MainActivity.GoalFromDB;
import static com.fitpal.fitpal.MainActivity.KeyUserMealsFromDB;
import static com.fitpal.fitpal.MainActivity.bmiS;

public class FriendDetails extends AppCompatActivity {

    TextView avgFat,avgCarb,avgCal,avgProt,avgFiber,weightChange,cBMI,reportProgress;
    DatabaseReference databaseReference,databaseReferenceUserMeals,databaseUserHistory,db;
    Double rfat,rfib,rcal,rcarb,rpro,count;
    String bmiTemp;
    private RecyclerView recyclerView;
    private FriendDetailMeals fAdapter;
    ArrayList<UserMeal> umlist;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_details);

        umlist=new ArrayList<>();
        fAdapter=new FriendDetailMeals(umlist, getApplicationContext());

        recyclerView = (RecyclerView) findViewById(R.id.friendDetailsRecyclerID);

        db=FirebaseDatabase.getInstance().getReference("UserMeals").child(String.valueOf(FriendsAdapter.tempUser.KeyUserMeals));


        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    UserMeal u=dataSnapshot1.getValue(UserMeal.class);
                    umlist.add(u);


                }
                Log.d("um1",String.valueOf(umlist));
                flag=1;
                dispMeals();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        avgFat=(TextView)findViewById(R.id.freport_avg_fats);
        avgCarb=(TextView)findViewById(R.id.freport_avg_carb);
        avgCal=(TextView)findViewById(R.id.freport_avg_cal);
        avgProt=(TextView)findViewById(R.id.freport_avg_prot);
        avgFiber=(TextView)findViewById(R.id.freport_avg_fiber);
        cBMI=(TextView)findViewById(R.id.freport_bmi_score);
        //reportProgress=(TextView)findViewById(R.id.freport_RemarksID);

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




        databaseUserHistory=FirebaseDatabase.getInstance().getReference("UserHistory").child(String.valueOf(FriendsAdapter.tempUser.KeyUserMeals));

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
//
//
//        avgFiber.setText(String.valueOf(String.format("%.2f", rfib/count)));
//        avgFat.setText(String.valueOf(String.format("%.2f", rfat/count)));
//        avgCal.setText(String.valueOf(rcal/count));
//        avgCarb.setText(String.valueOf(rcarb/count));
//        avgProt.setText(String.valueOf(rpro/count));
//        cBMI.setText(String.valueOf(bmiS));
        //reportProgress.setText("BMI score has increased.");
        avgFiber.setText(String.valueOf(String.format("%.2f", rfib/count)));
        avgFat.setText(String.valueOf(String.format("%.2f", rfat/count)));
        avgCal.setText(String.valueOf(String.format("%.2f", rcal/count)));
        avgCarb.setText(String.valueOf(String.format("%.2f", rcarb/count)));
        avgProt.setText(String.valueOf(String.format("%.2f", rpro/count)));
        cBMI.setText(String.valueOf(String.format("%.2f", bmiS)));
    }

    private void dispMeals() {

        if(flag==1) {

            Log.d("um2",String.valueOf(umlist));

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(fAdapter);

            fAdapter = new FriendDetailMeals(umlist, getApplicationContext());

            fAdapter.notifyDataSetChanged();

        }
        else{
            dispMeals();
        }
    }
}
