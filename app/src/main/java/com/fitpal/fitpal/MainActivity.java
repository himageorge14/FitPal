package com.fitpal.fitpal;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.fitpal.fitpal.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static com.fitpal.fitpal.Login.Email;

public class MainActivity extends AppCompatActivity {

    Button voice,barcode,signout;
    DatabaseReference databaseUsers;
    static float GoalFromDB;
    static long KeyUserMealsFromDB;
    Users us=new Users();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        voice=findViewById(R.id.voiceRedirectButtonId);
        barcode=findViewById(R.id.barcodeRedirectButtonId);
        signout=findViewById(R.id.signoutId);

        databaseUsers=FirebaseDatabase.getInstance().getReference("Users");

        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    Users utemp = dataSnapshot1.getValue(Users.class);
                    if(utemp.Email.equalsIgnoreCase(Email)){

                        us=utemp;
                        break;

                    }
                }

                GoalFromDB=us.Goal;
                KeyUserMealsFromDB=us.KeyUserMeals;

//                Log.d("goallllldbbb",String.valueOf(GoalFromDB));
//                Log.d("Keyyyyuserr",String.valueOf(KeyUserMealsFromDB));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
