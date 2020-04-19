package com.fitpal.fitpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fitpal.fitpal.model.UserMeal;
import com.fitpal.fitpal.model.Users;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.fitpal.fitpal.Login.Email;
import static com.fitpal.fitpal.Login.Name;
import static com.fitpal.fitpal.Login.profile_url;
import static com.fitpal.fitpal.MainActivity.KeyUserMealsFromDB;

public class UserDetailsPage extends AppCompatActivity {

    private TextView name,email;
    private SignInButton signOutButton;
    private Button submit;
    private ImageView photo;
    private GoogleSignInClient mGoogleSignInClient;
    EditText height,weight;
    DatabaseReference databaseReferenceUser,db;
    String etemp;
    Users utemp2;
    ArrayList<Users> usersArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_page);

        name=(TextView)findViewById(R.id.name);
        email=(TextView)findViewById(R.id.email);
        Button signOutButton=(Button)findViewById(R.id.userDets_sign_out);
        photo=(ImageView)findViewById(R.id.profile_photo);
        height=(EditText)findViewById(R.id.userDetsHeightEdit);
        weight=(EditText)findViewById(R.id.userDetsWeightEdit);
        utemp2=new Users();
        usersArrayList=new ArrayList<>();

        //set details on page
        name.setText(Name);
        email.setText(Email);
        if(profile_url!=null){
            Glide.with(UserDetailsPage.this).load(profile_url).into(photo);
        }


        databaseReferenceUser=FirebaseDatabase.getInstance().getReference("Users");

        databaseReferenceUser.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    Users utemp= dataSnapshot1.getValue(Users.class);
                    String date= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());


                    if(utemp.Email.equals(Email) && utemp!=null ){

                        utemp2=utemp;
                        height.setText(String.valueOf(utemp.Height));
                        weight.setText(String.valueOf(utemp.Weight));
                        break;
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

//
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(getApplicationContext(),Reports.class);
//                i.putExtra("heightNew",String.valueOf(height.getText()));
//                i.putExtra("weightNew",String.valueOf(weight.getText()));
//                startActivity(i);
//            }
//        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String h=height.getText().toString();
                String w=weight.getText().toString();
                String genderVal=utemp2.Gender;
                float bmiVal,bmiActual;
                float weightNo=Float.parseFloat(w);
                float heightNo=Float.parseFloat(h);
                int ageNo=utemp2.Age;

                if(genderVal.equalsIgnoreCase("female")){
                    bmiVal=(float)(655.1+((4.35*2.20462*weightNo)+(4.7*39.3701*heightNo)-(4.7*ageNo)));
                }
                else{
                    bmiVal=(float)(66.47+(6.24*2.20462*weightNo)+(12.7*39.3701*heightNo)-(6.755*ageNo));
                }

                //x 1.2
                //bmiVal=(float)(bmiVal*1.2);

                bmiActual=weightNo/(heightNo*heightNo);

                utemp2.BMI=bmiActual;
                utemp2.Height=heightNo;
                utemp2.Weight=weightNo;

                db=FirebaseDatabase.getInstance().getReference("Users");

//                Query applesQuery = db.orderByChild("Email").equalTo(utemp2.Email);
//
//                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
//                            appleSnapshot.getRef().removeValue();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        Log.e("fdeletee", "onCancelled", databaseError.toException());
//                    }
//                });

                db.addValueEventListener(new ValueEventListener() {
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


                int i=usersArrayList.size()+1;

                db.child(String.valueOf(i)).setValue(utemp2);




            }
        });


    }
}
