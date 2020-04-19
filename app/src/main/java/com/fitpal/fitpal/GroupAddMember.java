package com.fitpal.fitpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fitpal.fitpal.model.UserKeys;
import com.fitpal.fitpal.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.fitpal.fitpal.Login.Email;
import static com.fitpal.fitpal.MainActivity.KeyUserMealsFromDB;

public class GroupAddMember extends AppCompatActivity {

    EditText addMemKey;
    Button addMemSubmit;
    DatabaseReference db,databaseUsers,databaseReference;
    String k;
    UserKeys us,u;
    int flag=0;
    ArrayList<String> people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_add_member);
        u=new UserKeys();

        db=FirebaseDatabase.getInstance().getReference("UserKeys");
        people=new ArrayList<>();
        us=new UserKeys();

        addMemKey=(EditText)findViewById(R.id.addkeyId);
        addMemSubmit=(Button)findViewById(R.id.memberSubmitId);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    UserKeys utemp = dataSnapshot1.getValue(UserKeys.class);
                    people.add(utemp.key);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        addMemSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                k=addMemKey.getText().toString();

                for (int i=0;i<people.size();i++){
                    if(people.get(i).equals(k)){
                        us.setKey(k);
                        flag=1;
                        break;

                    }
                }

                if(flag!=1){
                    AlertDialog.Builder builder = new AlertDialog.Builder(GroupAddMember.this);
                    builder.setTitle("Invalid key");

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

                    databaseUsers=FirebaseDatabase.getInstance().getReference("UserKeys");

                    databaseUsers.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                                UserKeys utemp = dataSnapshot1.getValue(UserKeys.class);
                                if(String.valueOf(utemp.key).equals(String.valueOf(us.key))){

                                    u=utemp;
                                    Log.d("reachhhh",String.valueOf(u.userKey));
                                    break;

                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    Log.d("valll",String.valueOf(u.userKey));

                    Log.d("keee",String.valueOf(KeyUserMealsFromDB));
                    Toast.makeText(getApplicationContext(),"Friend added",Toast.LENGTH_LONG);

                    databaseReference=FirebaseDatabase.getInstance().getReference("UserFriends").child(String.valueOf(KeyUserMealsFromDB));

                    databaseReference.child(String.valueOf(u.userKey)).setValue(u.userKey);
//                    Intent i=new Intent(getApplicationContext(),MyGroup.class);
//                    startActivity(i);


                }


            }

        });


    }
}
